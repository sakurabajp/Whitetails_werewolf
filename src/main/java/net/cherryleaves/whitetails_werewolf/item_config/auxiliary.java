package net.cherryleaves.whitetails_werewolf.item_config;

import net.cherryleaves.whitetails_werewolf.OriginalItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class auxiliary implements Listener {

    @EventHandler
    public void dropAccomplice(PlayerDropItemEvent e){
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

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player p1){
            if(e.getEntity() instanceof Player p2){
                if(p1.getInventory().getItemInMainHand().equals(new OriginalItem().cross)) {
                    if (Objects.requireNonNull(p2.getScoreboard().getTeam("vampire")).hasEntry(p2.getName())) {
                        e.setDamage(40);
                    }
                }
            }
        }
    }

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

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player player && e.getItem().getItemStack().equals(new OriginalItem().fortune)) {
            // fortuneアイテムを拾ったプレイヤーのスコアのみを更新
            Objects.requireNonNull(player.getScoreboard().getObjective("fortune")).getScore(player.getName()).setScore(
                    Objects.requireNonNull(player.getScoreboard().getObjective("fortune")).getScore(player.getName()).getScore() + 1
            );
            player.getInventory().removeItem(new OriginalItem().fortune);
        }
    }

}
