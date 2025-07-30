package net.cherryleaves.whitetails_werewolf.classic_config;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class potion {
    public ItemStack makePotion(Color color, Component Name, PotionEffectType pet,int time, int level,Boolean b, List<Component> lore) {
        // 基本的なポーションItemStackの作成
        ItemStack potionItem = new ItemStack(Material.POTION);
        ItemMeta potionMeta1 = potionItem.getItemMeta();
        PotionMeta potionMeta = (PotionMeta) potionMeta1;

        // カスタム効果の追加（旧setBasePotionDataの代わり）
        potionMeta.addCustomEffect(new PotionEffect(pet, time, level), b);

        // 色の設定
        potionMeta.setColor(color);

        // 名前の設定（Component APIを使用）
        potionMeta.displayName(Name);

        // Loreの設定（Component APIを使用）
        potionMeta.lore(lore);

        // メタデータをItemStackに適用
        potionItem.setItemMeta(potionMeta);

        return potionItem;

    }
}
