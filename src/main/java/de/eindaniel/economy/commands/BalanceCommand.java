package de.eindaniel.economy.commands;

import de.eindaniel.economy.Economy;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>" + player.getName() +  "'s Balance <dark_gray>→ <#1fff17>" + bal + "€")));
            return true;
        }
        Player player = (Player) sender;
        double bal = Economy.getInstance().getEconomyManager().getBalance(player);
        player.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>Your Balance <dark_gray>→ <#1fff17>" + bal + "€")));
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        String lastWord = args[args.length - 1];
        Player senderPlayer = sender instanceof Player ? (Player) sender : null;

        ArrayList<String> matchedPlayers = new ArrayList<String>();
        for (Player player : sender.getServer().getOnlinePlayers()) {
            String name = player.getName();
            if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord)) {
                matchedPlayers.add(name);
            }
        }

        Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }
}
