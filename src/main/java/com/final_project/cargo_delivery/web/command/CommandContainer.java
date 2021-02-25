package com.final_project.cargo_delivery.web.command;

import java.util.HashMap;
import java.util.Map;

/**
 * CommandContainer is holder for all commands.
 *
 * @author Mykhailo Hryb
 */
public class CommandContainer {

    private static final Map<String, Command> commands = new HashMap<String, Command>();

    static {
        commands.put("login", new LoginCommand());
        commands.put("login-page", new LoginPageCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("registration-page", new RegistrationPageCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("deliveries-page", new DeliveriesPageCommand());
        commands.put("calculate-distance", new CalculateCommand());
        commands.put("logout", new LogOutCommand());
        commands.put("localization", new LocalizationCommand());
        commands.put("profile-page", new ProfilePageCommand());
        commands.put("manager-page", new ManagerPageCommand());
        commands.put("make-order", new MakeOrderCommand());
        commands.put("create-receipt-payment", new CreateReceiptPaymentCommand());
        commands.put("change-order-status", new ChangeOrderStatusCommand());
        commands.put("make-report", new MakeReportCommand());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
