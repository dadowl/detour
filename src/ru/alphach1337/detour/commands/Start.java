package ru.alphach1337.detour.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.alphach1337.detour.Settings;
import ru.alphach1337.detour.helpers.PacketUtil;
import ru.alphach1337.detour.managers.DetourManager;
import ru.alphach1337.detour.models.EventParticipant;
import ru.alphach1337.detour.sqlite.Database;

public class Start extends DetourCommand {
    public Start() {
        super("start", "detour.manage");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        DetourManager detourManager = DetourManager.getInstance();

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Только игроки могут использовать эту команду!");
            return false;
        }

        if (!detourManager.getIsDetour()) {
            final Player p = ((Player) commandSender);
            
            detourManager.start();

            EventParticipant eventCreator = new EventParticipant(
                    p.getUniqueId(),
                    detourManager.getEventId(),
                    p.getLocation(),
                    true,
                    false
            );

            Database.getInstance().addPlayerInEvent(detourManager.getEventId(), eventCreator);
            
            Stick.give(p);
            
            PacketUtil.broadcastTitle(Settings.Started1, Settings.onStartSubtitle);
            
            Bukkit.broadcastMessage(Settings.Started1);
            Bukkit.broadcastMessage(Settings.Started2);

            commandSender.sendMessage(Settings.partyMessage);

            return true;
        }
        commandSender.sendMessage(Settings.alreadyStarted);
        return false;
    }
}
