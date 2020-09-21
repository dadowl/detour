package ru.alphach1337.detour.helpers;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.ChatMessageType;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import net.minecraft.server.v1_15_R1.PacketPlayOutTitle;

public class PacketUtil {
    public static void sendActionBar(Player player, String message) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((new PacketPlayOutChat(new ChatComponentText(message), ChatMessageType.GAME_INFO)));
    }
    
    public static void broadcastTitle(String t, String st) {
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(st));
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(t));
        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(subtitle);
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(title);
        }
    }
}
