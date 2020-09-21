package ru.alphach1337.detour.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;

import ru.alphach1337.detour.Settings;
import ru.alphach1337.detour.managers.DetourManager;
import ru.alphach1337.detour.models.EventParticipant;
import ru.alphach1337.detour.sqlite.Database;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (DetourManager.getInstance().getIsDetour()) {
            if (player.isOp()) {
                player.sendMessage(Settings.joinAlreadyStarted);
            } else {
                player.sendMessage(Settings.Started1);
                player.sendMessage(Settings.Started2);
            }
        } else {
            if (player.isOp()) {
                ArrayList<EventParticipant> players =
                        Database.getInstance().getPlayers(-1, false, false);
                player.sendMessage(Settings.joinReady + players.size());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DetourManager detourManager = DetourManager.getInstance();
        Database database = Database.getInstance();

        if (!Settings.allowOffline) {
            database.removePlayerFromEvent(detourManager.getEventId(), new EventParticipant(
                    detourManager.getEventId(),
                    event.getPlayer()
            ));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        DetourManager detourManager = DetourManager.getInstance();
        Database database = Database.getInstance();
        
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        
        Player player = event.getPlayer();
        if (
                player.isOp() &&
                        player.getInventory().getItemInMainHand().getType() == Material.STICK &&
                        player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(Settings.stick)
        ) {
            if (detourManager.getIsDetour()) {
                ArrayList<EventParticipant> players =
                        database.getPlayers(detourManager.getEventId(), false, false);
                ArrayList<EventParticipant> reviewers =
                        database.getPlayers(detourManager.getEventId(), false, true);

                if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    for (EventParticipant reviewer : reviewers) {
                        Bukkit.getPlayer(reviewer.getUUID()).teleport(players.get(0).getLocation());
                    }

                } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    DetourManager.getInstance().next(player);
                }
            } else {
                player.sendMessage(Settings.notStarted);
            }
        }
    }
}
