package com.ifountain.client.statussiren;

import com.ifountain.client.ClientConstants;
import com.ifountain.client.ClientException;
import com.ifountain.client.http.HttpClient;
import com.ifountain.client.http.JsonHttpClient;
import com.ifountain.client.statussiren.model.incident.*;
import com.ifountain.client.util.ClientConfiguration;

import java.io.IOException;
import java.text.ParseException;

/**
 * Provides the client for accessing the StatusSiren.
 * <p/>
 * <p><code>StatusSirenClient</code> class provides the implementation APIs for StatusSiren operations like creating, resolving and getting incidents,
 * etc. All service calls made using this client are blocking, and will not return until the service call completes.</p>
 * <p/>
 * <h4>Creating an Incident</h4>
 * <p>Construct a <code>CreateIncidentRequest</code> object with preferred options and call <code>createIncident</code> method on client.</p>
 * <p><blockquote><pre>
 * StatusSirenClient client = new StatusSirenClient();
 * CreateIncidentRequest request = new CreateIncidentRequest();
 * request.setApiKey("ab5454992-fabb2-4ba2-ad44f-1af65ds8b5c079");
 * request.setService("service1");
 * request.setMessage("appserver1 down");
 * request.setDescription("cpu usage is over 60%");
 * CreateIncidentResponse response = client.createIncident(request);
 * long incidentId = response.getIncidentId();
 *
 * @author Tuba Ozturk
 * @version 25.4.2014 08:46
 */
public class StatusSirenClient implements IStatusSirenClient {
    /**
     * Http client object
     */
    private JsonHttpClient jsonHttpClient;

    /**
     * Constructs a new client to invoke service methods on OpsGenie using the specified client configuration options.
     */
    public StatusSirenClient(ClientConfiguration config) {
        this(new HttpClient(config));
    }

    /**
     * Constructs a new client to invoke service methods on StatusSiren.
     */
    public StatusSirenClient(){
        this(new HttpClient());
    }

    /**
     * Constructs a new client to invoke service methods on StatusSiren using the specified client.
     */
    public StatusSirenClient(HttpClient httpClient) {
        this.jsonHttpClient = new JsonHttpClient(httpClient);
        setRootUri(ClientConstants.STATUSSIREN_API_URI);
    }

    /**
     * @see com.ifountain.client.statussiren.IStatusSirenClient#createIncident(com.ifountain.client.statussiren.model.incident.CreateIncidentRequest)
     */
    @Override
    public CreateIncidentResponse createIncident(CreateIncidentRequest createIncidentRequest) throws IOException, ClientException, ParseException {
        return (CreateIncidentResponse) jsonHttpClient.doPostRequest(createIncidentRequest);
    }

    /**
     * @see com.ifountain.client.statussiren.IStatusSirenClient#resolveIncident(com.ifountain.client.statussiren.model.incident.ResolveIncidentRequest)
     */
    @Override
    public ResolveIncidentResponse resolveIncident(ResolveIncidentRequest resolveIncidentRequest) throws ParseException, ClientException, IOException {
        return (ResolveIncidentResponse) jsonHttpClient.doPostRequest(resolveIncidentRequest);
    }

    /**
     * @see com.ifountain.client.statussiren.IStatusSirenClient#deleteIncident(com.ifountain.client.statussiren.model.incident.DeleteIncidentRequest)
     */
    @Override
    public DeleteIncidentResponse deleteIncident(DeleteIncidentRequest deleteIncidentRequest) throws ParseException, ClientException, IOException {
        return (DeleteIncidentResponse) jsonHttpClient.doDeleteRequest(deleteIncidentRequest);
    }

    /**
     * @see com.ifountain.client.statussiren.IStatusSirenClient#updateIncident(com.ifountain.client.statussiren.model.incident.UpdateIncidentRequest)
     */
    @Override
    public UpdateIncidentResponse updateIncident(UpdateIncidentRequest updateIncidentRequest) throws ParseException, ClientException, IOException {
        return (UpdateIncidentResponse) jsonHttpClient.doPostRequest(updateIncidentRequest);
    }

    /**
     * @see com.ifountain.client.statussiren.IStatusSirenClient#getIncident(com.ifountain.client.statussiren.model.incident.GetIncidentRequest)
     */
    @Override
    public GetIncidentResponse getIncident(GetIncidentRequest getIncidentRequest) throws ParseException, ClientException, IOException {
        return (GetIncidentResponse) jsonHttpClient.doGetRequest(getIncidentRequest);
    }

    /**
     * @see com.ifountain.client.statussiren.IStatusSirenClient#listIncidents(com.ifountain.client.statussiren.model.incident.ListIncidentsRequest)
     */
    @Override
    public ListIncidentsResponse listIncidents(ListIncidentsRequest listIncidentsRequest) throws ParseException, ClientException, IOException {
        return (ListIncidentsResponse) jsonHttpClient.doGetRequest(listIncidentsRequest);
    }

    /**
     * Set root endpoint uri that the client uses to send http requests.Default is https://api.statussiren.com.
     *
     * @param rootUri Uri to set.
     */
    @Override
    public void setRootUri(String rootUri) {
        this.jsonHttpClient.setRootUri(rootUri);
    }

    /**
     * Closes client
     */
    @Override
    public void close() {
        this.jsonHttpClient.close();
    }

    /**
     * Api key used for authenticating API requests.
     */
    public String getApiKey(){
        return this.jsonHttpClient.getApiKey();
    }

    /**
     * Sets the api key used for authenticating API requests.
     */
    public void setApiKey(String apiKey){
        this.jsonHttpClient.setApiKey(apiKey);
    }
}