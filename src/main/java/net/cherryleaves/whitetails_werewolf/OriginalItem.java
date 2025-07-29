package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class OriginalItem {
    
    ItemStack bow = setItem(Material.BOW, Component.text("弓"), BOW_LORE, true, 1);
    ItemStack arrow = setItem(Material.ARROW, Component.text("矢"), ARROW_LORE, false, 1);
    ItemStack steak = setItem(Material.COOKED_BEEF, Component.text("ステーキ"), STEAK_LORE, false, 5);
    ItemStack potion = setItem(Material.POTION, Component.text("透明化のポーション"), POTION_LORE, false, 1);
    ItemStack grenade = setItem(Material.SNOWBALL, Component.text("スタングレネード"), GRENADE_LORE, false, 1);
    ItemStack trident = setItem(Material.TRIDENT, Component.text("怨念の槍"), TRIDENT_LORE, true, 1);
    ItemStack wolf_axe = setItem(Material.STONE_AXE, Component.text("人狼の斧").color(NamedTextColor.RED), AXE_LORE, true, 1);

    public ItemStack setItem(Material m, Component s, List<Component> lore, Boolean d1, int a){
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(s);
        meta.lore(lore);
        if(d1.equals(true)) {
            if (meta instanceof Damageable damageable) {
                damageable.setDamage(Math.max(0, item.getType().getMaxDurability() - 1));
            }
        }
        item.setAmount(a);
        item.setItemMeta(meta);
        return item;
    }

    // ここから下アイテム説明
    private static final List<Component> BOW_LORE = List.of(
            Component.text("右クリック長押し→離して発射").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("プレイヤーを一撃で倒せる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("発射には通常の矢を用いる").color(NamedTextColor.GRAY),
            Component.text("※一回で壊れる").color(NamedTextColor.RED),
            Component.text("※スケルトンを倒しても、エメラルドはドロップしない").color(NamedTextColor.RED)
    );
    private static final List<Component> ARROW_LORE = List.of(
            Component.text("弓を使う際に必要になる").color(NamedTextColor.GRAY),
            Component.text("※一回で無くなる").color(NamedTextColor.RED)
    );
    private static final List<Component> STEAK_LORE = List.of(
            Component.text("右クリック長押しで使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true)
    );
    private static final List<Component> POTION_LORE = List.of(
            Component.text("右クリック長押しで使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("15秒間").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false).append(Component.text("体が透明になる").color(NamedTextColor.GRAY)),
            Component.text("手に持っているアイテムは透明にならない").color(NamedTextColor.GRAY)
    );
    private static final List<Component> GRENADE_LORE = List.of(
            Component.text("右クリック長押しで投擲").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("当てた対象を").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false).append(Component.text("10秒間").color(NamedTextColor.WHITE)).append(Component.text("盲目にし、").color(NamedTextColor.GRAY)),
            Component.text("移動不能にする").color(NamedTextColor.GRAY)
    );
    private static final List<Component> TRIDENT_LORE = List.of(
            Component.text("殴って使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true).append(Component.text("または").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)),
            Component.text("右クリック長押し→離して投擲").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("プレイヤーを二発で倒せる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("外した場合は返ってくる").color(NamedTextColor.GRAY),
            Component.text("※一回で壊れる").color(NamedTextColor.RED)
    );
    private static final List<Component> AXE_LORE = List.of(
            Component.text("殴って使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("プレイヤーを一撃で倒せる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("昼のあいだは一度しか使えない").color(NamedTextColor.GRAY),
            Component.text("※一回で壊れる").color(NamedTextColor.RED),
            Component.text("※人狼以外購入できない").color(NamedTextColor.RED)
    );
}
