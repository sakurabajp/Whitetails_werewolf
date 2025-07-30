package net.cherryleaves.whitetails_werewolf;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class System implements Listener {

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
    }

    public void changeDay() {
        world.getEntities().stream()
                .filter(entity -> entity instanceof Skeleton)
                .filter(entity -> entity.getScoreboardTags().contains("game_skeleton"))
                .forEach(Entity::remove);
        world.setTime(6000); // midnight = 18000 ticks
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
            else if (event.getEntity() instanceof Player player) {
                player.setGameMode(GameMode.SPECTATOR);
                // ここに役職ごとの処理を入れる
            }
        }
    }
}
