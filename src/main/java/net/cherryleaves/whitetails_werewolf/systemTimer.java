package net.cherryleaves.whitetails_werewolf;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class systemTimer {
    private BukkitRunnable timerTask;
    int currentTime = 0;
    private boolean isRunning = false;
    private final JavaPlugin plugin;
    private BossBar bossBar;
    private static final int TIME_LIMIT = 120; // 2 minutes in seconds
    System system = System.getInstance(); // シングルトンから取得
    boolean n = system.Night;

    // シングルトンインスタンス
    private static systemTimer instance;

    public systemTimer(JavaPlugin plugin) {
        this.plugin = plugin;
        // コンストラクタでインスタンスを保存
        instance = this;
        setupBossBar();
    }
    public static systemTimer getInstance() {
        return instance;
    }

    public void startTimer() {
        // タイマーが既に動いている場合は処理を終了
        if (isRunning) return;

        isRunning = true;

        // 全プレイヤーをBossBarに追加
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }

        // タイマータスクの作成
        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                currentTime++; // 1秒ごとにカウントアップ
                updateBossBar();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(currentTime + "秒" + "夜 : " + System.getInstance().Night);
                }
                if (currentTime >= 120) {
                    currentTime = 0;
                    if (System.getInstance().Night) {
                        system.changeDay();
                    } else {
                        system.changeNight();
                    }
                }
            }
        };
        timerTask.runTaskTimer(plugin, 20L, 20L); // 初回実行までの遅延:20ティック, 実行間隔:20ティック
    }

    public void stopTimer() {
        // タイマータスクが存在する場合は停止処理を実行
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        isRunning = false;
    }

    public void resetTimer() {
        stopTimer();
        currentTime = 0;
    }

    // setupBossBar();

    public void setupBossBar() {
        bossBar = Bukkit.createBossBar("ゲーム開始前", BarColor.YELLOW, BarStyle.SEGMENTED_6);
        bossBar.setVisible(true);

        // 現在オンラインの全プレイヤーを追加
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
    }

    private void updateBossBar() {
        double progress = 1.0 - ((double) currentTime / TIME_LIMIT);
        progress = Math.max(0.0, Math.min(1.0, progress));
        bossBar.setProgress(progress);
        if(System.getInstance().Night) {
            bossBar.setTitle("夜");
            bossBar.setColor(BarColor.PURPLE);
            bossBar.setVisible(true);
        }
        else {
            bossBar.setTitle("昼");
            bossBar.setColor(BarColor.YELLOW);
            bossBar.setVisible(true);
        }
    }
    public BossBar getBossBar() {
        return bossBar;
    }
}
