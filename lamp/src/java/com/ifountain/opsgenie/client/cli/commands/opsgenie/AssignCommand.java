package com.ifountain.opsgenie.client.cli.commands.opsgenie;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.ifountain.client.ClientConstants;
import com.ifountain.client.model.IClient;
import com.ifountain.client.opsgenie.IOpsGenieClient;
import com.ifountain.client.opsgenie.model.alert.AssignRequest;
import com.ifountain.client.util.Strings;
import com.ifountain.opsgenie.client.cli.commands.BaseCommand;
import com.ifountain.opsgenie.client.cli.commands.CommonCommandOptions;
import com.ifountain.opsgenie.client.cli.commands.NullSplitter;

import java.util.List;

/**
 * @author Sezgin Kucukkaraaslan
 * @version 11/26/12 5:00 PM
 */
@Parameters(commandDescription = "Assigns the ownership of an alert to the specified user.")
public class AssignCommand extends BaseCommand {
    @ParametersDelegate
    private CommonCommandOptions commonOptions = new CommonCommandOptions();

    @Parameter(names = "--" + ClientConstants.API.ALERT_ID, description = "Id of the alert that will be assigned. Either this or alias should be given.", splitter = NullSplitter.class)
    private String alertId;

    @Parameter(names = "--" + ClientConstants.API.ALIAS, description = "Alias of the alert that will be assigned. Either this or alertId should be given.", variableArity = true, splitter = NullSplitter.class)
    private List<String> alias;

    @Parameter(names = "--" + ClientConstants.API.OWNER, description = "The user who will be the owner of the alert after the execution.", variableArity = true, splitter = NullSplitter.class)
    private List<String> owner;

    @Parameter(names = "--" + ClientConstants.API.NOTE, description = "User note.", variableArity = true, splitter = NullSplitter.class)
    private List<String> note;

    @Parameter(names = "--" + ClientConstants.API.SOURCE, description = "Source of action.", variableArity = true, splitter = NullSplitter.class)
    private List<String> source;

    public AssignCommand(JCommander commander) {
        super(commander);
    }

    @Override
    public String getName() {
        return "assign";
    }

    @Override
    protected CommonCommandOptions getCommonCommandOptions() {
        return commonOptions;
    }

    @Override
    public void doExecute(IClient client) throws Exception {
        AssignRequest request = new AssignRequest();
        request.setApiKey(commonOptions.getApiKey());
        request.setId(alertId);
        if (alias != null) request.setAlias(Strings.join(alias, " "));
        if (owner != null) request.setOwner(Strings.join(owner, " "));
        if (note != null) request.setNote(Strings.join(note, " "));
        if (source != null) request.setSource(Strings.join(source, " "));
        if (commonOptions.getUser() != null) request.setUser(commonOptions.getUser());
        ((IOpsGenieClient)client).alert().assign(request);
    }
}