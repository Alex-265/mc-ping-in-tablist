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
        CommonClass.init();

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (IConfigScreenFactory) (modContainer, screen) -> new ConfigScreen(Component.empty()));
    }
}