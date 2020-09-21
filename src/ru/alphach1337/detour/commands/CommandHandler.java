package ru.alphach1337.detour.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandHandler implements TabCompleter, CommandExecutor {
    public ArrayList<DetourCommand> commands = new ArrayList<>();
    private String commandName;

    public CommandHandler(String command) {
        commandName = command;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {
        if (args.length <= 0) {
            return false;
        }

        if (cmd.getName().equals(commandName)) {
            for (DetourCommand command : commands) {
                if (command.getName().equalsIgnoreCase(args[0])) {
                    if (commandSender.hasPermission(command.getPermission())) {
                        command.execute(commandSender, cmd.toString(), args);
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command cmd, String s, String[] args) {
        if (args.length <= 1) {
            ArrayList<String> strings = new ArrayList<>();

            for (DetourCommand command : commands) {
                if (
                        command.getName().toLowerCase().startsWith(args[0]) &&
                        commandSender.hasPermission(command.getPermission())
                ) {
                    strings.add(command.getName().toLowerCase());
                }
            }

            return strings;
        }

        if (cmd.getName().equals(commandName)) {
            if (args[0].equalsIgnoreCase("party")) {
                if (args.length <= 2) {
                    ArrayList<String> strings = new ArrayList<>();
                    strings.add("add");
                    return strings;
                }
            }
        }

        return null;
    }
}
