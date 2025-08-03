package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;
import net.kyori.adventure.util.HSVLike;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class System implements Listener {

    // シングルトンインスタンスを追加
    private static System instance;

    // シングルトンを取得するためのメソッド
    public static System getInstance() {
        if (instance == null) {
            instance = new System();
        }
        return instance;
    }

    // コンストラクタをprivateに変更
    private System() {
        // 初期化処理
    }

    public boolean GameNow = false;
    public int Day = 1;
    public boolean Night = false;

    @EventHandler
    public void onPlayerShootArrow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getProjectile() instanceof Arrow arrow) {
            arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
        }
    }
    
    World world = Objects.requireNonNull(Bukkit.getWorld("world"));

    public void changeGameRule(){
        if (world != null) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        }
    }

    public void changeNight() {
        spawnSkeletonAtRandomStand(10);
        world.setTime(18000); // midnight = 18000 ticks
        systemTimer.getInstance().currentTime = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Objects.requireNonNull(player.getScoreboard().getObjective("knight_check")).getScore(player.getName()).setScore(0);
        }
        Night = true;
        
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitlePart(TitlePart.TITLE, Component.text(" 夜 ").color(TextColor.color(HSVLike.fromRGB(10, 10, 200))).decorate(TextDecoration.BOLD));
            player.sendTitlePart(TitlePart.SUBTITLE, Component.text("- " + Day + "日目 -").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD).decorate(TextDecoration.ITALIC));
            player.sendMessage(Night + "←を有効にしたはず！");
        }
    }

    public void changeDay() {
        Day++;
        systemTimer.getInstance().currentTime = 0;
        Night = false;
        world.getEntities().stream()
                .filter(entity -> entity instanceof Skeleton)
                .filter(entity -> entity.getScoreboardTags().contains("game_skeleton"))
                .forEach(Entity::remove);
        world.setTime(6000); // midnight = 18000 ticks
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitlePart(TitlePart.TITLE, Component.text(" 昼 ").color(TextColor.color(HSVLike.fromRGB(250, 140, 0))).decorate(TextDecoration.BOLD));
            player.sendTitlePart(TitlePart.SUBTITLE, Component.text("- " + Day + "日目 -").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD).decorate(TextDecoration.ITALIC));
        }
    }

    public void spawnSkeletonAtRandomStand(int count) {
        World world = Bukkit.getWorld("world");
        List<Entity> entities = Objects.requireNonNull(world).getEntities();
        List<ArmorStand> stands = entities.stream()
                .filter(e -> e instanceof ArmorStand)
                .filter(e -> e.getScoreboardTags().contains("skeleton_spawn"))
                .map(e -> (ArmorStand) e)
                .toList();

        if (stands.isEmpty()) return;

        Random random = new Random();
        List<ArmorStand> availableStands = new ArrayList<>(stands);

        for (int i = 0; i < count && !availableStands.isEmpty(); i++) {
            int index = random.nextInt(availableStands.size());
            Location spawnLoc = availableStands.get(index).getLocation();
            availableStands.remove(index);

            world.spawn(spawnLoc, Skeleton.class, skeleton -> {
                skeleton.addScoreboardTag("game_skeleton");
                skeleton.setCanPickupItems(false);
                skeleton.setAI(true);
                skeleton.getEquipment().setItemInMainHand(null);
                skeleton.setPersistent(true);
            });
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player p) {
            if (event.getEntity() instanceof Skeleton skeleton) {
                if (skeleton.getScoreboardTags().contains("game_skeleton")) {
                    event.setDamage(10.0);
                }
            }
            // if(p.getInventory().getItemInMainHand() == new OriginalItem().wolf_axe){
            if(p.getInventory().getItemInMainHand().getType() == Material.STONE_AXE){
                event.setDamage(40.0);
                p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
            }
        }
        if(event.getDamager() instanceof Arrow){
            event.setDamage(40);
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            if (event.getEntity() instanceof Skeleton skeleton) {
                if (skeleton.getScoreboardTags().contains("game_skeleton")) {
                    Random random = new Random();
                    if (random.nextDouble() < 0.50) {
                        skeleton.getKiller().getInventory().addItem(new ItemStack(Material.EMERALD, 1));
                        event.setDroppedExp(0);
                    }
                }
            }
        }
        if (event.getEntity() instanceof Player p) {
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage("あなたは " + event.getEntity().getKiller() + " に殺されました");
            Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
            Team teamW = scoreboard.getTeam("wolf");
            Team teamM = scoreboard.getTeam("madman");
            Team teamV = scoreboard.getTeam("villager");
            Team teamVP = scoreboard.getTeam("vampire");
            new gameStart().ALLPlayerCount -= 1;
            if(Objects.requireNonNull(teamW).hasEntry(p.getName())){
                new gameStart().WolfCount -= 1;
            }
            if(Objects.requireNonNull(teamM).hasEntry(p.getName())){
                new gameStart().MadmanCount -= 1;
            }
            if(Objects.requireNonNull(teamVP).hasEntry(p.getName())){
                new gameStart().VampireCount -= 1;
            }
            if(Objects.requireNonNull(teamV).hasEntry(p.getName())){
                new gameStart().VillagerCount -= 1;
            }
            if(new gameStart().ALLPlayerCount - new gameStart().VampireCount - new gameStart().WolfCount - new gameStart().MadmanCount == 0){
                if(new gameStart().VampireCount >= 1){
                    new gameEnd().VampireWin();
                }
                else if(new gameStart().VampireCount <= 0){
                    new gameEnd().WolfWin();
                }
            }
            else if(new gameStart().ALLPlayerCount - new gameStart().VampireCount - new gameStart().VillagerCount - new gameStart().MadmanCount == 0){
                if(new gameStart().VampireCount >= 1){
                    new gameEnd().VampireWin();
                }
                else if(new gameStart().VampireCount <= 0){
                    new gameEnd().VillagerWin();
                }
            }
        }
    }
}
