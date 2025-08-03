package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class setVillager implements Listener {
    Component axe = Component.text("村人(武器)のスポーン場所をセットする").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true);
    Component axe2 = Component.text("村人(補助)のスポーン場所をセットする").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true);

    public void getItem(Player player) {
        ItemStack setTaskItem = new ItemStack(Material.GOLDEN_AXE);
        ItemMeta setTaskItemMeta = setTaskItem.getItemMeta();
        setTaskItemMeta.displayName(axe);
        setTaskItem.setItemMeta(setTaskItemMeta);
        player.getInventory().addItem(setTaskItem);

        ItemStack setTaskItem2 = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta setTaskItem2Meta = setTaskItem2.getItemMeta();
        setTaskItem2Meta.displayName(axe2);
        setTaskItem2.setItemMeta(setTaskItem2Meta);
        player.getInventory().addItem(setTaskItem2);
    }

    @EventHandler
    public void setStand(PlayerInteractEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLDEN_AXE && Objects.requireNonNull(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().displayName()).equals(axe)) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getHand() == EquipmentSlot.HAND) {
                    Block b = e.getClickedBlock();
                    Objects.requireNonNull(b).getWorld().spawn(b.getLocation().add(0.5, 1, 0.5), Villager.class, villager -> {
                        villager.setAI(false);
                        villager.setAdult();
                        villager.customName(Component.text("村人(武器)").color(NamedTextColor.WHITE));
                        villager.addScoreboardTag("Villager_Weapon");
                        villager.setInvulnerable(true);
                        villager.setCanPickupItems(false);
                        villager.setVillagerType(Villager.Type.SNOW);
                        villager.setProfession(Villager.Profession.WEAPONSMITH);
                        villager.setSilent(true);
                        setVillagerTrades(villager);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    });
                }
            }
            e.setCancelled(true);
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_AXE && Objects.requireNonNull(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().displayName()).equals(axe2)) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getHand() == EquipmentSlot.HAND) {
                    Block b = e.getClickedBlock();
                    Objects.requireNonNull(b).getWorld().spawn(b.getLocation().add(0.5, 1, 0.5), Villager.class, villager -> {
                        villager.setAI(false);
                        villager.setAdult();
                        villager.customName(Component.text("村人(補助)").color(NamedTextColor.WHITE));
                        villager.addScoreboardTag("Villager_auxiliary");
                        villager.setInvulnerable(true);
                        villager.setCanPickupItems(false);
                        villager.setVillagerType(Villager.Type.SNOW);
                        villager.setProfession(Villager.Profession.LIBRARIAN);
                        villager.setSilent(true);
                        setVillagerTrades2(villager);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    });
                }
            }
            e.setCancelled(true);
        }
    }

    // AI生成
    private void setVillagerTrades(Villager villager) {
        // 取引リストを作成
        List<MerchantRecipe> recipes = new ArrayList<>();

        // 取引例1:
        MerchantRecipe recipe1 = new MerchantRecipe(new ItemStack(new OriginalItem().bow), 2147483647); // 最大取引回数
        recipe1.addIngredient(new ItemStack(Material.EMERALD, 2)); // 必要なアイテム
        recipes.add(recipe1);

        MerchantRecipe recipe2 = new MerchantRecipe(new ItemStack(new OriginalItem().arrow), 2147483647);
        recipe2.addIngredient(new ItemStack(Material.EMERALD, 2));
        recipes.add(recipe2);

        MerchantRecipe recipe3 = new MerchantRecipe(new ItemStack(new OriginalItem().steak), 2147483647);
        recipe3.addIngredient(new ItemStack(Material.EMERALD, 1));
        recipes.add(recipe3);

        MerchantRecipe recipe4 = new MerchantRecipe(new ItemStack(new OriginalItem().potion), 2147483647);
        recipe4.addIngredient(new ItemStack(Material.EMERALD, 4));
        recipes.add(recipe4);

        MerchantRecipe recipe5 = new MerchantRecipe(new ItemStack(new OriginalItem().grenade), 2147483647);
        recipe5.addIngredient(new ItemStack(Material.EMERALD, 2));
        recipes.add(recipe5);

        MerchantRecipe recipe6 = new MerchantRecipe(new ItemStack(new OriginalItem().trident), 2147483647);
        recipe6.addIngredient(new ItemStack(Material.EMERALD, 3));
        recipes.add(recipe6);

        MerchantRecipe recipe7 = new MerchantRecipe(new ItemStack(new OriginalItem().wolf_axe), 2147483647);
        recipe7.addIngredient(new ItemStack(Material.EMERALD, 4));
        recipes.add(recipe7);

        // 村人に取引リストを設定
        villager.setRecipes(recipes);
    }

    // コピペ
    private void setVillagerTrades2(Villager villager) {
        // 取引リストを作成
        List<MerchantRecipe> recipes = new ArrayList<>();

        // 取引例1:
        MerchantRecipe recipe1 = new MerchantRecipe(new ItemStack(new OriginalItem().fortune), 2147483647); // 最大取引回数
        recipe1.addIngredient(new ItemStack(Material.EMERALD, 6)); // 必要なアイテム
        recipes.add(recipe1);

        MerchantRecipe recipe2 = new MerchantRecipe(new ItemStack(new OriginalItem().medium), 2147483647);
        recipe2.addIngredient(new ItemStack(Material.EMERALD, 4));
        recipes.add(recipe2);

        MerchantRecipe recipe3 = new MerchantRecipe(new ItemStack(new OriginalItem().knight), 2147483647);
        recipe3.addIngredient(new ItemStack(Material.EMERALD, 2));
        recipes.add(recipe3);

        MerchantRecipe recipe4 = new MerchantRecipe(new ItemStack(new OriginalItem().accomplice), 2147483647);
        recipe4.addIngredient(new ItemStack(Material.EMERALD, 4));
        recipes.add(recipe4);

        MerchantRecipe recipe5 = new MerchantRecipe(new ItemStack(new OriginalItem().cross), 2147483647);
        recipe5.addIngredient(new ItemStack(Material.EMERALD, 2));
        recipes.add(recipe5);

        MerchantRecipe recipe6 = new MerchantRecipe(new ItemStack(new OriginalItem().providence), 2147483647);
        recipe6.addIngredient(new ItemStack(Material.EMERALD, 3));
        recipes.add(recipe6);

        MerchantRecipe recipe7 = new MerchantRecipe(new ItemStack(new OriginalItem().talisman), 2147483647);
        recipe7.addIngredient(new ItemStack(Material.EMERALD, 1));
        recipes.add(recipe7);

        // 村人に取引リストを設定
        villager.setRecipes(recipes);
    }
}
