package ru.alphach1337.detour;

import org.bukkit.ChatColor;

import ru.alphach1337.detour.managers.DetourManager;

public class Settings {
    public static final String hasNoPermission = ChatColor.RED + "У вас нет прав для использования данной команды";
    public static final String notStarted = ChatColor.RED + "Обход еще не начался!";
    public static final String stopDetour = ChatColor.RED + "Обход закончен";
    public static final String Started1 = ChatColor.GREEN + "Обход начался";
    public static final String Started2 = ChatColor.YELLOW + "Пиши команду " + ChatColor.LIGHT_PURPLE + "/detour join" + ChatColor.YELLOW
            + ", чтобы присоединиться";
    public static final String alreadyInTheList = ChatColor.RED + "Ты уже участвуешь в обходе!";
    public static final String addedToList = ChatColor.GREEN + "Теперь ты участвуешь в обходе!";
    public static final String deletedFromList = ChatColor.GREEN + "Теперь ты не участвуешь в обходе!";
    public static final String notAdded = ChatColor.RED + "Ты и так не участвовал в обходе!";
    public static final String notJoined = ChatColor.RED + "В очереди нет игроков!";
    public static final String alreadyStarted = ChatColor.RED + "Обход уже начался!";
    public static final String onStartSubtitle = ChatColor.YELLOW + "Чтобы присоединиться, пиши /detour join";
    public static final String stick = "Посох Мегумин";
    public static final String time = ChatColor.RED + "Вы отыграли недостаточно часов, чтобы зайти в обход. Необходимо: " + ChatColor.YELLOW;
    public static final String playerAmount = ChatColor.GREEN + "На данный момент в обходе участвует " + ChatColor.YELLOW + "%s"
            + ChatColor.GREEN + " игроков: " + ChatColor.BLUE + "%s";
    public static final String joinAlreadyStarted = ChatColor.YELLOW + "Обход уже начался, Вы можете продолжить его с помощью команды\n"
            + ChatColor.DARK_AQUA + "/detour " + ChatColor.BLUE + "next";
    public static final String joinReady = ChatColor.GREEN + "Привет, к обходу готово человек: " + ChatColor.DARK_PURPLE;
    public static final String welcomeSuffix = ChatColor.GREEN + "Добро пожаловать к игроку " + ChatColor.BLUE;
    public static final String welcome = ChatColor.GREEN + ". Это его 0 обход.\n" + ChatColor.YELLOW + "Осталось: " + ChatColor.DARK_PURPLE;
    public static final String welcomeOffline = ChatColor.RED + " (оффлайн)"
            + ChatColor.GREEN + ". Это его 0 обход.\n" + ChatColor.YELLOW + "Осталось: " + ChatColor.DARK_PURPLE;
    public static final String playersPassed = ChatColor.YELLOW + "Было пройдено игроков: " + ChatColor.DARK_PURPLE;
    public static final String queuePlace = ChatColor.GREEN + "Твое место в очереди ";
    public static final String beingSpectated = ChatColor.YELLOW + "За вами наблюдают!";
    public static final String partyMessage = ChatColor.YELLOW + "Вы можете проводить совместные обходы с помощью команды\n" +
            ChatColor.DARK_AQUA + "/detour " + ChatColor.BLUE + "party add " + ChatColor.WHITE + "<username>";
    
    // Database
    public static final String eventsTable = "events";
    public static final String joinsTable = "joins";
    
    public static final int hoursToAllowDetour = DetourManager.getInstance().getConfig().getInt("hoursToAllowDetour");
    public static final int minutesToAllowDetour = hoursToAllowDetour * 60;
    
    public static final boolean allowOffline =  DetourManager.getInstance().getConfig().getBoolean("allowOffline");
    
    
}
