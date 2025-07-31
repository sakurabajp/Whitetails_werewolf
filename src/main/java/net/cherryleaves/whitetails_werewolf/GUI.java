package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
    private static final gameStart gameStartInstance = new gameStart();
    public static Inventory StartGUI = Bukkit.createInventory(null, 18, Component.text("プレイヤー人数と役職数の確認").color(NamedTextColor.DARK_AQUA));

    Component addWolfText = Component.text("クリックで人狼の数を増やす").color(NamedTextColor.BLUE);
    Component removeWolfText = Component.text("クリックで人狼の数を減らす").color(NamedTextColor.RED);
    Component addMadmanText = Component.text("クリックで共犯者の数を増やす").color(NamedTextColor.BLUE);
    Component removeMadmanText = Component.text("クリックで共犯者の数を減らす").color(NamedTextColor.RED);
    Component addVampireText = Component.text("クリックで吸血鬼の数を増やす").color(NamedTextColor.BLUE);
    Component removeVampireText = Component.text("クリックで吸血鬼の数を減らす").color(NamedTextColor.RED);

    public void openGUI(Player AdminPlayer) {
        // GUI表示
        // 人狼数表示アイテム
        Component WolfCountText = Component.text("現在の設定では").color(NamedTextColor.DARK_AQUA)
                .append(Component.text("人狼").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, true))
                .append(Component.text("の数は").color(NamedTextColor.DARK_AQUA).decoration(TextDecoration.BOLD, false))
                .append(Component.text(gameStartInstance.BeforeWolfPlayerCount).color(NamedTextColor.GOLD))
                .append(Component.text("人です").color(NamedTextColor.DARK_AQUA));
        ItemStack WolfCountItem = makeItemMeta(Material.FIRE_CORAL, WolfCountText);
        WolfCountItem.setAmount(gameStartInstance.BeforeWolfPlayerCount);
        StartGUI.setItem(2, WolfCountItem);
        // 人狼数減らす
        StartGUI.setItem(1, makeItemMeta(Material.RED_STAINED_GLASS_PANE, removeWolfText));
        // 人狼数増やす
        StartGUI.setItem(3, makeItemMeta(Material.BLUE_STAINED_GLASS_PANE, addWolfText));
        // 狂人数表示アイテム
        Component MadmanCountText = Component.text("現在の設定では").color(NamedTextColor.DARK_AQUA)
                .append(Component.text("共犯者").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true))
                .append(Component.text("の数は").color(NamedTextColor.DARK_AQUA).decoration(TextDecoration.BOLD, false))
                .append(Component.text(gameStartInstance.BeforeMadmanPlayerCount).color(NamedTextColor.GOLD))
                .append(Component.text("人です").color(NamedTextColor.DARK_AQUA));
        ItemStack MadmanCountItem = makeItemMeta(Material.BUBBLE_CORAL, MadmanCountText);
        MadmanCountItem.setAmount(gameStartInstance.BeforeMadmanPlayerCount);
        StartGUI.setItem(6, MadmanCountItem);
        if(gameStartInstance.BeforeMadmanPlayerCount == 0) {
            StartGUI.setItem(6, makeItemMeta(Material.DEAD_BUBBLE_CORAL, MadmanCountText));
        }
        // 狂人数減らす
        StartGUI.setItem(5, makeItemMeta(Material.RED_STAINED_GLASS_PANE, removeMadmanText));
        // 狂人数増やす
        StartGUI.setItem(7, makeItemMeta(Material.BLUE_STAINED_GLASS_PANE, addMadmanText));
        // 吸血鬼表示アイテム
        Component VampireCountText = Component.text("現在の設定では").color(NamedTextColor.DARK_AQUA)
                .append(Component.text("吸血鬼").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true))
                .append(Component.text("の数は").color(NamedTextColor.DARK_AQUA).decoration(TextDecoration.BOLD, false))
                .append(Component.text(gameStartInstance.BeforeVampirePlayerCount).color(NamedTextColor.GOLD))
                .append(Component.text("人です").color(NamedTextColor.DARK_AQUA));
        ItemStack VampireCountItem = makeItemMeta(Material.TUBE_CORAL, VampireCountText);
        VampireCountItem.setAmount(gameStartInstance.BeforeVampirePlayerCount);
        StartGUI.setItem(11, VampireCountItem);
        if(gameStartInstance.BeforeVampirePlayerCount == 0) {
            StartGUI.setItem(11, makeItemMeta(Material.DEAD_TUBE_CORAL, VampireCountText));
        }
        // 吸血鬼数減らす
        StartGUI.setItem(10, makeItemMeta(Material.RED_STAINED_GLASS_PANE, removeVampireText));
        // 吸血鬼数増やす
        StartGUI.setItem(12, makeItemMeta(Material.BLUE_STAINED_GLASS_PANE, addVampireText));
        // 未設定アイテム
        StartGUI.setItem(15, makeItemMeta(Material.DEAD_HORN_CORAL, Component.text("未実装")));
        // 未設定アイテム減らす
        StartGUI.setItem(14, makeItemMeta(Material.WHITE_STAINED_GLASS_PANE, Component.text("未実装")));
        // 未設定アイテム増やす
        StartGUI.setItem(16, makeItemMeta(Material.WHITE_STAINED_GLASS_PANE, Component.text("未実装")));
        //空白埋め
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 2; j++) {
                StartGUI.setItem(j * 9 + i * 4, makeItemMeta(Material.GRAY_STAINED_GLASS_PANE, Component.text("")));
            }
        }
        //ゲームスタートアイテム
        ItemStack GameStartItem = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta GameStartItemMeta = GameStartItem.getItemMeta();
        Objects.requireNonNull(GameStartItemMeta).displayName(Component.text("ゲームスタート！").color(NamedTextColor.YELLOW));
        GameStartItem.setItemMeta(GameStartItemMeta);
        StartGUI.setItem(17, GameStartItem);

        // プレイヤーに上まで作ってきたGUIを表示する
        assert AdminPlayer != null;
        AdminPlayer.openInventory(StartGUI);
    }

    public ItemStack makeItemMeta(Material m, Component name) {
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.displayName(name);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player GUIClickedPlayer) {
            // クリックされたGUIを取得する
            Inventory clickedInventory = event.getClickedInventory();
            assert clickedInventory != null;
            if (clickedInventory == StartGUI) {
                GUIClickedPlayer.addScoreboardTag("Admin1");
                event.setCancelled(true);
                ItemStack clickedItem = event.getCurrentItem();
                if(clickedItem == null) {return;}else {clickedItem.getType();}
                if (clickedItem.getType() == Material.RED_STAINED_GLASS_PANE) {
                    Component ClickedItemName = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
                    if (ClickedItemName == null) return;

                    if (ClickedItemName.equals(removeWolfText)) {
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                        if (gameStartInstance.BeforeWolfPlayerCount > 1) {
                            gameStartInstance.BeforeWolfPlayerCount--;
                        } else {
                            GUIClickedPlayer.sendMessage(Component.text("人狼の人数を1人未満にすることは出来ません。").color(NamedTextColor.DARK_RED));
                            GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
                        }
                    } else if (ClickedItemName.equals(removeMadmanText)) {
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                        if (gameStartInstance.BeforeMadmanPlayerCount > 0) {
                            gameStartInstance.BeforeMadmanPlayerCount--;
                        } else {
                            GUIClickedPlayer.sendMessage(Component.text("割り当て人数を0人未満にすることは出来ません。").color(NamedTextColor.DARK_RED));
                            GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
                        }
                    } else if (ClickedItemName.equals(removeVampireText)) {
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                        if (gameStartInstance.BeforeVampirePlayerCount > 0) {
                            gameStartInstance.BeforeVampirePlayerCount--;
                        } else {
                            GUIClickedPlayer.sendMessage(Component.text("割り当て人数を0人未満にすることは出来ません。").color(NamedTextColor.DARK_RED));
                            GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
                        }
                    }
                    openGUI(GUIClickedPlayer);
                } else if (clickedItem.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                    Component ClickedItemName = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
                    if (ClickedItemName == null) return;

                    if (ClickedItemName.equals(addWolfText)) {
                        gameStartInstance.BeforeWolfPlayerCount++;
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.2f);
                    } else if (ClickedItemName.equals(addMadmanText)) {
                        gameStartInstance.BeforeMadmanPlayerCount++;
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.2f);
                    } else if (ClickedItemName.equals(addVampireText)) {
                        gameStartInstance.BeforeVampirePlayerCount++;
                        GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.2f);
                    }
                    openGUI(GUIClickedPlayer);
                } else if (clickedItem.getType() == Material.TOTEM_OF_UNDYING) {
                    gameStartInstance.GameStart();
                } else if(clickedItem.getType() == Material.FIRE_CORAL || clickedItem.getType() == Material.BUBBLE_CORAL || clickedItem.getType() == Material.TUBE_CORAL) {
                    GUIClickedPlayer.sendMessage(Component.text("サンゴに触らないで！！").color(NamedTextColor.AQUA));
                    GUIClickedPlayer.playSound(GUIClickedPlayer.getLocation(), Sound.ENTITY_RABBIT_DEATH, 1.0f, 1.2f);
                }
                else {return;}
            }
        }
    }
}