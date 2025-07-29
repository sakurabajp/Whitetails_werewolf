package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class SetArmorStand implements Listener {

    Component pickaxe = Component.text("スケルトンのスポーン場所をセットする").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true);
    public void getItem(Player player) {
        ItemStack setTaskItem = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta setTaskItemMeta = setTaskItem.getItemMeta();
        setTaskItemMeta.displayName(pickaxe);
        setTaskItem.setItemMeta(setTaskItemMeta);
        player.getInventory().addItem(setTaskItem);
    }

    Inventory RGUI = Bukkit.createInventory(null, 9,  Component.text("設定").decoration(TextDecoration.BOLD, true).color(NamedTextColor.DARK_AQUA));

    @EventHandler
    public void setStand(PlayerInteractEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLDEN_PICKAXE && Objects.requireNonNull(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().displayName()).equals(pickaxe)) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getHand() == EquipmentSlot.HAND) {
                    Block b = e.getClickedBlock();
                    Objects.requireNonNull(b).getWorld().spawn(b.getLocation().add(0.5, 1, 0.5), ArmorStand.class, armorStand -> {
                        armorStand.setBasePlate(true); // 防具建てが地面に設置されないようにする
                        armorStand.customName(Component.text("スケルトンの出現場所の候補").color(NamedTextColor.GOLD));
                        armorStand.addScoreboardTag("skeleton_spawn");
                        armorStand.setInvulnerable(true);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    });
                }
            }
            if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                ItemStack ResetItem = new ItemStack(Material.ARMOR_STAND);
                ItemMeta ResetItemMeta = ResetItem.getItemMeta();
                Objects.requireNonNull(ResetItemMeta).displayName(Component.text("出現場所を全てリセットする").color(NamedTextColor.AQUA));
                ResetItem.setItemMeta(ResetItemMeta);
                RGUI.setItem(2, ResetItem);
                ItemStack GlowItem = new ItemStack(Material.GLOW_INK_SAC);
                ItemMeta GlowItemMeta = GlowItem.getItemMeta();
                Objects.requireNonNull(GlowItemMeta).displayName(Component.text("スポーン場所を発光させる").color(NamedTextColor.AQUA));
                GlowItem.setItemMeta(GlowItemMeta);
                RGUI.setItem(4, GlowItem);
                ItemStack unGlowItem = new ItemStack(Material.INK_SAC);
                ItemMeta unGlowItemMeta = unGlowItem.getItemMeta();
                Objects.requireNonNull(unGlowItemMeta).displayName(Component.text("スポーン場所の発光を解除").color(NamedTextColor.AQUA));
                unGlowItem.setItemMeta(unGlowItemMeta);
                RGUI.setItem(6, unGlowItem);
                e.getPlayer().openInventory(RGUI);
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
            }
                e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            Inventory clickedInventory = event.getClickedInventory();
            if (clickedInventory == RGUI) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null && clickedItem.getType().equals(Material.ARMOR_STAND)) {
                    for (Entity entity : player.getWorld().getEntities()) {
                        if (entity instanceof ArmorStand armorStand && entity.getScoreboardTags().contains("skeleton_spawn")) {
                            armorStand.remove();
                        }
                    }
                    player.closeInventory();
                    player.sendMessage(Component.text("スケルトンのスポーン場所を全て削除しました").color(NamedTextColor.GOLD));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                }
                if (clickedItem != null && clickedItem.getType().equals(Material.GLOW_INK_SAC)) {
                    int count = 0;
                    for (Entity entity : player.getWorld().getEntities()) {
                        if (entity instanceof ArmorStand armorStand && entity.getScoreboardTags().contains("skeleton_spawn")) {
                            armorStand.setGlowing(true);
                            count += 1;
                        }
                    }
                    player.closeInventory();
                    player.sendMessage(Component.text("スケルトンのスポーン場所を全て発光させました").color(NamedTextColor.GOLD));
                    player.sendMessage(Component.text("スケルトンのスポーン場所の総数 : ").color(NamedTextColor.WHITE).append(Component.text(count).color(NamedTextColor.GOLD)));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }
                if (clickedItem != null && clickedItem.getType().equals(Material.INK_SAC)) {
                    for (Entity entity : player.getWorld().getEntities()) {
                        if (entity instanceof ArmorStand armorStand && entity.getScoreboardTags().contains("skeleton_spawn")) {
                            armorStand.setGlowing(false);
                        }
                    }
                    player.closeInventory();
                    player.sendMessage(Component.text("金のスポーン場所の発光を全て解除しました").color(NamedTextColor.GOLD));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.1f);
                }
                event.setCancelled(true);
            }
        }
    }
}
