package ru.alphach1337.detour.managers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jline.utils.Log;

import ru.alphach1337.detour.Settings;
import ru.alphach1337.detour.helpers.PacketUtil;
import ru.alphach1337.detour.models.EventParticipant;
import ru.alphach1337.detour.sqlite.Database;

public class DetourManager {
    private static final DetourManager INSTANCE = new DetourManager();
    private FileConfiguration config = Bukkit.getPluginManager().getPlugin("Detour").getConfig();
    private int     eventId  = -1;

    public static DetourManager getInstance() {
        return INSTANCE;
    }

    public boolean getIsDetour() {
        return eventId != -1;
    }

    public void setEvent(int event) {
        this.eventId = event;
    }

    public int getEventId() {
        return eventId;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public boolean addPlayer(Player player) {
        EventParticipant participant = new EventParticipant(eventId, player);

        return Database.getInstance().addPlayerInEvent(eventId, participant);
    }

    public void start() {
        eventId = Database.getInstance().startEvent();
        Bukkit.getLogger().info(new StringBuilder().append("Обход #").append(eventId).append(" создан").toString());
    }

    public void stop() {
        ArrayList<EventParticipant> participants = Database.getInstance().getPlayers(eventId, true, false);

        Database.getInstance().closeAllEvents();
        Bukkit.broadcastMessage(Settings.stopDetour);
        Bukkit.broadcastMessage(Settings.playersPassed + participants.size());

        eventId = -1;
    }

    public void next(Player p) {
        Database database = Database.getInstance();

        if (!DetourManager.getInstance().getIsDetour()) {
            p.sendMessage(Settings.notStarted);
            return;
        }

        ArrayList<EventParticipant> participants = database.getPlayers(eventId, false, false);
        ArrayList<EventParticipant> party = database.getPlayers(eventId, true, true);//false true

        Log.info(participants);

        if (participants.isEmpty()) {
            DetourManager.getInstance().stop();
            return;
        }

        EventParticipant participant = participants.get(0);
        Player player = Bukkit.getPlayer(participant.getUUID());

        if (player != null && player.isOnline()) {
            for (EventParticipant member : party) {
                Bukkit.getPlayer(member.getUUID()).teleport(player.getLocation());
            }
            PacketUtil.sendActionBar(player, Settings.beingSpectated);
            p.sendMessage(Settings.welcomeSuffix + player.getName() + Settings.welcome + (participants.size() - 1));
        } else {
            p.sendMessage(Settings.welcomeSuffix + player.getName() + Settings.welcomeOffline + (participants.size() - 1));
        }

        participant.setIgnore(true);
        Database.getInstance().setPlayer(participant);
    }
}
