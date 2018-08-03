package ru.alphach1337.detour.commands;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.alphach1337.detour.Settings;
import ru.alphach1337.detour.managers.DetourManager;
import ru.alphach1337.detour.sqlite.DataBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Next implements Command {

    @Override
    public String getPermission() {
        return "detour.manage";
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public void execute(CommandSender commandSender, org.bukkit.command.Command command, String[] args) {
        try {
            if (!DetourManager.getInstance().getIsDetour()) {
                commandSender.sendMessage(Settings.notStarted);
                return;
            }

            boolean isOffline = false;
            ArrayList<String> players = DataBase.selectAllUuids("players");
            ArrayList<String> party = DataBase.selectAllUuids("party");
            HashMap<String, String> locations = DataBase.selectAllLocations("locations");


            Player p = (Player) commandSender;

            if (players.size() <= 0) {
                DetourManager.getInstance().stop();
                Bukkit.broadcastMessage(Settings.stopDetour);

                return;
            }

            if (!DetourManager.getInstance().getIsDetour()) {
                p.sendMessage(Settings.notStarted);
                return;
            }

            if (players.isEmpty()) {
                p.sendMessage(Settings.notJoined);
                return;
            }

            try {
                for (String username : party) {
                    Bukkit.getPlayer(UUID.fromString(username)).teleport(Bukkit.getPlayer(UUID.fromString(players.get(0))));
                }

            } catch (Exception e) {
                for (String username : party) {
                    Bukkit.getPlayer(UUID.fromString(username)).teleport(DetourManager.getLocationFromString(locations.get(players.get(0))));

                }
                isOffline = true;
            }

            for (int i = 0; i < players.size(); i++) {
                Player player = Bukkit.getPlayer(UUID.fromString(players.get(i)));

                if (DetourManager.getInstance().getIsDetour() && player != null && player.isOnline()) {
                    if (i > 0) {
                        try {
                            ActionBarAPI.sendActionBar(player, ChatColor.GREEN + "Твое место в очереди " + i);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            ActionBarAPI.sendActionBar(player, ChatColor.YELLOW + "За вами наблюдают!");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            for (String s : locations.keySet()) {
                if (players.get(0).equals(s)) {
                    int count = 0;
                    try {
                        count = Integer.parseInt(DataBase.selectById(s, "counts", "count"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    ++count;
                    DataBase.delete(s, "counts");
                    DataBase.insert("" + count, s, "counts", "count");

                    String str = DataBase.selectById(s, "idsAndNames", "name");
                    if (!isOffline) {
                        p.sendMessage(ChatColor.GREEN + "Добро пожаловать к игроку " + ChatColor.BLUE + str + ChatColor.GREEN + ". Это его " + count + " обход."
                                         + "\n " + ChatColor.YELLOW + "Осталось: " + ChatColor.DARK_PURPLE + (players.size() - 1));
                    } else {

                        p.sendMessage(ChatColor.GREEN + "Добро пожаловать к игроку " + ChatColor.BLUE + str + ChatColor.RED + " (оффлайн)"
                                        + ChatColor.GREEN + ". Это его " + count + " обход." +
                                "\n" + ChatColor.YELLOW + "Осталось: " + ChatColor.DARK_PURPLE + (players.size() - 1));
                    }
                    DataBase.delete(players.get(0), "players");
                    return;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
