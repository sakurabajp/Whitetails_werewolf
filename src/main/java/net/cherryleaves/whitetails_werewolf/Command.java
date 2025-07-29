package net.cherryleaves.whitetails_werewolf;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Command {
    public String MyCommand = "whitetails_werewolf";
    public void AlliRegister() {
        registerSkeletonCommand();
        registerVillagerCommand();
        registerChangeTimeCommand();
        registerStartCommand();
    }
    private void registerSkeletonCommand() {
        org.bukkit.command.Command Command = new org.bukkit.command.Command("skeleton-spawn") {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
                if (!(sender instanceof Player) || !sender.isOp()) {
                    sender.sendMessage(NamedTextColor.RED + "You don't have permission to use this command.");
                    return true;
                }
                new SetArmorStand().getItem((Player) sender);
                return true;
            }
        };
        Command.setDescription("set skeleton spawn armor stand");
        new Whitetails_werewolf().getServer().getCommandMap().register(MyCommand, Command);
    }
    private void registerVillagerCommand() {
        org.bukkit.command.Command Command = new org.bukkit.command.Command("villager-spawn") {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
                if (!(sender instanceof Player) || !sender.isOp()) {
                    sender.sendMessage(NamedTextColor.RED + "You don't have permission to use this command.");
                    return true;
                }
                new setVillager().getItem((Player) sender);
                return true;
            }
        };
        Command.setDescription("villager spawn");
        new Whitetails_werewolf().getServer().getCommandMap().register(MyCommand, Command);
    }
    private void registerChangeTimeCommand() {
        org.bukkit.command.Command Command = new org.bukkit.command.Command("game-time") {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
                if (!(sender instanceof Player) || !sender.isOp()) {
                    sender.sendMessage(NamedTextColor.RED + "You don't have permission to use this command.");
                    return true;
                }
                if (args.length != 1) {
                    sender.sendMessage(NamedTextColor.RED + "Usage: /game-time <day|night>");
                    return true;
                }
                if (args[0].equalsIgnoreCase("day")) {
                    new System().changeDay();
                } 
                else if (args[0].equalsIgnoreCase("night")) {
                    new System().changeNight();
                } 
                else {
                    sender.sendMessage(NamedTextColor.RED + "Usage: /time <day|night>");
                }
                return true;
            }
        };
        Command.setDescription("change world time");
        new Whitetails_werewolf().getServer().getCommandMap().register(MyCommand, Command);
    }
    private void registerStartCommand() {
        org.bukkit.command.Command Command = new org.bukkit.command.Command("start") {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
                if (!(sender instanceof Player p) || !sender.isOp()) {
                    sender.sendMessage(NamedTextColor.RED + "You don't have permission to use this command.");
                    return true;
                }
                else {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 0.7f, 1.0f);
                    new GUI().openGUI(p);
                    return true;
                }
            }
        };
        Command.setDescription("game start");
        new Whitetails_werewolf().getServer().getCommandMap().register(MyCommand, Command);
    }
}
