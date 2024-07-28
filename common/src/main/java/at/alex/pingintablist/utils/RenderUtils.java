package at.alex.pingintablist.utils;

import at.alex.pingintablist.CommonClass;
import at.alex.pingintablist.Constants;
import at.alex.pingintablist.config.Config;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class RenderUtils {
    public static void renderScaledText(GuiGraphics guiGraphics, String text, int width, int x, int y, int color) {
        Font font = Minecraft.getInstance().font;
        Config config = CommonClass.config;
        int stringW = Minecraft.getInstance().font.width(text);
        int posX = width+x-stringW+config.offsetX;
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        guiGraphics.drawString(font, text, posX, y+config.offsetY,color);
        poseStack.popPose();
    }
}
