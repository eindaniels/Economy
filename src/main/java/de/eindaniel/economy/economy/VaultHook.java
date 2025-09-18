package de.eindaniel.economy.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.eclipse.sisu.launch.Main;

public class VaultHook {

    public static void setup(de.eindaniel.economy.Economy plugin, EconomyManager manager) {
        Economy provider = new VaultEconomy(manager);
        Bukkit.getServicesManager().register(Economy.class, provider, plugin, ServicePriority.Highest);
    }
}
