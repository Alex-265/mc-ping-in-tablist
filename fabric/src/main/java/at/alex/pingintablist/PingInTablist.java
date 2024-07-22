package at.alex.pingintablist;

import at.alex.pingintablist.config.Config;
import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.Nullable;

public class PingInTablist implements ModInitializer {
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }
}
