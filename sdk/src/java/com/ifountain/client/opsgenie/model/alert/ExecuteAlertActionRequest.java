package com.ifountain.client.opsgenie.model.alert;

import com.ifountain.client.ClientConstants;
import com.ifountain.client.ClientValidationException;

import java.util.Map;

/**
 * Container for the parameters to make an execute alert action api call.
 *
 * @author Sezgin Kucukkaraaslan
 * @version 10/30/12 4:59 PM
 * @see com.ifountain.client.opsgenie.IAlertOpsGenieClient#executeAlertAction(ExecuteAlertActionRequest)
 */
public class ExecuteAlertActionRequest extends BaseAlertRequestWithSource<ExecuteAlertActionResponse> {
    private String action;
    private String user;
    private String note;

    /**
     * Rest api uri of execute alert action operation.
     */
    @Override
    public String getEndPoint() {
        return "/v1/json/alert/executeAction";
    }

    /**
     * The action to be executed.
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the action to be executed.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * The user who is performing the execute alert action operation.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user who is performing the execute alert action operation.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Additional alert note.
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets additional alert note.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @see com.ifountain.client.model.BaseRequest#serialize()
     */
    public Map<String,Object> serialize() throws ClientValidationException {
        Map<String,Object> json = super.serialize();
        json.put(ClientConstants.API.ACTION, getAction());
        if (getUser() != null)
            json.put(ClientConstants.API.USER, getUser());
        if (getNote() != null)
            json.put(ClientConstants.API.NOTE, getNote());
        return json;
    }

    @Override
    /**
     * @see com.ifountain.client.model.BaseRequest#createResponse()
     */
    public ExecuteAlertActionResponse createResponse() {
        return new ExecuteAlertActionResponse();
    }
}