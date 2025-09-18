package de.eindaniel.economy.economy;

import de.eindaniel.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class EconomyManager {

    private final Economy plugin;
    private final Map<UUID, Double> balances = new HashMap<>();

    public EconomyManager(Economy plugin) {
        this.plugin = plugin;
        loadBalances();
    }

    private void loadBalances() {
        FileConfiguration cfg = plugin.getConfig();
        if (cfg.getConfigurationSection("balances") == null) {
            return;
        }
        for (String key : cfg.getConfigurationSection("balances").getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            balances.put(uuid, cfg.getDouble("balances." + key));
        }
    }

    public void saveBalances() {
        FileConfiguration cfg = plugin.getConfig();
        for (Map.Entry<UUID, Double> entry : balances.entrySet()) {
            cfg.set("balances." + entry.getKey().toString(), entry.getValue());
        }
        plugin.saveConfig();
    }

    public double getBalance(OfflinePlayer player) {
        return balances.getOrDefault(player.getUniqueId(), 0.0);
    }

    public void setBalance(OfflinePlayer player, double amount) {
        balances.put(player.getUniqueId(), amount);
        saveBalances();
    }

    public void addBalance(OfflinePlayer player, double amount) {
        setBalance(player, getBalance(player) + amount);
    }

    public void removeBalance(OfflinePlayer player, double amount) {
        setBalance(player, getBalance(player) - amount);
    }

    public List<Map.Entry<UUID, Double>> getTopBalances() {
        List<Map.Entry<UUID, Double>> list = new ArrayList<>(balances.entrySet());
        list.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        return list;
    }
}
