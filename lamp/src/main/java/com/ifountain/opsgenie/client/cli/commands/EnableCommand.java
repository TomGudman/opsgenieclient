package com.ifountain.opsgenie.client.cli.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.ifountain.opsgenie.client.IOpsGenieClient;
import com.ifountain.opsgenie.client.OpsGenieClientConstants;
import com.ifountain.opsgenie.client.model.alertpolicy.EnableAlertPolicyRequest;
import com.ifountain.opsgenie.client.model.integration.EnableIntegrationRequest;
import com.ifountain.opsgenie.client.util.Strings;

import java.util.List;

/**
 * Created by Sezgin Kucukkaraaslan
 * Date: 6/4/12
 * Time: 9:46 AM
 */
@Parameters(commandDescription = "Enables Opsgenie Integration and Policy.")
public class EnableCommand extends BaseCommand {
    @Parameter(names = "--" + OpsGenieClientConstants.API.TYPE, description = "Should be one of [integration, policy]", splitter = NullSplitter.class)
    private List<String> type;
    @Parameter(names = "--" + OpsGenieClientConstants.API.NAME, description = "Name of integration or policy", variableArity = true, splitter = NullSplitter.class)
    private List<String> name;
    @Parameter(names = "--" + OpsGenieClientConstants.API.ID, description = "Id of integration or policy", variableArity = true, splitter = NullSplitter.class)
    private List<String> id;

    @ParametersDelegate
    private CommonCommandOptions commonOptions = new CommonCommandOptions();

    public EnableCommand(JCommander commander) {
        super(commander);
    }


    protected boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "enable";
    }

    @Override
    public void doExecute(IOpsGenieClient opsGenieClient) throws Exception {
        String typeStr = null;
        if (type != null) typeStr = Strings.join(type, " ");
        if (OpsGenieClientConstants.API.INTEGRATION.equals(typeStr)) {
            EnableIntegrationRequest request = new EnableIntegrationRequest();
            request.setEnabled(isEnabled());
            if (name != null) request.setName(Strings.join(name, " "));
            if (id != null) request.setId(Strings.join(id, " "));
            request.setApiKey(getCommonCommandOptions().getApiKey());
            opsGenieClient.integration().enableIntegration(request);
            if (isEnabled())
                System.out.println("Integration enabled");
            else
                System.out.println("Integration disabled");
        } else if (OpsGenieClientConstants.API.POLICY.equals(typeStr)) {
            EnableAlertPolicyRequest request = new EnableAlertPolicyRequest();
            request.setEnabled(isEnabled());
            if (name != null) request.setName(Strings.join(name, " "));
            if (id != null) request.setId(Strings.join(id, " "));
            request.setApiKey(getCommonCommandOptions().getApiKey());
            opsGenieClient.alertPolicy().enableAlertPolicy(request);
            if (isEnabled())
                System.out.println("Policy enabled");
            else
                System.out.println("Policy disabled");
        } else {
            throw new Exception("Invalid type [" + typeStr + "]. Should be one of [integration, policy]");
        }
    }

    @Override
    protected CommonCommandOptions getCommonCommandOptions() {
        return commonOptions;
    }
}
