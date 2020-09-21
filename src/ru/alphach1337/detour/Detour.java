package ru.alphach1337.detour;

import org.bukkit.GameMode;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ru.alphach1337.detour.commands.CommandHandler;
import ru.alphach1337.detour.commands.Join;
import ru.alphach1337.detour.commands.List;
import ru.alphach1337.detour.commands.Next;
import ru.alphach1337.detour.commands.Party;
import ru.alphach1337.detour.commands.Quit;
import ru.alphach1337.detour.commands.Start;
import ru.alphach1337.detour.commands.Stick;
import ru.alphach1337.detour.commands.Stop;
import ru.alphach1337.detour.events.EventListener;
import ru.alphach1337.detour.managers.DetourManager;
import ru.alphach1337.detour.sqlite.Database;

public class Detour extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        this.getConfig().addDefault("allowOffline", true);
        this.getConfig().addDefault("hoursToAllowDetour", 12);
        this.getConfig().addDefault("partyGameMode", String.valueOf(GameMode.SPECTATOR));
        this.getConfig().options().copyDefaults(true);
        saveConfig();

        CommandHandler cw = new CommandHandler("detour");

        cw.commands.add(new Join());
        cw.commands.add(new Quit());
        cw.commands.add(new List());
        cw.commands.add(new Start());
        cw.commands.add(new Stop());
        cw.commands.add(new Next());
        cw.commands.add(new Party());
        cw.commands.add(new Stick());

        this.getCommand("detour").setExecutor(cw);

        DetourManager.getInstance().setEvent(Database.getInstance().init());
    }

    @Override
    public void onDisable() {
        Database.getInstance().close();
    }
}