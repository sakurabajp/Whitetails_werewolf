package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
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
    int BeforeVampirePlayerCount = 0;
    int VillagerCount;
    int MadmanCount;
    int AfterVampireCount;
    int ALLPlayerCount;

    private void showPlayerRoleTitle(Player player, Team teamV, Team teamW, Team teamM, Team teamVP) {
        Component roleText;
        if (teamW.hasEntry(player.getName())) {
            roleText = Component.text("人狼").color(NamedTextColor.RED).decorate(TextDecoration.BOLD);
        } else if (teamM.hasEntry(player.getName())) {
            roleText = Component.text("共犯者").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD);
        }else if (teamVP.hasEntry(player.getName())) {
            roleText = Component.text("吸血鬼").color(NamedTextColor.LIGHT_PURPLE).decorate(TextDecoration.BOLD);
        } else if (teamV.hasEntry(player.getName())) {
            roleText = Component.text("村人").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD);
        } else {
            roleText = Component.text("不明").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD);
        }
        player.sendTitlePart(TitlePart.TITLE, roleText);
        player.sendTitlePart(TitlePart.SUBTITLE, Component.text("GAME START").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD).decorate(TextDecoration.UNDERLINED));
    }


    public void GameStart() {
        final ScoreboardManager managerW = Bukkit.getScoreboardManager();
        final ScoreboardManager managerV = Bukkit.getScoreboardManager();
        final ScoreboardManager managerM = Bukkit.getScoreboardManager();
        final ScoreboardManager managerVP = Bukkit.getScoreboardManager();

        final Scoreboard scoreboardW = Objects.requireNonNull(managerW).getMainScoreboard();
        final Scoreboard scoreboardV = Objects.requireNonNull(managerV).getMainScoreboard();
        final Scoreboard scoreboardM = Objects.requireNonNull(managerM).getMainScoreboard();
        final Scoreboard scoreboardVP = Objects.requireNonNull(managerVP).getMainScoreboard();

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
        if (scoreboardVP.getTeam("vampire") != null) {
            Objects.requireNonNull(scoreboardVP.getTeam("vampire")).unregister();
        }
        Team teamW = scoreboardW.registerNewTeam("wolf");
        Team teamM = scoreboardM.registerNewTeam("madman");
        Team teamV = scoreboardV.registerNewTeam("villager");
        Team teamVP = scoreboardVP.registerNewTeam("vampire");
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
                return;
            } else if (teamW.hasEntry(MadmanTeamPlayers.getName())) {
                return;
            }
            teamM.addPlayer(MadmanTeamPlayers);
            // MadmanTeamPlayers.sendMessage("貴方は狂人に選ばれました");
        }
        for (int i = BeforeVampirePlayerCount; i > 0; i -= 1) {
            Random random = new Random();
            Player VampireTeamPlayers = Players.get(random.nextInt(Players.size()));
            if (teamM.hasEntry(VampireTeamPlayers.getName())) {
                return;
            }if (teamW.hasEntry(VampireTeamPlayers.getName())) {
                return;
            }if (teamVP.hasEntry(VampireTeamPlayers.getName())) {
                return;
            }
            else if (teamVP.hasEntry(VampireTeamPlayers.getName())) {
                return;
            }
            teamVP.addPlayer(VampireTeamPlayers);
        }
        new System().changeGameRule();
        for (Player p : Bukkit.getOnlinePlayers()) {
            ALLPlayerCount++;
            p.setGameMode(GameMode.ADVENTURE);
            Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(40.0);
            p.setHealth(40.0);
            p.setFoodLevel(30);
            p.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 10, 80, true, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 10, 80, true, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 80, true, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 5, true, false));
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 0.5f, 1.0f);
            p.getInventory().clear();
            p.getActivePotionEffects().clear();

            // 改良されたタイトル表示を呼び出す
            showPlayerRoleTitle(p, teamV, teamW, teamM, teamVP);

            // Component APIを使用したメッセージ送信
            p.sendMessage(Component.text("---------------------------------------------").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));
            p.sendMessage(Component.text("プレイヤー人数").color(TextColor.color(13, 217, 153)).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(Bukkit.getServer().getOnlinePlayers().size()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text(""));
            p.sendMessage(Component.text("役職は以下の人数で設定されています").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text("人狼　").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(teamW.getEntries().size()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text("共犯者").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(teamM.getEntries().size()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text("吸血鬼").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(teamVP.getEntries().size()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text("村人　").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true)
                    .append(Component.text(" : ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                    .append(Component.text(teamVP.getEntries().size()).color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, false)));
            p.sendMessage(Component.text(""));
            if (teamV.hasEntry(p.getName())) {
                p.sendMessage(Component.text("あなたの役職は").color(NamedTextColor.WHITE)
                        .append(Component.text(" 村人 ").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD ,true))
                        .append(Component.text("です").color(NamedTextColor.WHITE)));
            }
            if (teamW.hasEntry(p.getName())) {
                p.sendMessage(Component.text("あなたの役職は").color(NamedTextColor.WHITE)
                        .append(Component.text(" 人狼 ").color(NamedTextColor.RED).decoration(TextDecoration.BOLD ,true))
                        .append(Component.text("です").color(NamedTextColor.WHITE)));
                p.sendMessage(Component.text("仲間は").color(NamedTextColor.WHITE)
                        .append(Component.text(teamW.getEntries().toString()).color(NamedTextColor.RED))
                        .append(Component.text("です").color(NamedTextColor.WHITE)));
            }
            if (teamM.hasEntry(p.getName())) {
                p.sendMessage(Component.text("あなたの役職は").color(NamedTextColor.WHITE)
                        .append(Component.text(" 共犯者 ").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD ,true))
                        .append(Component.text("です").color(NamedTextColor.WHITE)));
            }
            if (teamVP.hasEntry(p.getName())) {
                p.sendMessage(Component.text("あなたの役職は").color(NamedTextColor.WHITE)
                        .append(Component.text(" 吸血鬼 ").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.BOLD ,true))
                        .append(Component.text("です").color(NamedTextColor.WHITE)));
            }

            p.sendMessage(Component.text("---------------------------------------------").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));

            for (Player playerAdminTAG1 : Bukkit.getOnlinePlayers()) {
                if (p.getScoreboardTags().contains("Admin1")) {
                    playerAdminTAG1.teleport(p.getLocation());
                }
            }
            p.removeScoreboardTag("Admin1");
            p.setStatistic(org.bukkit.Statistic.DEATHS, 0);
        }
        VillagerCount = (ALLPlayerCount - BeforeWolfPlayerCount -BeforeMadmanPlayerCount - BeforeVampirePlayerCount);
        MadmanCount = BeforeMadmanPlayerCount;
    }

}
