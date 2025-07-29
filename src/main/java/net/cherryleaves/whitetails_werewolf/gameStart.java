package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class gameStart {
    int BeforeWolfPlayerCount = 1;
    int BeforeMadmanPlayerCount = 0;
    int VillagerCount;
    int MadmanCount;
    int ALLPlayerCount;

    private void showPlayerRoleTitle(Player player, Team teamV, Team teamW, Team teamM) {
        String roleText;
        if (teamW.hasEntry(player.getName())) {
            roleText = "人狼";
        } else if (teamM.hasEntry(player.getName())) {
            roleText = "狂人";
        } else if (teamV.hasEntry(player.getName())) {
            roleText = "村人";
        } else {
            roleText = "不明";
        }

        // 新しい Bukkit API の sendTitle メソッドを使用
        player.sendTitle(
                "貴方は " + roleText + " です",
                "ゲームスタート！",
                10, 40, 10
        );
    }


    public void GameStart() {
        final ScoreboardManager managerW = Bukkit.getScoreboardManager();
        final ScoreboardManager managerV = Bukkit.getScoreboardManager();
        final ScoreboardManager managerM = Bukkit.getScoreboardManager();

        final Scoreboard scoreboardW = Objects.requireNonNull(managerW).getMainScoreboard();
        final Scoreboard scoreboardV = Objects.requireNonNull(managerV).getMainScoreboard();
        final Scoreboard scoreboardM = Objects.requireNonNull(managerM).getMainScoreboard();

        List<Player> Players = new ArrayList<>(Bukkit.getOnlinePlayers());
        if (scoreboardW.getTeam("wolf") != null) {
            Objects.requireNonNull(scoreboardW.getTeam("wolf")).unregister();
        }
        if (scoreboardV.getTeam("villager") != null) {
            Objects.requireNonNull(scoreboardV.getTeam("villager")).unregister();
        }
        if (scoreboardM.getTeam("madman") != null) {
            Objects.requireNonNull(scoreboardM.getTeam("madman")).unregister();
        }
        Team teamW = scoreboardW.registerNewTeam("wolf");
        Team teamM = scoreboardM.registerNewTeam("madman");
        Team teamV = scoreboardV.registerNewTeam("villager");
        for (Player playerACC : Bukkit.getOnlinePlayers()) {
            // playerACC.sendMessage("貴方を村人チームに追加しました");
            teamV.addPlayer(playerACC);
        }
        for (int i = BeforeWolfPlayerCount; i > 0; i -= 1) {
            Random random = new Random();
            Player WolfTeamPlayers = Players.get(random.nextInt(Players.size()));
            if (teamW.hasEntry(WolfTeamPlayers.getName())) {
                // WolfTeamPlayers.sendMessage("貴方はすでに人狼チームに所属しているため再抽選が行われます");
                return;
            }
            teamW.addPlayer(WolfTeamPlayers);
            // WolfTeamPlayers.sendMessage("貴方は人狼に選ばれました");
        }
        for (int i = BeforeMadmanPlayerCount; i > 0; i -= 1) {
            Random random = new Random();
            Player MadmanTeamPlayers = Players.get(random.nextInt(Players.size()));
            if (teamM.hasEntry(MadmanTeamPlayers.getName())) {
                // MadmanTeamPlayers.sendMessage("貴方はすでに狂人チームに所属しているため再抽選が行われます");
                return;
            } else if (teamW.hasEntry(MadmanTeamPlayers.getName())) {
                // MadmanTeamPlayers.sendMessage("貴方はすでに狂人チームに所属しているため再抽選が行われます");
                return;
            }
            teamM.addPlayer(MadmanTeamPlayers);
            // MadmanTeamPlayers.sendMessage("貴方は狂人に選ばれました");
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            ALLPlayerCount++;
            p.setGameMode(GameMode.SURVIVAL);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 10, 80, true, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 80, true, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 5, true, false));
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 0.5f, 1.0f);
            p.getInventory().clear();
            p.getActivePotionEffects().clear();

            // 改良されたタイトル表示を呼び出す
            showPlayerRoleTitle(p, teamV, teamW, teamM);

            // Component APIを使用したメッセージ送信
            p.sendMessage(Component.text("-----------------------------------------------------").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));
            p.sendMessage(Component.text("ゲームスタート！").color(NamedTextColor.AQUA).decorate(TextDecoration.BOLD));
            p.sendMessage(Component.text(""));
            p.sendMessage(Component.text("制限時間は").color(NamedTextColor.AQUA)
                    .append(Component.text("3時間").color(NamedTextColor.GOLD))
                    .append(Component.text("です").color(NamedTextColor.AQUA)));
            p.sendMessage(Component.text(""));

            if (teamV.hasEntry(p.getName())) {
                p.sendMessage(Component.text("あなたは").color(NamedTextColor.DARK_AQUA)
                        .append(Component.text("村人陣営").color(NamedTextColor.GREEN))
                        .append(Component.text("です").color(NamedTextColor.DARK_AQUA)));
            }
            if (teamW.hasEntry(p.getName())) {
                p.sendMessage(Component.text("あなたは").color(NamedTextColor.DARK_AQUA)
                        .append(Component.text("人狼陣営").color(NamedTextColor.RED))
                        .append(Component.text("です").color(NamedTextColor.DARK_AQUA)));
                p.sendMessage(Component.text("仲間は").color(NamedTextColor.DARK_AQUA)
                        .append(Component.text(teamW.getEntries().toString()).color(NamedTextColor.RED))
                        .append(Component.text("です").color(NamedTextColor.DARK_AQUA)));
            }
            if (teamM.hasEntry(p.getName())) {
                p.sendMessage(Component.text("あなたは").color(NamedTextColor.DARK_AQUA)
                        .append(Component.text("狂人陣営").color(NamedTextColor.LIGHT_PURPLE))
                        .append(Component.text("です").color(NamedTextColor.DARK_AQUA)));
                p.sendMessage(Component.text("人狼は").color(NamedTextColor.DARK_AQUA)
                        .append(Component.text(teamW.getEntries().toString()).color(NamedTextColor.RED))
                        .append(Component.text("です").color(NamedTextColor.DARK_AQUA)));
            }

            p.sendMessage(Component.text("-----------------------------------------------------").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));

            for (Player playerAdminTAG1 : Bukkit.getOnlinePlayers()) {
                if (p.getScoreboardTags().contains("Admin1")) {
                    playerAdminTAG1.teleport(p.getLocation());
                }
            }
            p.removeScoreboardTag("Admin1");
            p.setStatistic(org.bukkit.Statistic.DEATHS, 0);
        }
        VillagerCount = 2 * (ALLPlayerCount - BeforeWolfPlayerCount -BeforeMadmanPlayerCount);
        MadmanCount = 2 * BeforeMadmanPlayerCount;
    }

}
