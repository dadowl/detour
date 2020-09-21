package ru.alphach1337.detour.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.alphach1337.detour.managers.DetourManager;

public class Next extends DetourCommand {
    public Next() {
        super("next", "detour.manage");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        DetourManager.getInstance().next((Player) commandSender);

        return true;
    }
}
