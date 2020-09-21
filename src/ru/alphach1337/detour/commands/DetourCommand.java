package ru.alphach1337.detour.commands;

import org.bukkit.command.CommandSender;

public abstract class DetourCommand  {
    private final String name;
    private final String permission;
    
    protected DetourCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }
    
    public final String getName() {
        return name;
    }

    public final String getPermission() {
        return permission;
    }
    
    public abstract boolean execute(CommandSender commandSender, String s, String[] args);
}