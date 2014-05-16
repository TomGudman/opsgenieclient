package com.ifountain.opsgenie.client.cli.commands.opsgenie;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.ifountain.client.ClientConstants;
import com.ifountain.client.model.IClient;
import com.ifountain.client.opsgenie.IOpsGenieClient;
import com.ifountain.client.opsgenie.model.alert.DeleteAlertRequest;
import com.ifountain.client.util.Strings;
import com.ifountain.opsgenie.client.cli.commands.BaseCommand;
import com.ifountain.opsgenie.client.cli.commands.CommonCommandOptions;
import com.ifountain.opsgenie.client.cli.commands.NullSplitter;

import java.util.List;

/**
 * @author Sezgin Kucukkaraaslan
 * @version 12/4/12 3:57 PM
 */
@Parameters(commandDescription = "Deletes an alert at OpsGenie.")
public class DeleteAlertCommand extends BaseCommand {
    @ParametersDelegate
    private CommonCommandOptions commonOptions = new CommonCommandOptions();

    @Parameter(names = "--" + ClientConstants.API.ALERT_ID, description = "Id of the alert that will be deleted.", splitter = NullSplitter.class)
    private String alertId;

    @Parameter(names = "--" + ClientConstants.API.ALIAS, description = "Alias of the alert that will be deleted. Either this or alertId should be given.", variableArity = true, splitter = NullSplitter.class)
    private List<String> alias;

    @Parameter(names = "--" + ClientConstants.API.SOURCE, description = "Source of action.", variableArity = true, splitter = NullSplitter.class)
    private List<String> source;

    public DeleteAlertCommand(JCommander commander) {
        super(commander);
    }

    @Override
    public String getName() {
        return "deleteAlert";
    }

    @Override
    protected CommonCommandOptions getCommonCommandOptions() {
        return commonOptions;
    }

    @Override
    public void doExecute(IClient client) throws Exception {
        DeleteAlertRequest request = new DeleteAlertRequest();
        request.setApiKey(commonOptions.getApiKey());
        request.setId(alertId);
        if (alias != null) request.setAlias(Strings.join(alias, " "));
        if (source != null) request.setSource(Strings.join(source, " "));
        if (commonOptions.getUser() != null) request.setUser(commonOptions.getUser());
        ((IOpsGenieClient)client).alert().deleteAlert(request);
    }
}