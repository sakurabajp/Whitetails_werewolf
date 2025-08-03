package net.cherryleaves.whitetails_werewolf.item_config;

import net.cherryleaves.whitetails_werewolf.OriginalItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class accomplice implements Listener {
    @EventHandler
    public void dropMedium(PlayerDropItemEvent e){
        if(e.getItemDrop().getItemStack().equals(new OriginalItem().accomplice)) {
            e.getItemDrop().remove();
            if (Objects.requireNonNull(e.getPlayer().getScoreboard().getTeam("madman")).hasEntry(e.getPlayer().getName())) {
                Team wolfTeam = e.getPlayer().getScoreboard().getTeam("wolf");
                List<String> wolfPlayers = new ArrayList<>(Objects.requireNonNull(wolfTeam).getEntries());
                if(wolfPlayers.isEmpty()){
                    return;
                }
                String selectedWolf = wolfPlayers.get(new Random().nextInt(wolfPlayers.size()));
                e.getPlayer().sendMessage(Component.text("[人狼RPG] ").color(NamedTextColor.RED).append(Component.text("人狼は" + selectedWolf).color(NamedTextColor.WHITE)));
            }
        }
    }
}
