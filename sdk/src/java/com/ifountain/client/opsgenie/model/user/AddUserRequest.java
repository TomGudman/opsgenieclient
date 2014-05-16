package com.ifountain.client.opsgenie.model.user;

import com.ifountain.client.ClientConstants;
import com.ifountain.client.ClientValidationException;
import com.ifountain.client.model.BaseRequest;
import com.ifountain.client.opsgenie.model.beans.User;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Container for the parameters to make an add user api call.
 *
 * @author Mustafa Sener
 * @version 09.04.2013 17:03
 * @see com.ifountain.client.opsgenie.IUserOpsGenieClient#addUser(AddUserRequest)
 */
public class AddUserRequest extends BaseRequest<AddUserResponse> {
    private String username;
    private String fullname;
    private TimeZone timeZone;
    private Locale locale;
    private User.Role role;


    /**
     * Rest api uri of addding user operation.
     */
    @Override
    public String getEndPoint() {
        return "/v1/json/user";
    }

    /**
     * Username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username of user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Fullname of user
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Sets fullname of user
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * TimeZone of user
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Sets timezone of user
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Locale of user
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets locale of user
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Role of user
     * @see com.ifountain.client.opsgenie.model.beans.User.Role
     */
    public User.Role getRole() {
        return role;
    }

    /**
     * Sets role of user
     * @see com.ifountain.client.opsgenie.model.beans.User.Role
     */
    public void setRole(User.Role role) {
        this.role = role;
    }

    @Override
    /**
     * @see com.ifountain.client.model.BaseRequest#serialize()
     */
    public Map<String, Object> serialize() throws ClientValidationException {
        Map<String, Object> json = super.serialize();
        if(getUsername() != null){
            json.put(ClientConstants.API.USERNAME, getUsername());
        }
        if(getFullname() != null){
            json.put(ClientConstants.API.FULLNAME, getFullname());
        }
        if(getRole() != null){
            json.put(ClientConstants.API.ROLE, getRole().name());
        }
        if(getTimeZone() != null){
            json.put(ClientConstants.API.TIMEZONE, getTimeZone().getID());
        }
        if(getLocale() != null){
            json.put(ClientConstants.API.LOCALE, User.getLocaleId(getLocale()));
        }
        return json;
    }

    @Override
    /**
     * @see com.ifountain.client.model.BaseRequest#createResponse()
     */
    public AddUserResponse createResponse() {
        return new AddUserResponse();
    }
}