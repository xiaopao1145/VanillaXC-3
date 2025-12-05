package io.VanillaX.vanillaXC3;

import io.VanillaX.vanillaXC3.command.ReloadCommand; // 确保导入命令类
import io.VanillaX.vanillaXC3.config.PluginConfig;
import io.VanillaX.vanillaXC3.util.XLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class VanillaXC3 extends JavaPlugin {

    private static VanillaXC3 instance;
    private PluginConfig pluginConfig;

    public static VanillaXC3 getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // 初始化日志工具
        XLogger.initialize(this);

        // 注册命令执行器
        ReloadCommand reloadHandler = new ReloadCommand(this);

        if (getCommand("vanillac3") != null) {
            getCommand("vanillac3").setExecutor(reloadHandler);
            getCommand("vanillac3").setTabCompleter(reloadHandler);
        } else {
            XLogger.getInstance().error("无法注册命令 /vanillac3。请检查 plugin.yml 文件中的 commands 配置。");
        }

        // 初始化配置管理类
        this.pluginConfig = new PluginConfig(this);

        // 加载配置。如果失败，禁用插件
        if (!pluginConfig.loadConfig()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        applyConfigSettings();

        // banner
        boolean isDebugMode = pluginConfig.isDebugMode();
        XLogger.getInstance().info("                                                              ");
        XLogger.getInstance().info("&b__     __          _ _ _      &3__  ______  &c _____        ");
        XLogger.getInstance().info("&b\\ \\   / /_ _ _ __ (_) | | __ _&3\\ \\/ / ___| &c|___ /    ");
        XLogger.getInstance().info("&b \\ \\ / / _` | '_ \\| | | |/ _` |&3\\  / |     &c  |_ \\   ");
        XLogger.getInstance().info("&b  \\ V / (_| | | | | | | | (_| |&3/  \\ |___  &c ___) |     ");
        XLogger.getInstance().info("&b   \\_/ \\__,_|_| |_|_|_|_|\\__,_&3/_/\\_\\____| &c|____/   ");
        XLogger.getInstance().info("                                                              ");
        XLogger.getInstance().info("&eVanillaXC 3 - 加载成功！                                      ");
        XLogger.getInstance().info("                                                              ");
        XLogger.getInstance().debug("您开启了debug模式，这是一条测试消息，无需理会~");
        XLogger.getInstance().debug("");
    }

    @Override
    public void onDisable() {
        if (XLogger.isInitialized()) {
            XLogger.getInstance().warn("插件正在禁用...");
        } else {
            getLogger().warning("插件正在禁用...");
        }
        instance = null;
    }

    /**
     * 将配置中的设置应用到插件的运行时组件 (XLogger, etc.)。
     */
    private void applyConfigSettings() {
        boolean isDebugMode = pluginConfig.isDebugMode();
        XLogger.setDebugMode(isDebugMode);

        // 如果插件有其他需要应用的配置项，也应在此处添加。
    }

    /**
     * 重新加载插件配置和相关设置。
     * @return true 如果重新加载成功，否则 false。
     */
    public boolean reloadPlugin() {
        // 重新加载配置文件
        if (!pluginConfig.loadConfig()) {
            return false;
        }

        // 重新应用配置中读取的设置
        applyConfigSettings();

        return true;
    }

    // 提供给外部访问配置的公共方法
    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }
}