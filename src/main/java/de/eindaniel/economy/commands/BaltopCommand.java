package de.eindaniel.economy.commands;

import de.eindaniel.economy.Economy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class BaltopCommand extends Command {

    public BaltopCommand() {
        super("baltop");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        List<Map.Entry<java.util.UUID, Double>> top = Economy.getInstance().getEconomyManager().getTopBalances();

        sender.sendMessage(Economy.getPrefix().append(MiniMessage.miniMessage().deserialize("<gray>Top 10 Spieler mit dem meisten Geld:")));
        for (int i = 0; i < Math.min(10, top.size()); i++) {
            Map.Entry<java.util.UUID, Double> entry = top.get(i);
            OfflinePlayer p = Bukkit.getOfflinePlayer(entry.getKey());
            Component component;
            switch (i) {
                case 0:
                    component = MiniMessage.miniMessage().deserialize("<#EFBF04>" + (i + 1) + ". " + p.getName() + " <dark_gray>- <#1fff17>" + entry.getValue() + "€");
                    break;
                case 1:
                    component = MiniMessage.miniMessage().deserialize("<#C4C4C4>" + (i + 1) + ". " + p.getName() + " <dark_gray>- <#1fff17>" + entry.getValue() + "€");
                    break;
                case 2:
                    component = MiniMessage.miniMessage().deserialize("<#CE8946>" + (i + 1) + ". " + p.getName() + " <dark_gray>- <#1fff17>" + entry.getValue() + "€");
                    break;
                default:
                    component = MiniMessage.miniMessage().deserialize("<#fbecab>" + (i + 1) + ". <gray>" + p.getName() + " <dark_gray>- <#1fff17>" + entry.getValue() + "€");
                    break;
            }
            sender.sendMessage(Economy.getPrefix().append(component));
        }
        return true;
    }
}
