package com.ifountain.client.opsgenie;

import com.ifountain.client.ClientConstants;
import com.ifountain.client.ClientException;
import com.ifountain.client.http.JsonHttpClient;
import com.ifountain.client.opsgenie.model.InputStreamAttachRequest;
import com.ifountain.client.opsgenie.model.alert.*;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;

/**
 * Inner Alert Client
 *
 * @author Mustafa Sener
 * @version 09.04.2013 11:39
 * @see com.ifountain.client.opsgenie.IAlertOpsGenieClient
 */
public class InnerAlertOpsGenieClient implements IAlertOpsGenieClient{
    private JsonHttpClient httpClient;
    /**
     * Constructs a new alert client to invoke service methods on OpsGenie for alerts using the specified client and root URI.
     */
    public InnerAlertOpsGenieClient(JsonHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * @see IAlertOpsGenieClient#createAlert(com.ifountain.client.opsgenie.model.alert.CreateAlertRequest)
     */
    @Override
    public CreateAlertResponse createAlert(CreateAlertRequest createAlertRequest) throws IOException, ClientException, ParseException {
        return (CreateAlertResponse) httpClient.doPostRequest(createAlertRequest);
    }

    /**
     * @see IAlertOpsGenieClient#closeAlert(com.ifountain.client.opsgenie.model.alert.CloseAlertRequest)
     */
    @Override
    public CloseAlertResponse closeAlert(CloseAlertRequest closeAlertRequest) throws ClientException, IOException, ParseException {
        return (CloseAlertResponse) httpClient.doPostRequest(closeAlertRequest);
    }

    /**
     * @see IAlertOpsGenieClient#deleteAlert(com.ifountain.client.opsgenie.model.alert.DeleteAlertRequest)
     */
    @Override
    public DeleteAlertResponse deleteAlert(DeleteAlertRequest deleteAlertRequest) throws ClientException, IOException, ParseException {
        return (DeleteAlertResponse) httpClient.doDeleteRequest(deleteAlertRequest);
    }

    /**
     * @see IAlertOpsGenieClient#getAlert(com.ifountain.client.opsgenie.model.alert.GetAlertRequest)
     */
    @Override
    public GetAlertResponse getAlert(GetAlertRequest getAlertRequest) throws ClientException, IOException, ParseException {
        return (GetAlertResponse) httpClient.doGetRequest(getAlertRequest);
    }

    /**
     * @see IAlertOpsGenieClient#listAlerts(com.ifountain.client.opsgenie.model.alert.ListAlertsRequest)
     */
    @Override
    public ListAlertsResponse listAlerts(ListAlertsRequest listAlertsRequest) throws ClientException, IOException, ParseException {
        return (ListAlertsResponse) httpClient.doGetRequest(listAlertsRequest);
    }

    /**
     * @see IAlertOpsGenieClient#addNote(com.ifountain.client.opsgenie.model.alert.AddNoteRequest)
     */
    @Override
    public AddNoteResponse addNote(AddNoteRequest addNoteRequest) throws ClientException, IOException, ParseException {
        return (AddNoteResponse) httpClient.doPostRequest(addNoteRequest);
    }

    /**
     * @see IAlertOpsGenieClient#acknowledge(com.ifountain.client.opsgenie.model.alert.AcknowledgeRequest)
     */
    @Override
    public AcknowledgeResponse acknowledge(AcknowledgeRequest acknowledgeRequest) throws ClientException, IOException, ParseException {
        return (AcknowledgeResponse) httpClient.doPostRequest(acknowledgeRequest);
    }

    /**
     * @see IAlertOpsGenieClient#renotify(com.ifountain.client.opsgenie.model.alert.RenotifyRequest)
     */
    @Override
    public RenotifyResponse renotify(RenotifyRequest renotifyRequest) throws ClientException, IOException, ParseException {
        return (RenotifyResponse) httpClient.doPostRequest(renotifyRequest);
    }

    /**
     * @see IAlertOpsGenieClient#takeOwnership(com.ifountain.client.opsgenie.model.alert.TakeOwnershipRequest)
     */
    @Override
    public TakeOwnershipResponse takeOwnership(TakeOwnershipRequest takeOwnershipRequest) throws ClientException, IOException, ParseException {
        return (TakeOwnershipResponse) httpClient.doPostRequest(takeOwnershipRequest);
    }

    /**
     * @see IAlertOpsGenieClient#assign(com.ifountain.client.opsgenie.model.alert.AssignRequest)
     */
    @Override
    public AssignResponse assign(AssignRequest assignRequest) throws ClientException, IOException, ParseException {
        return (AssignResponse) httpClient.doPostRequest(assignRequest);
    }

    /**
     * @see IAlertOpsGenieClient#addRecipient(com.ifountain.client.opsgenie.model.alert.AddRecipientRequest)
     */
    @Override
    public AddRecipientResponse addRecipient(AddRecipientRequest addRecipientRequest) throws ClientException, IOException, ParseException {
        return (AddRecipientResponse) httpClient.doPostRequest(addRecipientRequest);
    }



    /**
     * @see IAlertOpsGenieClient#attach(com.ifountain.client.opsgenie.model.alert.FileAttachRequest)
     */
    @Override
    public AttachResponse attach(FileAttachRequest attachRequest) throws ClientException, IOException, ParseException {
        FileInputStream in = attachRequest.getFile() != null ? new FileInputStream(attachRequest.getFile()) : null;
        String fileName = attachRequest.getFile() != null ? attachRequest.getFile().getName() : null;
        return _attach(attachRequest, in, fileName);
    }

    /**
     * @see IAlertOpsGenieClient#attach(com.ifountain.client.opsgenie.model.InputStreamAttachRequest)
     */
    @Override
    public AttachResponse attach(InputStreamAttachRequest attachRequest) throws ClientException, IOException, ParseException {
        return _attach(attachRequest, attachRequest.getInputStream(), attachRequest.getFileName());
    }

    /**
     * @see IAlertOpsGenieClient#listAlertLogs(com.ifountain.client.opsgenie.model.alert.ListAlertLogsRequest)
     */
    @Override
    public ListAlertLogsResponse listAlertLogs(ListAlertLogsRequest listAlertLogsRequest) throws ParseException, ClientException, IOException {
        return (ListAlertLogsResponse) httpClient.doGetRequest(listAlertLogsRequest);
    }

    /**
     * @see IAlertOpsGenieClient#listAlertRecipients(com.ifountain.client.opsgenie.model.alert.ListAlertRecipientsRequest)
     */
    @Override
    public ListAlertRecipientsResponse listAlertRecipients(ListAlertRecipientsRequest listAlertRecipientsRequest) throws ParseException, ClientException, IOException {
        return (ListAlertRecipientsResponse) httpClient.doGetRequest(listAlertRecipientsRequest);
    }

    /**
     * @see IAlertOpsGenieClient#executeAlertAction(com.ifountain.client.opsgenie.model.alert.ExecuteAlertActionRequest)
     */
    @Override
    public ExecuteAlertActionResponse executeAlertAction(ExecuteAlertActionRequest executeAlertActionRequest) throws ClientException, IOException, ParseException {
        return (ExecuteAlertActionResponse) httpClient.doPostRequest(executeAlertActionRequest);
    }

    private AttachResponse _attach(AttachRequest attachRequest, InputStream inputStream, String fileName) throws IOException, ClientException, ParseException {
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (inputStream != null && fileName != null)
            entity.addPart(ClientConstants.API.ATTACHMENT, new ByteArrayBody(convertInputStreamToByteArray(inputStream), new File(fileName).getName()));
        if (attachRequest.getApiKey() != null)
            entity.addPart(ClientConstants.API.API_KEY, new StringBody(attachRequest.getApiKey(), "text/plain", Charset.forName("utf-8")));
        if (attachRequest.getId() != null)
            entity.addPart(ClientConstants.API.ID, new StringBody(attachRequest.getId(), "text/plain", Charset.forName("utf-8")));
        if (attachRequest.getAlias() != null)
            entity.addPart(ClientConstants.API.ALIAS, new StringBody(attachRequest.getAlias(), "text/plain", Charset.forName("utf-8")));
        if (attachRequest.getTinyId() != null)
            entity.addPart(ClientConstants.API.TINY_ID, new StringBody(attachRequest.getTinyId(), "text/plain", Charset.forName("utf-8")));
        if (attachRequest.getIndexFile() != null)
            entity.addPart(ClientConstants.API.INDEX_FILE, new StringBody(attachRequest.getIndexFile(), "text/plain", Charset.forName("utf-8")));
        if (attachRequest.getUser() != null)
            entity.addPart(ClientConstants.API.USER, new StringBody(attachRequest.getUser(), "text/plain", Charset.forName("utf-8")));
        if (attachRequest.getNote() != null)
            entity.addPart(ClientConstants.API.NOTE, new StringBody(attachRequest.getNote(), "text/plain", Charset.forName("utf-8")));
        return (AttachResponse) httpClient.doPostRequest(attachRequest, entity);
    }

    /*this is required to fix proxy authentication retry failure
    *caused by org.apache.http.client.NonRepeatableRequestException: Cannot retry request with a non-repeatable request entity
    */
    protected byte[] convertInputStreamToByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int c = -1;
        while ((c = in.read()) != -1) {
            outputStream.write(c);
        }
        outputStream.flush();
        in.close();
        return outputStream.toByteArray();
    }
}