package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import net.cherryleaves.whitetails_werewolf.classic_config.potion;
import org.bukkit.potion.PotionEffectType;

import javax.swing.text.StyleContext;
import java.util.List;

public class OriginalItem {

    // 武器
    ItemStack bow = setItem(Material.BOW, Component.text("弓"), BOW_LORE, true, 1);
    ItemStack arrow = setItem(Material.ARROW, Component.text("矢"), ARROW_LORE, false, 1);
    ItemStack steak = setItem(Material.COOKED_BEEF, Component.text("ステーキ"), STEAK_LORE, false, 5);
    ItemStack potion = new potion().makePotion(Color.fromRGB(230, 230, 250), Component.text("透明化"), PotionEffectType.INVISIBILITY, 20 * 15, 0, false, POTION_LORE);
    ItemStack grenade = setItem(Material.SNOWBALL, Component.text("スタングレネード"), GRENADE_LORE, false, 1);
    ItemStack trident = setItem(Material.TRIDENT, Component.text("怨念の槍"), TRIDENT_LORE, true, 1);
    ItemStack wolf_axe = setItem(Material.STONE_AXE, Component.text("人狼の斧").color(NamedTextColor.RED), AXE_LORE, true, 1);

    // 補助
    ItemStack fortune = setItem(Material.HEART_OF_THE_SEA, Component.text("占い師の心").color(NamedTextColor.WHITE), FORTUNE_LORE, false, 1);
    public ItemStack medium = setItem(Material.GUNPOWDER, Component.text("霊媒師の遺灰"), MEDIUM_LORE, false, 1);
    ItemStack knight = setItem(Material.GOLDEN_HORSE_ARMOR, Component.text("騎士の祈り"), KNIGHT_LORE, false, 1);
    public ItemStack accomplice = setItem(Material.END_CRYSTAL, Component.text("共犯者の目").color(NamedTextColor.GRAY), ACCOMPLICE_LORE, false, 1);
    ItemStack cross = setItem(Material.NETHER_STAR, Component.text("聖なる十字架").color(NamedTextColor.WHITE), CROSS_LORE, false, 1);
    public ItemStack providence = setItem(Material.SUNFLOWER, Component.text("プロビデンスの眼光"), PROVIDENCE_LORE, false, 1);
    ItemStack talisman = setItem(Material.PAPER, Component.text("天啓の呪符"), TALISMAN_LORE, false, 1);

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
            Component.text("発射には通常の矢を用いる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("※一回で壊れる").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
            Component.text("※スケルトンを倒しても、エメラルドはドロップしない").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> ARROW_LORE = List.of(
            Component.text("弓を使う際に必要になる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("※一回で無くなる").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> STEAK_LORE = List.of(
            Component.text("右クリック長押しで使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true)
    );
    private static final List<Component> POTION_LORE = List.of(
            Component.text("右クリック長押しで使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("15秒間").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false).append(Component.text("体が透明になる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
            Component.text("手に持っているアイテムは透明にならない").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> GRENADE_LORE = List.of(
            Component.text("右クリック長押しで投擲").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("当てた対象を").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false).append(Component.text("10秒間").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)).append(Component.text("盲目にし、").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
            Component.text("移動不能にする").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> TRIDENT_LORE = List.of(
            Component.text("殴って使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true).append(Component.text("または").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)),
            Component.text("右クリック長押し→離して投擲").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("プレイヤーを二発で倒せる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("外した場合は返ってくる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("※一回で壊れる").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> AXE_LORE = List.of(
            Component.text("殴って使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("プレイヤーを一撃で倒せる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("昼のあいだは一度しか使えない").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("※一回で壊れる").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
            Component.text("※人狼以外購入できない").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
    );

    private static final List<Component> FORTUNE_LORE = List.of(
            Component.text("購入した数だけ看板から役職を").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("見ることができる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("※占いは一夜につき一度のみ").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> MEDIUM_LORE = List.of(
            Component.text("Qで使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("※死亡者全員の名前がわかる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> KNIGHT_LORE = List.of(
            Component.text("看板に使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true).append(Component.text(" / ").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, true).append(Component.text("夜のみ使用可能").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, true))),
            Component.text("日が昇るまで").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false).append(Component.text("一度だけ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false).append(Component.text("対象を致命傷から")).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
            Component.text("護ることができる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("護衛が成功すると").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false).append(Component.text("通知が届く").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)),
            Component.text("※自分には使うことができない").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
            Component.text("　人狼にも使用できるが、効果はない").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> ACCOMPLICE_LORE = List.of(
            Component.text("Qで使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("人狼").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false).append(Component.text("誰か一人の名前がわかる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
            Component.text("※共犯者以外使用できない").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> CROSS_LORE = List.of(
            Component.text("殴って使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("吸血鬼").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false).append(Component.text("を一撃で倒せる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
            Component.text("※一回で壊れる").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> PROVIDENCE_LORE = List.of(
            Component.text("Qで使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("自分以外の全生存者を").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false).append(Component.text("30秒間").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)),
            Component.text("壁越しでも視認可能にする").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
    );
    private static final List<Component> TALISMAN_LORE = List.of(
            Component.text("Qで使用").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true),
            Component.text("使ってから次の朝まで").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
            Component.text("自分が占われたこと").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false).append(Component.text("を察知できる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
    );
}
