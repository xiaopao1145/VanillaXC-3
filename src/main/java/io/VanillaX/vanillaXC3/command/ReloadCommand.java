package io.VanillaX.vanillaXC3.command;

import io.VanillaX.vanillaXC3.VanillaXC3;
import io.VanillaX.vanillaXC3.util.XLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 负责处理 /vanillac3 命令的执行和 Tab 补全功能。
 * 实现了 CommandExecutor 和 TabCompleter 接口。
 */
public class ReloadCommand implements CommandExecutor, TabCompleter {

    private final VanillaXC3 plugin;
    private final XLogger logger;

    // 所有有效的子命令列表
    private static final List<String> COMMANDS = Arrays.asList("reload");

    public ReloadCommand(VanillaXC3 plugin) {
        this.plugin = plugin;
        this.logger = XLogger.getInstance();
    }

    // --- 命令执行部分 (CommandExecutor) ---

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // 1. 权限检查
        if (!sender.hasPermission("vanillac3.reload")) {
            sender.sendMessage(ChatColor.RED + "你没有执行此命令的权限。");
            return true;
        }

        // 2. 参数处理
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            logger.warn("正在重新加载插件配置...");

            // 调用主插件的重载方法
            if (plugin.reloadPlugin()) {
                logger.info("插件配置已成功重新加载。");
                sender.sendMessage(ChatColor.GREEN + "插件配置已成功重新加载！");
            } else {
                logger.error("插件配置重新加载失败，请检查控制台日志！");
                sender.sendMessage(ChatColor.RED + "插件配置重新加载失败，请检查控制台日志！");
            }
            return true;
        }

        // 3. 用法提示
        sender.sendMessage(ChatColor.YELLOW + "用法: /" + label + " reload");
        return true;
    }

    // --- Tab 补全部分 (TabCompleter) ---

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        // 1. 检查权限
        if (!sender.hasPermission("vanillac3.reload")) {
            return completions; // 没有权限，不提供补全
        }

        if (args.length == 1) {
            // 2. 补全第一个参数 (子命令)
            // 使用 StringUtil.copyPartialMatches 进行模糊匹配
            StringUtil.copyPartialMatches(args[0], COMMANDS, completions);
        }

        // 3. 如果是第二个参数或更多，不提供补全，因为 reload 没有更多参数

        return completions;
    }
}