package at.alex.pingintablist.config;


import at.alex.pingintablist.Constants;
import at.alex.pingintablist.platform.Services;
import java.io.*;
import java.util.Properties;

public class Config {
    public static final Properties defaultValues = new Properties();
    public int fontSize;
    public int offsetX;
    public int offsetY;
    public void read() {
        Constants.LOG.info("Loading Config!");
        Properties properties = new Properties(defaultValues);
        try {
            FileReader configReader = new FileReader(Constants.configFilePath);
            properties.load(configReader);
        } catch (FileNotFoundException e) {
            Constants.LOG.info("Generating the config file at: " + Constants.configFilePath);
            save();
            return;
        } catch (IOException e) {
            Constants.LOG.error("[FATAL]: Failed to read config file: " + Constants.configFilePath);
            throw new RuntimeException(e);
        }
        fontSize = parseIntOrDefault(properties.getProperty(DefaultConfig.CONFIG_FONT_SIZE), DefaultConfig.defaultFontSize);
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
        try {
            File config = new File(Constants.configFilePath);
            boolean existed = config.exists();
            File parentDir = config.getParentFile();
            if(parentDir.exists()) {
                parentDir.mkdirs();
            }
            FileWriter configWriter = new FileWriter(config);
            writeInt(configWriter, DefaultConfig.CONFIG_FONT_SIZE, fontSize);
            writeInt(configWriter, DefaultConfig.CONFIG_X_OFFSET, offsetX);
            writeInt(configWriter, DefaultConfig.CONFIG_Y_OFFSET, offsetY);
            configWriter.close();
            if(!existed) {
                Constants.LOG.info("Created config file");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void writeInt(FileWriter configWriter, String name, Integer value) throws IOException {
        configWriter.write(name + '=' + value + '\n');
    }
    static {
        defaultValues.setProperty(DefaultConfig.CONFIG_FONT_SIZE, String.valueOf(DefaultConfig.defaultFontSize));
        defaultValues.setProperty(DefaultConfig.CONFIG_X_OFFSET, String.valueOf(DefaultConfig.defaultOffsetX));
        defaultValues.setProperty(DefaultConfig.CONFIG_Y_OFFSET, String.valueOf(DefaultConfig.defaultOffsetY));
    }
}