package ru.alphach1337.detour.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import ru.alphach1337.detour.Settings;
import ru.alphach1337.detour.managers.DetourManager;

public class Stop extends DetourCommand {
    public Stop() {
        super("stop", "detour.manage");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (DetourManager.getInstance().getIsDetour()) {

            DetourManager.getInstance().stop();
            Bukkit.getServer().broadcastMessage(Settings.stopDetour);

            return true;
        }
        commandSender.sendMessage(Settings.notStarted);
        return false;
    }
}
