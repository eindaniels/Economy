package de.eindaniel.economy.commands;

import de.eindaniel.economy.Economy;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BalanceCommand extends Command {

    public BalanceCommand() {
        super("balance");
        setAliases(Arrays.asList("bal"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (args.length > 0) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            double bal = Economy.getInstance().getEconomyManager().getBalance(player);
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>" + player.getName() +  "'s Kontostand <dark_gray>→ <#1fff17>" + bal + "€")));
            return true;
        }
        Player player = (Player) sender;
        double bal = Economy.getInstance().getEconomyManager().getBalance(player);
        player.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>Dein Kontostand <dark_gray>→ <#1fff17>" + bal + "€")));
        return true;
    }
}
