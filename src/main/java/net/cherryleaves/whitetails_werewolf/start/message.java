package net.cherryleaves.whitetails_werewolf.start;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

public class message {
    public void println(){
        Component mainText = Component.text("[人狼RPG]　").color(NamedTextColor.GOLD);
        Component mainLine = Component.text("——————————————————————————————————————————————————————————————————————————————————————————————").color(NamedTextColor.AQUA);

        //警告書き
        Bukkit.getServer().getConsoleSender().sendMessage(mainLine);
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("").color(NamedTextColor.RED)));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("このプラグインは、ワイテルズ様の『マインクラフト人狼RPG』を再現したプラグインになります").color(NamedTextColor.RED)));
        String url1 = "https://www.youtube.com/playlist?list=PL5aADROd9wP_ivTTE5bG3XlCrEyWJZz1o";
        Component YTLink = Component.text(url1)
                .color(NamedTextColor.DARK_GREEN)
                .clickEvent(ClickEvent.openUrl(url1));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(YTLink));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("").color(NamedTextColor.RED)));
        Bukkit.getServer().getConsoleSender().sendMessage(mainLine);

        Bukkit.getServer().getConsoleSender().sendMessage(mainLine);
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("プラグインがロードされました　").color(NamedTextColor.YELLOW)));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("プラグインの更新状況は、以下のリンクをご覧下さい").color(NamedTextColor.YELLOW)));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("　　　　　　　　　　　　　　　")));
        String url = "https://github.com/sakurabajp/null/releases";
        Component clickableLink = Component.text(url)
                .color(NamedTextColor.DARK_GREEN)
                .clickEvent(ClickEvent.openUrl(url));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(clickableLink));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("　　　　　　　　　　　　　　　")));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("推奨ver -> 1.21.8　　　　　").color(NamedTextColor.YELLOW)));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("現在のサーバーのバージョン ->　").color(NamedTextColor.YELLOW).append(Component.text(Bukkit.getVersion()))));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("　　　　　　　　　　　　　　　")));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("※　このプラグインは個人開発です").color(NamedTextColor.RED)));
        Bukkit.getServer().getConsoleSender().sendMessage(mainText.append(Component.text("※　バグや仕様変更を見かけた際も、同じく上のリンクからご報告下さい").color(NamedTextColor.RED)));
        Bukkit.getServer().getConsoleSender().sendMessage(mainLine);
    }
}
