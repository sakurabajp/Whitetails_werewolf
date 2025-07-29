package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.cherryleaves.whitetails_werewolf.start.message;

public final class Whitetails_werewolf extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new SetArmorStand(), this);
        getServer().getPluginManager().registerEvents(new setVillager(), this);
        getServer().getPluginManager().registerEvents(new System(), this);
        getServer().getPluginManager().registerEvents(new GUI(), this);
        new message().println();
        new Command().AlliRegister();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.joinMessage(Component.text("[人狼RPG] ").color(NamedTextColor.RED).append(Component.text(e.getPlayer().getName() + "が参加しました").color(NamedTextColor.AQUA)));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        e.quitMessage(Component.text("[人狼RPG] ").color(NamedTextColor.RED).append(Component.text(e.getPlayer().getName() + "が退出しました").color(NamedTextColor.AQUA)));
    }
}