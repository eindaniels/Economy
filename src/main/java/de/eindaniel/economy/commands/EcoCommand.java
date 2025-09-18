package de.eindaniel.economy.commands;

import de.eindaniel.economy.Economy;
import de.eindaniel.economy.economy.EconomyManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.eclipse.sisu.launch.Main;

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
            default:
                sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#ff1717>Ungültige Aktion. /eco <give|take|set> <Spieler> <Betrag>")));
                break;
        }
        return true;
    }
}
