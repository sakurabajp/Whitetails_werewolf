package net.cherryleaves.whitetails_werewolf.item_config;

import net.cherryleaves.whitetails_werewolf.OriginalItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class medium implements Listener {
    @EventHandler
    public void dropMedium(PlayerDropItemEvent e){
        if(e.getItemDrop().getItemStack().equals(new OriginalItem().medium)){
            e.getPlayer().sendMessage(Component.text("[人狼RPG] ").color(NamedTextColor.RED).append(Component.text("死亡者一覧").color(NamedTextColor.AQUA)));
            e.getItemDrop().remove();
            for(Player p : e.getPlayer().getWorld().getPlayers()){
                if(p.getGameMode().equals(GameMode.SPECTATOR) && p.getStatistic(Statistic.DEATHS) == 0 && !p.equals(e.getPlayer())){
                    e.getPlayer().sendMessage(Component.text("[人狼RPG] ").color(NamedTextColor.RED).append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
                }
            }
        }
    }
}
