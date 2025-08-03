package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class player_sign implements Listener {

    private final Whitetails_werewolf plugin;

    // コンストラクタでMainクラスのインスタンスを受け取る
    public player_sign(Whitetails_werewolf plugin) {
        this.plugin = plugin;
    }

    public void getSign(Player p){
        p.getInventory().addItem(makeItemMeta(Material.OAK_SIGN, Component.text("プレイヤー名前登録看板").color(NamedTextColor.GREEN)));
    }

    public ItemStack makeItemMeta(Material m, Component name) {
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.displayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // 看板設置イベント
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() == Material.OAK_SIGN) {
            ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
            if (meta != null && meta.hasDisplayName() &&
                    Objects.requireNonNull(meta.displayName()).equals(Component.text("プレイヤー名前登録看板").color(NamedTextColor.GREEN))) {
                // 看板の最初の行に「プレイヤー登録」と表示
                event.line(0, Component.text("プレイヤー登録").color(NamedTextColor.GREEN));
                player.sendMessage(Component.text("プレイヤー登録用の看板を設置しました。右クリックで登録できます。").color(NamedTextColor.GREEN));
            }
        }
    }

    // 看板クリックイベント
    @EventHandler
    public void onPlayerClickSign(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block clickedBlock = e.getClickedBlock();
            if (clickedBlock != null && clickedBlock.getType().toString().endsWith("_SIGN")) {
                Sign sign = (Sign) clickedBlock.getState();

                // 1行目が「プレイヤー登録」の看板かチェック
                if (sign.getSide(Side.FRONT).line(0).equals(Component.text("プレイヤー登録").color(NamedTextColor.GREEN))) {
                    Player player = e.getPlayer();

                    // すでに登録済みかどうかを確認
                    if (isSignRegistered(sign)) {
                        // 登録済みの場合の処理
                        handleRegisteredSignClick(sign, player);
                    } else {
                        // 未登録の場合はプレイヤー名を登録
                        registerPlayerToSign(sign, player);
                        updateSignDisplay(sign, player);
                        player.sendMessage(Component.text("この看板にあなたの名前が登録されました").color(NamedTextColor.GREEN));
                    }

                    // 看板更新を保存
                    sign.update();

                    // イベントのキャンセル
                    e.setCancelled(true);
                }
            }
        }
    }

    // 看板が登録済みかどうかを確認するメソッド
    private boolean isSignRegistered(Sign sign) {
        PersistentDataContainer container = sign.getPersistentDataContainer();
        return container.has(plugin.getNamespacedKey("player_name"), PersistentDataType.STRING);
    }

    // 登録済みの看板がクリックされたときの処理
    private void handleRegisteredSignClick(Sign sign, Player player) {
        PersistentDataContainer container = sign.getPersistentDataContainer();
        String registeredName = container.get(plugin.getNamespacedKey("player_name"), PersistentDataType.STRING);

        if (registeredName != null) {
            // 登録されたプレイヤーと現在のプレイヤーが同じかどうか
            if (registeredName.equals(player.getName())) {
                // 自分の登録した看板の場合
                player.sendMessage(Component.text("この看板はすでにあなたの名前で登録されています").color(NamedTextColor.YELLOW));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.5f);
            } else {
                // 占いとか騎士に飛ばす
                if(player.getInventory().getItemInMainHand() == new OriginalItem().knight) {
                    Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
                    Objects.requireNonNull(scoreboard.getObjective("knight_check")).getScore(registeredName).setScore(
                            Objects.requireNonNull(scoreboard.getObjective("knight_check")).getScore(registeredName).getScore() + 1
                    );
                    player.sendMessage(Component.text("[人狼RPG] ").color(NamedTextColor.RED).append(Component.text("騎士の祈りを使用しました").color(NamedTextColor.WHITE)));
                    player.getInventory().getItemInMainHand().setAmount(0);
                }
                Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
                if(Objects.requireNonNull(scoreboard.getObjective("fortune")).getScore(player).getScore() >= 1) {
                    Team wolfTeam = scoreboard.getTeam("wolf");
                    Team villagerTeam = scoreboard.getTeam("villager");
                    Team vampireTeam = scoreboard.getTeam("vampire");

                    if (Objects.requireNonNull(wolfTeam).hasEntry(registeredName)) {
                        player.sendMessage(Component.text("[人狼RPG] ").color(NamedTextColor.RED).append(Component.text(registeredName + "は人狼です").color(NamedTextColor.AQUA)));
                    } else if (Objects.requireNonNull(vampireTeam).hasEntry(registeredName)) {
                        player.sendMessage(Component.text("[人狼RPG] ").color(NamedTextColor.RED).append(Component.text(registeredName + "は吸血鬼です").color(NamedTextColor.AQUA)));
                    } else if (Objects.requireNonNull(villagerTeam).hasEntry(registeredName)) {
                        player.sendMessage(Component.text("[人狼RPG] ").color(NamedTextColor.RED).append(Component.text(registeredName + "は村人です").color(NamedTextColor.AQUA)));
                    }
                    Objects.requireNonNull(scoreboard.getObjective("fortune")).getScore(player).setScore(Objects.requireNonNull(scoreboard.getObjective("fortune")).getScore(player).getScore() - 1);
                }
            }
        }
    }

    // 看板の登録をリセットするメソッド
    private void resetSign(Sign sign) {
        // 看板のデータをクリア
        PersistentDataContainer container = sign.getPersistentDataContainer();
        container.remove(plugin.getNamespacedKey("player_name"));

        // 看板の表示をリセット
        sign.getSide(Side.FRONT).line(0, Component.text("プレイヤー登録").color(NamedTextColor.GREEN));
        sign.getSide(Side.FRONT).line(1, Component.empty());
        sign.getSide(Side.FRONT).line(2, Component.empty());
        sign.getSide(Side.FRONT).line(3, Component.empty());
    }

    // プレイヤー名を看板に登録するメソッド
    private void registerPlayerToSign(Sign sign, Player player) {
        PersistentDataContainer container = sign.getPersistentDataContainer();

        // プレイヤー名を保存
        container.set(
                plugin.getNamespacedKey("player_name"),
                PersistentDataType.STRING,
                player.getName()
        );
    }

    // 看板の表示を更新するメソッド
    private void updateSignDisplay(Sign sign, Player player) {
        // プレイヤー名を表示
        sign.getSide(Side.FRONT).line(1, Component.text(player.getName()).color(NamedTextColor.YELLOW));

        // 他の行はクリア
        sign.getSide(Side.FRONT).line(2, Component.empty());
        sign.getSide(Side.FRONT).line(3, Component.empty());
    }

    // 看板からプレイヤー名を取得するメソッド
    public String getPlayerNameFromSign(Sign sign) {
        PersistentDataContainer container = sign.getPersistentDataContainer();

        if (container.has(plugin.getNamespacedKey("player_name"), PersistentDataType.STRING)) {
            return container.get(plugin.getNamespacedKey("player_name"), PersistentDataType.STRING);
        }

        return null;
    }

    // 看板からプレイヤーを取得するメソッド
    public Player getPlayerFromSign(Sign sign) {
        String playerName = getPlayerNameFromSign(sign);
        if (playerName != null) {
            return Bukkit.getPlayer(playerName);
        }
        return null;
    }
}
