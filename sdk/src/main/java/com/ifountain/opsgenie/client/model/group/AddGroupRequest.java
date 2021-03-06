package com.ifountain.opsgenie.client.model.group;

import com.ifountain.opsgenie.client.OpsGenieClientConstants;
import com.ifountain.opsgenie.client.OpsGenieClientValidationException;
import com.ifountain.opsgenie.client.model.BaseRequest;

import java.util.List;
import java.util.Map;

/**
 * Container for the parameters to make an add group api call.
 *
 * @see com.ifountain.opsgenie.client.IGroupOpsGenieClient#addGroup(com.ifountain.opsgenie.client.model.group.AddGroupRequest)
 */
public class AddGroupRequest extends BaseRequest<AddGroupResponse> {
    private String name;
    private List<String> users;


    /**
     * Rest api uri of addding group operation.
     */
    @Override
    public String getEndPoint() {
        return "/v1/json/group";
    }

    /**
     * Name of group
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of group
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Users of group
     */
    public List<String> getUsers() {
        return users;
    }

    /**
     * Sets users of group
     */
    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    /**
     * @see com.ifountain.opsgenie.client.model.BaseRequest#serialize()
     */
    public Map serialize() throws OpsGenieClientValidationException {
        Map json = super.serialize();
        if(getName() != null)
        {
            json.put(OpsGenieClientConstants.API.NAME, getName());
        }
        if(getUsers() != null){
            json.put(OpsGenieClientConstants.API.USERS, getUsers());
        }
        return json;
    }

    @Override
    /**
     * @see com.ifountain.opsgenie.client.model.BaseRequest#createResponse()
     */
    public AddGroupResponse createResponse() {
        return new AddGroupResponse();
    }
}
