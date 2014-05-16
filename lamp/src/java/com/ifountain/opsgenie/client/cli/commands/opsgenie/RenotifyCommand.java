package com.ifountain.opsgenie.client.cli.commands.opsgenie;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.ifountain.client.ClientConstants;
import com.ifountain.client.model.IClient;
import com.ifountain.client.opsgenie.IOpsGenieClient;
import com.ifountain.client.opsgenie.model.alert.RenotifyRequest;
import com.ifountain.client.opsgenie.model.beans.RenotifyRecipient;
import com.ifountain.client.util.Strings;
import com.ifountain.opsgenie.client.cli.commands.BaseCommand;
import com.ifountain.opsgenie.client.cli.commands.CommonCommandOptions;
import com.ifountain.opsgenie.client.cli.commands.NullSplitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sezgin Kucukkaraaslan
 * @version 11/26/12 4:57 PM
 */

@Parameters(commandDescription = "Renotifies recipients at OpsGenie.")
public class RenotifyCommand extends BaseCommand {
    @ParametersDelegate
    private CommonCommandOptions commonOptions = new CommonCommandOptions();

    @Parameter(names = "--" + ClientConstants.API.ALERT_ID, description = "Id of the alert that recipient will be renotified for. Either this or alias should be given.", splitter = NullSplitter.class)
    private String alertId;

    @Parameter(names = "--" + ClientConstants.API.ALIAS, description = "Alias of the alert that recipient will be renotified for. Either this or alertId should be given.", variableArity = true, splitter = NullSplitter.class)
    private List<String> alias;

    @Parameter(names = "--" + ClientConstants.API.NOTE, description = "User note.", variableArity = true, splitter = NullSplitter.class)
    private List<String> note;

    @Parameter(names = "--" + ClientConstants.API.RECIPIENTS, description = "The user names of individual users or names of groups that will be renotified for alert.", variableArity = true, splitter = NullSplitter.class)
    private List<String> recipients;

    @Parameter(names = "--" + ClientConstants.API.SOURCE, description = "Source of action.", variableArity = true, splitter = NullSplitter.class)
    private List<String> source;

    public RenotifyCommand(JCommander commander) {
        super(commander);
    }

    @Override
    public String getName() {
        return "renotify";
    }

    @Override
    protected CommonCommandOptions getCommonCommandOptions() {
        return commonOptions;
    }

    @Override
    public void doExecute(IClient client) throws Exception {
        RenotifyRequest request = new RenotifyRequest();
        request.setApiKey(commonOptions.getApiKey());
        request.setId(alertId);
        if (alias != null) request.setAlias(Strings.join(alias, " "));
        if (note != null) request.setNote(Strings.join(note, " "));
        if (source != null) request.setSource(Strings.join(source, " "));
        if (commonOptions.getUser() != null) request.setUser(commonOptions.getUser());
        if (recipients != null){
            List<String> recipientList = Arrays.asList(Strings.join(recipients, " ").split(","));
            List<RenotifyRecipient> recipientBeans = new ArrayList<RenotifyRecipient>();
            for(String recipientName:recipientList){
                RenotifyRecipient recipientObject = new RenotifyRecipient();
                recipientObject.setRecipient(recipientName.trim());
                recipientBeans.add(recipientObject);
            }
            request.setRecipients(recipientBeans);
        }
        ((IOpsGenieClient)client).alert().renotify(request);
    }
}