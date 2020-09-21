package ru.alphach1337.detour.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.alphach1337.detour.Settings;
import ru.alphach1337.detour.managers.DetourManager;
import ru.alphach1337.detour.models.EventParticipant;
import ru.alphach1337.detour.sqlite.Database;

public class List extends DetourCommand {
    public List() {
        super("list", "detour.member");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        ArrayList<EventParticipant> players = Database.getInstance().getPlayers(
                DetourManager.getInstance().getEventId(),
                false,
                false
        );

        ArrayList<EventParticipant> reviewers = Database.getInstance().getPlayers(
                DetourManager.getInstance().getEventId(),
                false,
                true
        );

        ArrayList<String> names = new ArrayList<>();

        for (EventParticipant reviewer : reviewers) {
            String name = Bukkit.getPlayer(reviewer.getUUID()).getDisplayName();
            names.add(name + " [организатор]");
        }

        for (EventParticipant player : players) {
            String name = Bukkit.getPlayer(player.getUUID()).getDisplayName();
            names.add(name);
        }

        int size = players.size() + reviewers.size();

        String playersString = String.join(", ", names);
        
        Player p = (Player) commandSender;
        p.sendMessage(String.format(Settings.playerAmount, size, playersString));

        return true;
    }
}
