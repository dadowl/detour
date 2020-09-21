package ru.alphach1337.detour.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.alphach1337.detour.Settings;
import ru.alphach1337.detour.managers.DetourManager;
import ru.alphach1337.detour.models.EventParticipant;
import ru.alphach1337.detour.sqlite.Database;

public class Quit extends DetourCommand {
    public Quit() {
        super("quit", "detour.member");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        Player player = (Player) commandSender;
        Database database = Database.getInstance();
        DetourManager detourManager = DetourManager.getInstance();

        EventParticipant participant = database.getPlayerInEvent(detourManager.getEventId(), player.getUniqueId());

        if (participant != null) {
            database.removePlayerFromEvent(detourManager.getEventId(), participant);
            player.sendMessage(Settings.deletedFromList);

            return true;
        }
        player.sendMessage(Settings.notAdded);
        return false;
    }
}
