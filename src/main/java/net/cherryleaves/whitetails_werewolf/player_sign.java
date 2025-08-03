package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class player_sign implements Listener {


    public void getSign(Player p){
        p.getInventory().addItem(makeItemMeta(Material.OAK_SIGN, Component.text("プレイヤー名前登録看板").color(NamedTextColor.GREEN)));
    }

    public ItemStack makeItemMeta(Material m, Component name) {
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.displayName(name);
        item.setItemMeta(meta);
        return item;
    }
    
    @EventHandler
    public void onPlayerClickSign(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Objects.requireNonNull(e.getClickedBlock()).getType().equals(Material.OAK_SIGN)) {
                e.setCancelled(true);
                Sign sign = (Sign) e.getClickedBlock().getState();
                String signText = sign.getSide(Side.FRONT).line(0).toString();
                if (signText.isEmpty()) {
                    sign.getSide(Side.FRONT).line(0, Component.text(e.getPlayer().getName()));
                } 
                else {
                    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                    Player targetPlayer = Bukkit.getPlayer(signText);
                    if (targetPlayer != null) {
                        Team playerTeam = null;
                        for (Team team : scoreboard.getTeams()) {
                            if (team.hasEntry(targetPlayer.getName())) {
                                playerTeam = team;
                                break;
                            }
                        }
                        if (playerTeam != null) {
                            if(playerTeam.getName().equals("wolf")){
                                e.getPlayer().sendMessage(Component.text(targetPlayer.getName() + "は人狼です"));
                            }
                            if(playerTeam.getName().equals("vampire")){
                                e.getPlayer().sendMessage(Component.text(targetPlayer.getName() + "は吸血鬼です"));
                            }
                            if(playerTeam.getName().equals("madman")){
                                e.getPlayer().sendMessage(Component.text(targetPlayer.getName() + "は村人です"));
                            }
                            if(playerTeam.getName().equals("wolf")){
                                e.getPlayer().sendMessage(Component.text(targetPlayer.getName() + "は村人です"));
                            }
                        } else {
                            e.getPlayer().sendMessage(Component.text(targetPlayer.getName() + "はチームに所属していません").color(NamedTextColor.RED));
                        }
                    } else {
                        e.getPlayer().sendMessage(Component.text("プレイヤーが見つかりません").color(NamedTextColor.RED));
                    }
                }
                sign.update();
            }
        }
    }
}
