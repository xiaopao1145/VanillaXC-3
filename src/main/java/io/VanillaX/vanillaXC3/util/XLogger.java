package io.VanillaX.vanillaXC3.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin; // 使用 Plugin 接口

/**
 * 带有颜色代码支持的单例日志工具 (XLogger)。
 */
public class XLogger {

    private static XLogger instance;
    private static boolean debugMode = false;
    private static boolean initialized = false;

    // 日志前缀（硬编码且已翻译颜色代码）
    private final String consolePrefix;
    private final Plugin plugin;

    /**
     * 私有构造函数，防止外部实例化。
     */
    private XLogger(Plugin plugin) {
        this.plugin = plugin;

        // 使用你指定的前缀格式: [插件名]
        String hardcodedPrefix = "&8[&b" + plugin.getName() + "&8] ";
        this.consolePrefix = ChatColor.translateAlternateColorCodes('&', hardcodedPrefix);
    }

    // --- 单例方法 ---

    public static synchronized XLogger initialize(Plugin plugin) {
        if (instance == null) {
            instance = new XLogger(plugin);
            initialized = true;
        }
        return instance;
    }

    public static XLogger getInstance() {
        if (instance == null) {
            throw new IllegalStateException("XLogger 尚未初始化! 请确保在插件启用时调用 XLogger.initialize(this)。");
        }
        return instance;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    // --- DEBUG 控制方法 ---

    public static void setDebugMode(boolean mode) {
        debugMode = mode;
    }

    // --- 日志级别方法 ---

    public void info(String message) {
        log("&aINFO &7| ", message);
    }

    public void warn(String message) {
        log("&eWARN &7| ", message);
    }

    public void error(String message) {
        log("&cERR  &7| ", message);
    }

    public void debug(String message) {
        if (debugMode) {
            log("&dDEBUG &7| ", message);
        }
    }

    private void log(String coloredLevel, String message) {
        String formatted = consolePrefix + coloredLevel + message;

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', formatted));
    }

    // --- 格式化重载方法 (使用 String.format) ---

    public void info(String format, Object... args) {
        info(String.format(format, args));
    }

    public void warn(String format, Object... args) {
        warn(String.format(format, args));
    }

    public void error(String format, Object... args) {
        error(String.format(format, args));
    }

    public void debug(String format, Object... args) {
        debug(String.format(format, args));
    }
}