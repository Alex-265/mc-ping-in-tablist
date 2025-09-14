package at.alex.pingintablist.mixin;

import at.alex.pingintablist.CommonClass;
import at.alex.pingintablist.utils.Colors;
import at.alex.pingintablist.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.ArrayList;
import java.util.List;


@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {

    @ModifyConstant(method = "render", constant = @Constant(intValue = 13))
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

    /**
     * @author PingInTablist mod (Alex_265)
     * @reason Overrides the renderPingIcon method in the PlayerTabOverlay class, to render the ping in text instead of the Icon.
     */
    @Overwrite
    public void renderPingIcon(GuiGraphics guiGraphics, int width, int posX, int posY, PlayerInfo playerInfo) {
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
        RenderUtils.renderScaledText(guiGraphics, text, width, posX + width - Minecraft.getInstance().font.width(latency + "ms") - Minecraft.getInstance().font.width(" ") + 3, posY, color);
    }

    @Shadow
    private List<PlayerInfo> getPlayerInfos() {
        return new ArrayList<>();
    }

}
