import java.text.SimpleDateFormat
import org.apache.commons.lang.StringEscapeUtils

/********************CONFIGURATIONS****************************/
RECIPIENTS="Operations"
SMARTS_USERNAME = "admin"
SMARTS_PASSWORD = "rcpass"
CLASS_NAME = System.getenv().get("SM_OBJ_ClassName");
INSTANCE_NAME = System.getenv().get("SM_OBJ_InstanceName");
ELEMENT_CLASS_NAME = System.getenv().get("SM_OBJ_ElementClassName");
ELEMENT_INSTANCE_NAME = System.getenv().get("SM_OBJ_ElementName");
EVENT_NAME = System.getenv().get("SM_OBJ_EventName");
BROKER_NAME = System.getenv().get("SM_BROKER");
DOMAIN_NAME = System.getenv().get("SM_SERVER_NAME");
SOURCE_DOMAIN_NAME = System.getenv().get("SM_OBJ_SourceDomainName");
MESSAGE= "Smarts notification: ${CLASS_NAME} ${INSTANCE_NAME} ${EVENT_NAME}"
DESCRIPTION= "SM_OBJ_ClassName: ${CLASS_NAME} ${INSTANCE_NAME}:${INSTANCE_NAME}: ${EVENT_NAME}:${EVENT_NAME}"
notificationAttributesListToBeAddedToAlertDetails = [:]
/**************************************************************/



/**********************************HTML TOPOLOGY OBJECT DETAILS CONFIGS*******************/
severityRowColorMap = [:]
severityLabelMap = [:]
severityRowColorMap["1"] = "background-color: rgb(255, 0, 0); color: rgb(255, 255, 255);";
severityRowColorMap["2"] = "background-color: rgb(255, 117, 20); color: rgb(255, 255, 255);";
severityRowColorMap["3"] = "background-color: rgb(221, 199, 0); color: rgb(0, 0, 0);";
severityRowColorMap["4"] = "background-color: rgb(45, 191, 205); color: rgb(0, 0, 0);";
severityRowColorMap["5"] = "background-color: rgb(0, 255, 0); color: rgb(0, 0, 0);";

severityLabelMap["1"] = "Critical";
severityLabelMap["2"] = "Major";
severityLabelMap["3"] = "Minor";
severityLabelMap["4"] = "Unknown";
severityLabelMap["5"] = "Normal";
notificationAttributeNames = ["Severity":"Severity", "Active":"Active", "Owner":"Owner",
        "ElementClassName":"Device Type", "ElementName":"Device Name", "InstanceName":"Part Name",
        "EventText":"Event Text", "LastNotifiedAt":"Last Notify", "LastClearedAt":"Last Clear", "OccurrenceCount":"Count", "TroubleTicketID":"Case ID"]
dateAttributes = [LastNotifiedAt:true, LastChangedAt:true]
df = new SimpleDateFormat("dd MMM HH:mm:ss")
/*****************************************************************/


def alertProps = [:]
alertProps.message = MESSAGE
alertProps.recipients = RECIPIENTS
alertProps.description = DESCRIPTION
def details = [:]
notificationAttributesListToBeAddedToAlertDetails.each{smartsAttributeName, detailsPropName->
    details[detailsPropName] = String.valueOf(System.getenv().get(smartsAttributeName));
}
if(details){
    alertProps.details = details;
}

logger.warn("Creating alert with message ${alertProps.message}");
println "Creating alert"
def response = opsgenie.createAlert(alertProps)
def alertId =  response.alertId;
logger.warn("Alert is created with id :"+alertId);
println "Alert is created with id :"+alertId
attach(alertId, CLASS_NAME, INSTANCE_NAME)
if(ELEMENT_CLASS_NAME != "" && ELEMENT_INSTANCE_NAME != "" && (CLASS_NAME != ELEMENT_CLASS_NAME || INSTANCE_NAME != ELEMENT_INSTANCE_NAME)){
    attach(alertId, ELEMENT_CLASS_NAME, ELEMENT_INSTANCE_NAME)
}




def attach(alertId, className, instanceName){
    String htmlText = createHtml(className, instanceName);
    if(htmlText){
        logger.warn("Attaching ${className}:${instanceName} details");
        println "Attaching ${className}:${instanceName} details"
        response = opsgenie.attach([alertId:alertId, stream:new ByteArrayInputStream(htmlText.getBytes()), fileName:"${className}_${instanceName}.html"])
        if(response.success){
            logger.warn("Successfully attached ${className}:${instanceName} details");
            println "Successfully attached ${className}:${instanceName} details"
        }
        else{
            println "Could not attach ${className}:${instanceName} details"
            logger.warn("Could not attach ${className}:${instanceName} details");
        }
    }
    else{
        logger.warn("No Object found for ${className}:${instanceName}");
        println "No Object found for ${className}:${instanceName}"
    }
}

def createHtml(String className, String instanceName){
    StringBuffer bfr = new StringBuffer();
    bfr.append("""
        <html>
        <head>
            <title>Details of ${className}::${instanceName}</title>
            <style>
                table{
                    border:1px solid #cccccc;
                }

                table thead tr{
                    background-color: #F2F2F2;
                    text-align:left;
                }
                table thead tr th{
                    padding-top:8px;
                    padding-bottom:8px;
                }
                .odd{
                    background: none repeat scroll 0 0 #F9F9F9;
                }
                .well{
                    border: 1px solid #C0C0C0;
                    border-radius: 4px 4px 4px 4px;
                    padding: 5px;
                    margin:10px;
                }
                .lbl{
                    padding-right:30px;
                    font-weight:bold;
                }
                .well .heading{
                    border: 1px solid #212121;
                    color: white;
                    background-color: #323232;
                    border-radius: 4px 4px 0 0;
                    display: block;
                    font-size: 1.8rem;
                    min-width: 0;
                    padding: 15px;
                    position: relative;
                    z-index: 1;
                }
                .well .heading h2{
                    color: white;
                    font-size: 0.7em;
                    font-weight: normal;
                    margin: 0;
                    position: relative;
                    width: 87%;
                }
            </style>
        </head>
        <body>
        <div class="well">
        <div class="heading">
            <h2>${className}::${instanceName} Properties</h2>
        </div>
        <table cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th width="0%"><span>Property Name</span></th>
                    <th width="100%">Property Value</th>
                </tr>
            </thead>
            <tbody>
    """)
    def connParams = [broker: BROKER_NAME, domain: SOURCE_DOMAIN_NAME, password: SMARTS_PASSWORD, username: SMARTS_USERNAME];
    Map topologyAttributes = null;
    try{
        topologyAttributes = getTopologyObjectProperties(connParams, className, instanceName);
    }
    catch (Throwable t){
        if(t.toString().indexOf("is not registered with the broker") >= 0){
            connParams = [broker: BROKER_NAME, domain: DOMAIN_NAME, password: SMARTS_PASSWORD, username: SMARTS_USERNAME];
            try{
                topologyAttributes = getTopologyObjectProperties(connParams, className, instanceName);
            }
            catch(Throwable t1){
                if(t1.toString().indexOf("Object of given name and class not found") >= 0){
                    return null;
                }
                else{
                    throw t1;
                }
            }

        }
        else{
            throw t;
        }
    }
    def rowCount = 0;
    topologyAttributes.keySet().sort().each{String attributeName->
        rowCount++;
        StringBuffer valueBfr = new StringBuffer();
        def val = topologyAttributes[attributeName];
        if(val instanceof List){
            val.each{entry->
                valueBfr.append("""<div>${StringEscapeUtils.escapeHtml(entry.creationClassName)}::${StringEscapeUtils.escapeHtml(entry.instanceName)}</div>""")
            }
        }
        else if(val instanceof Map){
            valueBfr.append("""<div>${StringEscapeUtils.escapeHtml(val.creationClassName)}::${StringEscapeUtils.escapeHtml(val.instanceName)}</div>""")
        }
        else{
            valueBfr.append(StringEscapeUtils.escapeHtml(String.valueOf(val)))
        }
        bfr.append("""
        <tr ${rowCount%2==0?"class='odd'":""}>
            <td class="lbl">${StringEscapeUtils.escapeHtml(attributeName)}</td>
            <td>${valueBfr}</td>
        </tr>
    """)
    }
    bfr.append("""
        </tbody>
        </table>
        </div>
        <div class="well">
        <div class="heading">
            <h2>Notifications</h2>
        </div>
        <table width="100%"  cellspacing="0">
            <thead>
            <tr>
    """)
notificationAttributeNames.each{attributeName, attributeLabel->
    bfr.append("""<th>${attributeLabel}</th>""")

}
bfr.append("""
</tr>
    </thead>
    <tbody>
""")
    rowCount = 0;
    connParams = [broker: BROKER_NAME, domain: DOMAIN_NAME, password: SMARTS_PASSWORD, username: SMARTS_USERNAME];
    SmartsDatasource.execute(connParams){ds->
        List<Map> notifications = ds.getNotifications(className, instanceName, ".*");
        notifications.each{notificationAttributes->
            StringBuffer columnBfr = new StringBuffer();
            notificationAttributeNames.each{attributeName, attributeLabel->
                def val = notificationAttributes[attributeName];
                if(attributeName == "Severity"){
                    val = severityLabelMap[String.valueOf(val)]
                }
                else if(dateAttributes.containsKey(attributeName)){
                    val = df.format(new Date(val*1000l))
                }
                columnBfr.append("""<td>${StringEscapeUtils.escapeHtml(String.valueOf(val))}</td>""")
            }
            bfr.append("""
            <tr style="${severityRowColorMap[String.valueOf(notificationAttributes.Severity)]}">${columnBfr}</tr>
        """);
        }
    }

    bfr.append("""
        </tbody>
        </table>
        </div>
    </body>
    </html>
""")
}

def getTopologyObjectProperties(connParams, className, instanceName){
    return SmartsDatasource.execute(connParams){ds->
        return ds.getAllProperties(className, instanceName)
    }
}


