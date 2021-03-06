package com.ifountain.opsgenie.client.model.escalation;

import com.ifountain.opsgenie.client.model.BaseRequest;

/**
 * Container for the parameters to make a list escalations api call.
 *
 * @see com.ifountain.opsgenie.client.IEscalationOpsGenieClient#listEscalations(ListEscalationsRequest)
 */
public class ListEscalationsRequest extends BaseRequest<ListEscalationsResponse> {
    /**
     * Rest api uri of listing escalations operation.
     */
    @Override
    public String getEndPoint() {
        return "/v1/json/escalation";
    }

    @Override
    /**
     * @see com.ifountain.opsgenie.client.model.BaseRequest#createResponse()
     */
    public ListEscalationsResponse createResponse() {
        return new ListEscalationsResponse();
    }
}
