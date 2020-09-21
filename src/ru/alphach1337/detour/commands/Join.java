package ru.alphach1337.detour.commands;

import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.alphach1337.detour.Settings;
import ru.alphach1337.detour.managers.DetourManager;

public class Join extends DetourCommand {
    public Join() {
        super("join", "detour.member");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        DetourManager detourManager = DetourManager.getInstance();

        Player player = (Player) commandSender;

        if (player.getStatistic(Statistic.PLAY_ONE_MINUTE) <= Settings.minutesToAllowDetour) {
            player.sendMessage(Settings.time + Settings.hoursToAllowDetour);
            return false;
        }
        
        if (!detourManager.getIsDetour() && !Settings.allowOffline) {
            player.sendMessage(Settings.notStarted);
            return false;
        }

        if (detourManager.addPlayer(player)) {
            player.sendMessage(Settings.addedToList);
            return true;
        }
        
        player.sendMessage(Settings.alreadyInTheList);
        return false;
    }
}
