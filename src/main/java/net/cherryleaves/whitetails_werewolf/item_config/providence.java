package net.cherryleaves.whitetails_werewolf.item_config;

import net.cherryleaves.whitetails_werewolf.OriginalItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class providence implements Listener {

    @EventHandler
    public void useProvidence(PlayerDropItemEvent e){
        if(e.getItemDrop().getItemStack().equals(new OriginalItem().providence)) {
            e.getItemDrop().remove();
            for(Player p : e.getPlayer().getWorld().getPlayers()){
               if(p != e.getPlayer()){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 30 * 20, 0, true, false));
                }
            }
        }
    }
}
