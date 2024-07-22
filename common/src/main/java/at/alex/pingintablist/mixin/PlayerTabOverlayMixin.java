package at.alex.pingintablist.mixin;

import at.alex.pingintablist.utils.Colors;
import at.alex.pingintablist.utils.RenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;


@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {
    /**
     * @author PingInTablist mod (Alex_265)
     * @reason Overrides the renderPingIcon method in the PlayerTabOverlay class, to render the ping in text instead of the Icon.
     */
    @Overwrite
    public void renderPingIcon(GuiGraphics guiGraphics, int i, int j, int k, PlayerInfo playerInfo) {
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
        RenderUtils.renderScaledText(guiGraphics, latency + "ms", j + i - 24, k+1, color,0.75f);
    }
}
