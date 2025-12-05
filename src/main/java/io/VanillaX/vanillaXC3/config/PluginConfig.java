package io.VanillaX.vanillaXC3.config;

import io.VanillaX.vanillaXC3.VanillaXC3;
import io.VanillaX.vanillaXC3.util.XLogger;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class PluginConfig {

    private final VanillaXC3 plugin;
    private TomlParseResult config;

    // --- 配置键常量 ---
    private static final String KEY_DEBUG_MODE = "logger.debug-mode";

    public PluginConfig(VanillaXC3 plugin) {
        this.plugin = plugin;
    }

    public boolean loadConfig() {
        // 1. 确保文件存在
        if (!setupConfigFile()) {
            if (XLogger.isInitialized()) {
                XLogger.getInstance().error("无法创建或复制 config.toml 文件！");
            } else {
                plugin.getLogger().severe("无法创建或复制 config.toml 文件！");
            }
            return false;
        }

        // 2. 加载并解析 TOML
        this.config = loadTomlFile();

        if (this.config == null || this.config.hasErrors()) {
            plugin.getLogger().severe("解析 config.toml 文件时发生错误！");
            if (this.config != null) {
                this.config.errors().forEach(error -> plugin.getLogger().severe("TOML 解析错误: " + error.getMessage()));
            }
            return false;
        }

        return true;
    }

    // --- 配置获取方法 ---

    public boolean isDebugMode() {

        Optional<Boolean> debugOptional = Optional.ofNullable(config.getBoolean(KEY_DEBUG_MODE));

        if (debugOptional.isPresent()) {
            return debugOptional.get();
        }

        // 如果值不存在，返回默认值 false
        return false;
    }

    // --- 私有辅助方法 ---

    private boolean setupConfigFile() {
        File configFile = new File(plugin.getDataFolder(), "config.toml");

        if (!configFile.exists()) {
            try {
                if (!plugin.getDataFolder().exists()) {
                    plugin.getDataFolder().mkdirs();
                }
                Files.copy(plugin.getResource("config.toml"), configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                plugin.getLogger().info("已创建默认的 config.toml 文件。");
                return true;
            } catch (Exception e) {
                plugin.getLogger().severe("无法将 config.toml 从 JAR 复制到插件数据文件夹！请检查文件是否存在于 resources 目录。");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private TomlParseResult loadTomlFile() {
        File configFile = new File(plugin.getDataFolder(), "config.toml");
        try {
            return Toml.parse(configFile.toPath());
        } catch (IOException e) {
            plugin.getLogger().severe("无法读取 config.toml 文件!");
            e.printStackTrace();
            return null;
        }
    }
}