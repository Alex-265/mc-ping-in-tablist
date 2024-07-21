package at.alex.pingintablist;

import at.alex.pingintablist.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "pingintablist";
	public static final String MOD_NAME = "PingInTablist";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	public static final String configFilePath = Services.PLATFORM.getConfigDir() + "/" + MOD_ID + ".toml";

}