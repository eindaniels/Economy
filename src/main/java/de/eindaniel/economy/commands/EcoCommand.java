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
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#ff1717>Du hast keine Berechtigung dazu.")));
            return true;
        }
        if (args.length != 3) {
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>Richtige Verwendung <dark_gray>→ <#fbecab>/eco <give|take|set> <Spieler> <Betrag>")));
            return true;
        }

        String action = args[0].toLowerCase();
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        double amount;

        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#ff1717>Ungültiger Betrag!")));
            return true;
        }

        EconomyManager eco = Economy.getInstance().getEconomyManager();

        switch (action) {
            case "give":
                eco.addBalance(target, amount);
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#1fff17>" + amount + "€ wurden zu " + target.getName() + " erfolgreich hinzugefügt.")));
                break;
            case "take":
                eco.removeBalance(target, amount);
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#1fff17>" + amount + "€ wurden von " + target.getName() + " erfolgreich weggenommen.")));
                break;
            case "set":
                eco.setBalance(target, amount);
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#1fff17>" + target.getName() + "'s Geld wurde auf " + amount + "€ gesetzt.")));
                break;
            case "reset":
                eco.setBalance(target, 0);
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#1fff17>Du hast erfolgreich " + target.getName() + "'s Geld zurückgesetzt.")));
            default:
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#ff1717>Ungültige Aktion. /eco <give|take|set> <Spieler> <Betrag>")));
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
