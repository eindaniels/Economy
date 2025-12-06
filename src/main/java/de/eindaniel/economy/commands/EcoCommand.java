package de.eindaniel.economy.commands;

import de.eindaniel.economy.Economy;
import de.eindaniel.economy.economy.EconomyManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.eclipse.sisu.launch.Main;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EcoCommand extends Command {

    public EcoCommand() {
        super("eco");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("economy.eco")) {
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#ff1717>You don't have Permission to that.")));
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>Usage <dark_gray>→ <#fbecab>/eco <give|take|set> <Player> <Amount>")));
            return true;
        }

        String action = args[0].toLowerCase();
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        double amount;

        try {
            if (!action.equalsIgnoreCase("reset")) {
                amount = Double.parseDouble(args[2]);
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#ff1717>Wrong Amount!")));
            return true;
        }

        EconomyManager eco = Economy.getInstance().getEconomyManager();

        switch (action) {
            case "give":
                eco.addBalance(target, amount);
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#1fff17>" + amount + " has been sent to " + target.getName() + " successfully.")));
                break;
            case "take":
                eco.removeBalance(target, amount);
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#1fff17>" + amount + " has been taken from " + target.getName() + " successfully.")));
                break;
            case "set":
                eco.setBalance(target, amount);
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#1fff17>" + target.getName() + "'s Balance has been set to " + amount + "€ successfully.")));
                break;
            case "reset":
                eco.setBalance(target, 0);
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#1fff17>You have reseted " + target.getName() + "'s Balance.")));
                break;
            default:
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#ff1717>Wrong Action. /eco <give|take|set> <Player> <Amount>")));
                break;
        }
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return List.of("give", "take", "set", "reset");
        }
        if (args.length == 2) {
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
        return Collections.emptyList();
    }
}
