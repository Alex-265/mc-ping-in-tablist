package at.alex.pingintablist.mixin;

import at.alex.pingintablist.CommonClass;
import at.alex.pingintablist.utils.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;


@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {
    @Final
    @Shadow
    private Minecraft minecraft;

    @ModifyConstant(method = "extractRenderState", constant = @Constant(intValue = 13))
    private int modifySpace(int o) {
        return getMaxFontSize();
    }

    @Unique
    public int getMaxFontSize() {
        int max = getPlayerInfos()
                .stream()
                .map(PlayerInfo::getLatency)
                .mapToInt(v -> v)
                .map(x -> x == 0 ? 999 : x)
                .max().orElse(0); // ??? = 3

        return Minecraft.getInstance().font.width(" " + (max == 0 ? "???" : max) + "ms") + CommonClass.config.offsetX;
    }

    @Inject(method = "extractPingIcon", at = @At("HEAD"), cancellable = true)
    public void renderPingIcon(GuiGraphicsExtractor guiGraphics, int width, int posX, int posY, PlayerInfo playerInfo, CallbackInfo ci) {
        String latency = String.valueOf(playerInfo.getLatency());

        int color = Colors.GRAY;
        if (playerInfo.getLatency() <= 0) {
            latency = "???";
        } else if (playerInfo.getLatency() < 150) {
            color = Colors.GREEN;
        } else if (playerInfo.getLatency() < 300) {
            color = Colors.ORANGE;
        } else if (playerInfo.getLatency() < 600) {
            color = Colors.RED;
        } else if (playerInfo.getLatency() < 1000) {
            color = Colors.DARK_RED;
        } else {
            color = Colors.BLACK;
        }

        String text = latency + "ms";
        int textWidth = minecraft.font.width(text);

        guiGraphics.text(minecraft.font, text, posX + width - textWidth, posY, color, true);
        ci.cancel();
    }

    @Shadow
    private List<PlayerInfo> getPlayerInfos() {
        return new ArrayList<>();
    }

}
