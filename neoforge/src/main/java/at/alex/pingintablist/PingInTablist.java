package at.alex.pingintablist;


import at.alex.pingintablist.config.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

@Mod(Constants.MOD_ID)
public class PingInTablist {

    public PingInTablist(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.
        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> new IConfigScreenFactory() {
            @Override
            public Screen createScreen(ModContainer modContainer, Screen screen) {
                return new ConfigScreen(Component.empty());
            }
        });
    }
}