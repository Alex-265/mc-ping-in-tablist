package at.alex.pingintablist.mixin;


import at.alex.pingintablist.Constants;
import at.alex.pingintablist.config.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.ModListScreen;
import net.neoforged.neoforge.client.gui.widget.ModListWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ModListScreen.class)
public class ModListConfigButtonMixin {
    // IDK how to register a screen for the Mod list config button, so I modified it
    @Shadow
    private ModListWidget.ModEntry selected;
    @Shadow
    private Button configButton;
    @Inject(at = @At("HEAD"), method = "displayModConfig")
    private void displayConfig(CallbackInfo ci) {
        if(selected.getInfo().getModId().equals(Constants.MOD_ID))
            Minecraft.getInstance().setScreen(new ConfigScreen(Component.empty()));
    }
    @Inject(at=@At("RETURN"), method = "updateCache")
    private void updateCache(CallbackInfo ci) {
        if(selected !=null && Objects.equals(selected.getInfo().getModId(), Constants.MOD_ID)) {
            configButton.active = true;
        }
    }
}
