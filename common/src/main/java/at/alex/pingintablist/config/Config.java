package at.alex.pingintablist.config;


import at.alex.pingintablist.Constants;
import at.alex.pingintablist.platform.Services;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.datafixers.kinds.Const;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.core.appender.ConsoleAppender;

import java.io.*;
import java.util.Properties;

public class Config {
    public static final Properties defaultValues = new Properties();
    public int offsetX;
    public int offsetY;
    public void read() {
        Constants.LOG.info("Loading Config!");
        Properties properties = new Properties(defaultValues);
        try {
            FileReader configReader = new FileReader(Constants.configFilePath);
            properties.load(configReader);
            configReader.close();
        } catch (FileNotFoundException e) {
            Constants.LOG.info("Generating the config file at: " + Constants.configFilePath);
            save();
        } catch (IOException e) {
            Constants.LOG.error("[FATAL]: Failed to read config file: " + Constants.configFilePath);
            throw new RuntimeException(e);
        }
        offsetX = parseIntOrDefault(properties.getProperty(DefaultConfig.CONFIG_X_OFFSET), DefaultConfig.defaultOffsetX);
        offsetY = parseIntOrDefault(properties.getProperty(DefaultConfig.CONFIG_Y_OFFSET), DefaultConfig.defaultOffsetY);
    }

    private static int parseIntOrDefault(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void save() {
        Constants.LOG.info("Trying to save config to " + Constants.configFilePath);
        try {
            File config = new File(Constants.configFilePath);
            boolean existed = config.exists();
            File parentDir = config.getParentFile();
            if(parentDir.exists()) {
                parentDir.mkdirs();
            }
            FileWriter configWriter = new FileWriter(config);
            writeInt(configWriter, DefaultConfig.CONFIG_X_OFFSET, this.offsetX, DefaultConfig.defaultOffsetX);
            writeInt(configWriter, DefaultConfig.CONFIG_Y_OFFSET, this.offsetY, DefaultConfig.defaultOffsetY);
            configWriter.close();
            if(!existed) {
                Constants.LOG.info("Created config file");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void writeInt(FileWriter configWriter, String name, Integer value, Integer defaultValue) throws IOException {
        if(value == null) {
            configWriter.write(name + '=' + defaultValue + '\n');
        } else {
            configWriter.write(name + '=' + value + '\n');
        }
    }
    static {
        defaultValues.setProperty(DefaultConfig.CONFIG_X_OFFSET, String.valueOf(DefaultConfig.defaultOffsetX));
        defaultValues.setProperty(DefaultConfig.CONFIG_Y_OFFSET, String.valueOf(DefaultConfig.defaultOffsetY));
    }
}