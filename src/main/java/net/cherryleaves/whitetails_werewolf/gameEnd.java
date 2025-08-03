package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class gameEnd {
    public void VampireWin(){
        endGame();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitlePart(TitlePart.TITLE, Component.text("吸血鬼の勝利").color(NamedTextColor.LIGHT_PURPLE).decorate(TextDecoration.BOLD));
            p.sendTitlePart(TitlePart.SUBTITLE, Component.text("GAME END").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD).decorate(TextDecoration.UNDERLINED));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1.0f);
        }
    }

    public void WolfWin(){
        endGame();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitlePart(TitlePart.TITLE, Component.text("人狼の勝利").color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
            p.sendTitlePart(TitlePart.SUBTITLE, Component.text("GAME END").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD).decorate(TextDecoration.UNDERLINED));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1.0f);
        }
    }

    public void VillagerWin(){
        endGame();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitlePart(TitlePart.TITLE, Component.text("村人の勝利").color(NamedTextColor.BLUE).decorate(TextDecoration.BOLD));
            p.sendTitlePart(TitlePart.SUBTITLE, Component.text("GAME END").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD).decorate(TextDecoration.UNDERLINED));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1.0f);
        }
    }

    public void endGame() {
        // シングルトンからタイマーを取得して停止
        systemTimer timer = systemTimer.getInstance();
        if (timer != null) {
            timer.stopTimer();
        }
        // 昼に変更
        Objects.requireNonNull(Bukkit.getWorld("world")).getEntities().stream()
                .filter(entity -> entity instanceof Skeleton)
                .filter(entity -> entity.getScoreboardTags().contains("game_skeleton"))
                .forEach(Entity::remove);
        Objects.requireNonNull(Bukkit.getWorld("world")).setTime(6000); // midnight = 18000 ticks
        System.getInstance().GameNow = false;
        // 全プレイヤーにゲーム終了メッセージを表示
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().clear();

            Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();

            Team teamW = scoreboard.getTeam("wolf");
            Team teamM = scoreboard.getTeam("madman");
            Team teamV = scoreboard.getTeam("villager");
            Team teamVP = scoreboard.getTeam("vampire");

            // Component APIを使用したメッセージ送信
            p.sendMessage(Component.text("---------------------------------------------").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));
            p.sendMessage(Component.text("プレイヤー人数").color(TextColor.color(13, 217, 153)).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(Bukkit.getServer().getOnlinePlayers().size()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text(""));
            p.sendMessage(Component.text("役職一覧").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text("人狼　").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(Objects.requireNonNull(teamW).getEntries().toString()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text("共犯者").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(Objects.requireNonNull(teamM).getEntries().toString()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text("吸血鬼").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(Objects.requireNonNull(teamVP).getEntries().toString()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text("村人　").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(Objects.requireNonNull(teamV).getEntries().toString()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text(""));
            p.sendMessage(Component.text("---------------------------------------------").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));
        }
        Objects.requireNonNull(Bukkit.getWorld("world")).getEntities().stream()
                .filter(entity -> entity instanceof ArmorStand)
                .filter(entity -> entity.getScoreboardTags().contains("skeleton_spawn"))
                .forEach(entity -> ((ArmorStand) entity).setVisible(true));
    }
}
