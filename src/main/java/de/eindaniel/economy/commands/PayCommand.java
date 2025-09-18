package de.eindaniel.economy.commands;

import de.eindaniel.economy.Economy;
import de.eindaniel.economy.economy.EconomyManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand extends Command {

    public PayCommand() {
        super("pay");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        if (args.length != 2) {
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>Richtige Verwendung <dark_gray>→ <#fbecab>/pay <Spieler> <Betrag>")));
            return true;
        }

        Player from = (Player) sender;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double amount;

        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#ff1717>Ungültiger Betrag!")));
            return true;
        }

        EconomyManager eco = Economy.getInstance().getEconomyManager();

        if (eco.getBalance(from) < amount) {
            from.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<#ff1717>Du hast nicht genügend Geld!")));
            return true;
        }

        eco.removeBalance(from, amount);
        eco.addBalance(target, amount);

        from.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>Du hast <#1fff17>" + amount + "€ <gray>an <#1fff17>" + target.getName() + " <gray>gesendet.")));
        if (target.isOnline()) {
            target.getPlayer().sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>Du hast <#1fff17>" + amount + "€ <gray>von <#1fff17>" + from.getName() + " <gray>erhalten.")));
        }
        return true;
    }
}
