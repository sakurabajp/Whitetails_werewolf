package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class GUI implements Listener {
    private final Inventory StartGUI = Bukkit.createInventory(null, 9, Component.text("プレイヤー人数と役職数の確認").color(NamedTextColor.DARK_AQUA));
    private final gameStart gameStartInstance = new gameStart();

    public void openGUI(Player AdminPlayer) {
        // GUI表示
        // 人狼数表示アイテム
        ItemStack WolfPlayerCountItem = new ItemStack(Material.FIRE_CORAL);
        ItemMeta WolfPlayerCountItemMeta = WolfPlayerCountItem.getItemMeta();
        Objects.requireNonNull(WolfPlayerCountItemMeta).displayName(Component.text("現在の設定では人狼の数は").color(NamedTextColor.DARK_AQUA)
                .append(Component.text(gameStartInstance.BeforeWolfPlayerCount).color(NamedTextColor.GOLD))
                .append(Component.text("人です").color(NamedTextColor.DARK_AQUA)));
        WolfPlayerCountItem.setItemMeta(WolfPlayerCountItemMeta);
        StartGUI.setItem(2, WolfPlayerCountItem);
        // 人狼数減らす
        ItemStack WolfCountDownItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta WolfCountDownItemMeta = WolfCountDownItem.getItemMeta();
        Objects.requireNonNull(WolfCountDownItemMeta).displayName(Component.text("クリックで人狼の数を減らす").color(NamedTextColor.RED));
        WolfCountDownItem.setItemMeta(WolfCountDownItemMeta);
        StartGUI.setItem(1, WolfCountDownItem);
        // 人狼数増やす  
        ItemStack WolfCountUpItem = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta WolfCountUpItemMeta = WolfCountUpItem.getItemMeta();
        Objects.requireNonNull(WolfCountUpItemMeta).displayName(Component.text("クリックで人狼の数を増やす").color(NamedTextColor.BLUE));
        WolfCountUpItem.setItemMeta(WolfCountUpItemMeta);
        StartGUI.setItem(3, WolfCountUpItem);
        // 狂人数表示アイテム
        ItemStack MadmanPlayerCountItem = new ItemStack(Material.BUBBLE_CORAL);
        ItemMeta MadmanPlayerCountItemMeta = MadmanPlayerCountItem.getItemMeta();
        Objects.requireNonNull(MadmanPlayerCountItemMeta).displayName(Component.text("現在の設定では狂人の数は").color(NamedTextColor.DARK_AQUA)
                .append(Component.text(gameStartInstance.BeforeMadmanPlayerCount).color(NamedTextColor.GOLD))
                .append(Component.text("人です").color(NamedTextColor.DARK_AQUA)));
        MadmanPlayerCountItem.setItemMeta(MadmanPlayerCountItemMeta);
        StartGUI.setItem(6, MadmanPlayerCountItem);
        // 狂人数減らす
        ItemStack MadmanCountDownItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta MadmanCountDownItemMeta = MadmanCountDownItem.getItemMeta();
        Objects.requireNonNull(MadmanCountDownItemMeta).displayName(Component.text("クリックで狂人の数を減らす").color(NamedTextColor.RED));
        MadmanCountDownItem.setItemMeta(MadmanCountDownItemMeta);
        StartGUI.setItem(5, MadmanCountDownItem);
        // 狂人数増やす
        ItemStack MadmanCountUpItem = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta MadmanCountUpItemMeta = MadmanCountUpItem.getItemMeta();
        Objects.requireNonNull(MadmanCountUpItemMeta).displayName(Component.text("クリックで狂人の数を増やす").color(NamedTextColor.BLUE));
        MadmanCountUpItem.setItemMeta(MadmanCountUpItemMeta);
        StartGUI.setItem(7, MadmanCountUpItem);
        //ゲームスタートアイテム
        ItemStack GameStartItem = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta GameStartItemMeta = GameStartItem.getItemMeta();
        Objects.requireNonNull(GameStartItemMeta).displayName(Component.text("ゲームスタート！").color(NamedTextColor.YELLOW));
        GameStartItem.setItemMeta(GameStartItemMeta);
        StartGUI.setItem(8, GameStartItem);

        // プレイヤーに上まで作ってきたGUIを表示する
        assert AdminPlayer != null;
        AdminPlayer.openInventory(StartGUI);
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player GUIClickedPlayer) {
            // クリックされたGUIを取得する
            Inventory clickedInventory = event.getClickedInventory();
            if (clickedInventory != null && clickedInventory.equals(StartGUI)) {
                GUIClickedPlayer.addScoreboardTag("Admin1");
                event.setCancelled(true);

                // クリックされたアイテムを取得する
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null) return;

                if (clickedItem.getType() == Material.RED_STAINED_GLASS_PANE) {
                    Component ClickedItemName = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
                    if (ClickedItemName == null) return;

                    if (ClickedItemName.equals(Component.text("クリックで人狼の数を減らす").color(NamedTextColor.RED))) {
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                        if (gameStartInstance.BeforeWolfPlayerCount > 1) {
                            gameStartInstance.BeforeWolfPlayerCount--;
                        } else {
                            GUIClickedPlayer.sendMessage(Component.text("人狼の人数を1人未満にすることは出来ません。").color(NamedTextColor.DARK_RED));
                            GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
                        }
                    } else if (ClickedItemName.equals(Component.text("クリックで狂人の数を減らす").color(NamedTextColor.RED))) {
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                        if (gameStartInstance.BeforeMadmanPlayerCount > 0) {
                            gameStartInstance.BeforeMadmanPlayerCount--;
                        } else {
                            GUIClickedPlayer.sendMessage(Component.text("狂人の人数を0人未満にすることは出来ません。").color(NamedTextColor.DARK_RED));
                            GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
                        }
                    }
                    openGUI(GUIClickedPlayer);
                } else if (clickedItem.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                    Component ClickedItemName = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
                    if (ClickedItemName == null) return;

                    if (ClickedItemName.equals(Component.text("クリックで人狼の数を増やす").color(NamedTextColor.BLUE))) {
                        gameStartInstance.BeforeWolfPlayerCount++;
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.2f);
                    } else if (ClickedItemName.equals(Component.text("クリックで狂人の数を増やす").color(NamedTextColor.BLUE))) {
                        gameStartInstance.BeforeMadmanPlayerCount++;
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.2f);
                    }
                    openGUI(GUIClickedPlayer);
                } else if (clickedItem.getType() == Material.TOTEM_OF_UNDYING) {
                    new gameStart().GameStart();
                } else if(clickedItem.getType() == Material.FIRE_CORAL || clickedItem.getType() == Material.BUBBLE_CORAL){
                    GUIClickedPlayer.sendMessage(Component.text("サンゴに触らないで！！").color(NamedTextColor.AQUA));
                    GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.ENTITY_RABBIT_DEATH, 1.0f, 1.2f);
                }
                else {return;}
            }
        }
    }
}