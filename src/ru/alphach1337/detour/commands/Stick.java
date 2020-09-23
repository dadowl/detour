package ru.alphach1337.detour.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.alphach1337.detour.Settings;

public class Stick extends DetourCommand {
    public Stick() {
        super("stick", "detour.manage");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        give((Player)commandSender);
        
        return true;
    }
    
    public static void give(Player target) {
        ItemStack item = new ItemStack(Material.STICK, 1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Settings.stick);

        item.setItemMeta(meta);

        target.getInventory().addItem(item);
    }
}
