package de.eindaniel.economy;

import de.eindaniel.economy.commands.BalanceCommand;
import de.eindaniel.economy.commands.BaltopCommand;
import de.eindaniel.economy.commands.EcoCommand;
import de.eindaniel.economy.commands.PayCommand;
import de.eindaniel.economy.economy.EconomyManager;
import de.eindaniel.economy.economy.VaultHook;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Economy extends JavaPlugin {

    private static Economy instance;
    private EconomyManager economyManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        economyManager = new EconomyManager(this);
        VaultHook.setup(this, economyManager);

        registerCommands();
        getLogger().info("Economy aktiviert!");
    }

    private void registerCommands() {
        try {
            CommandMap commandMap = Bukkit.getCommandMap();
            commandMap.register("economy", new BalanceCommand());
            commandMap.register("economy", new PayCommand());
            commandMap.register("economy", new BaltopCommand());
            commandMap.register("economy", new EcoCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public static Economy getInstance() {
        return instance;
    }

    public static Component getPrefix() {
        Component prefix = MiniMessage.miniMessage().deserialize("<dark_gray>[<#ffdd00>Economy<dark_gray>] ");
        return prefix;
    }
}
