package com.ifountain.client.opsgenie.model.beans;

import com.ifountain.client.ClientConstants;
import com.ifountain.client.model.IBean;

import java.util.*;

/**
 * HeartBean bean
 *
 * @author Mustafa Sener
 * @version 30.04.2013 22:36
 */
public class Heartbeat implements IBean {
    private String source;
    private Date lastHeartbeat;
    private boolean expired;

    /*
    * Source of heartbeat monitor
     */
    public String getSource() {
        return source;
    }

    /*
    * Sets source of heartbeat monitor
     */
    public void setSource(String source) {
        this.source = source;
    }

    /*
    * Last heartbeat recieve time
     */
    public Date getLastHeartbeat() {
        return lastHeartbeat;
    }

    /*
    * Sets last heartbeat receieve time
     */
    public void setLastHeartbeat(Date lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    /*
    * Returns true if heartbeat is expired
     */
    public boolean isExpired() {
        return expired;
    }

    /*
    * Set expired state of heartbeat
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public Map<String,Object> toMap() {
        Map<String,Object> json = new HashMap<String,Object>();
        json.put(ClientConstants.API.SOURCE, source);
        json.put(ClientConstants.API.LAST_HEARTBEAT, lastHeartbeat);
        json.put(ClientConstants.API.EXPIRED, expired);
        return json;
    }

    @Override
    public void fromMap(Map<String,Object> resp) {
        setSource((String) resp.get(ClientConstants.API.SOURCE));
        setLastHeartbeat(new Date(((Number) resp.get(ClientConstants.API.LAST_HEARTBEAT)).longValue()));
        setExpired((Boolean) resp.get(ClientConstants.API.EXPIRED));
    }
}