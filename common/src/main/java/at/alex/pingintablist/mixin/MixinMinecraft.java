package at.alex.pingintablist.mixin;

import at.alex.pingintablist.Constants;
import com.sun.jna.platform.unix.X11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class MixinMinecraft {
    
    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(Component pTitle, CallbackInfo info) {
        if (Minecraft.getInstance().screen != null) {
            Constants.LOG.warn(Minecraft.getInstance().screen.toString());
        }
        Constants.LOG.warn(String.valueOf(pTitle));
    }
    @Inject(at=@At("HEAD"), method = "init()V")
    private void pinit(CallbackInfo info) {
        if (Minecraft.getInstance().screen != null) {
            Constants.LOG.warn(Minecraft.getInstance().screen.toString());
        }
    }
}