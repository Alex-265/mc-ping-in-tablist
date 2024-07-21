package at.alex.pingintablist.config;

import at.alex.pingintablist.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.awt.*;

public class ConfigScreen extends Screen {
    private Screen oldScreen;
    private int entryW = 30;
    private int defHeight = 25;
    private EditBox fontSizeEntry;
    private EditBox offsetXEntry;
    private EditBox offsetYEntry;
    public ConfigScreen(Component pTitle) {
        super(pTitle);
        this.oldScreen = Minecraft.getInstance().screen;
    }

    @Override
    protected void init() {
        super.init();
        this.fontSizeEntry = new EditBox(Minecraft.getInstance().font,width/2 + 50, getPosY(0), entryW, defHeight, Component.literal("Int"));
        this.fontSizeEntry.setResponder((string) -> {
            var number = tryParseIntWithLimits(string,1,99);
            if(number!=null || string.isEmpty()){
                this.fontSizeEntry.setTextColor(14737632);
            } else {
                this.fontSizeEntry.setTextColor(16733525);
            }
        });
        this.addRenderableWidget(new StringWidget(width/2 - 75 - 30, getPosY(0), 150, defHeight, Component.literal("Font Size"), font));
        this.addRenderableWidget(fontSizeEntry);

        this.offsetXEntry = new EditBox(Minecraft.getInstance().font,width/2 + 50, getPosY(1), entryW, defHeight, Component.literal("Int"));
        this.offsetXEntry.setResponder((string) -> {
            var number = tryParseIntWithLimits(string,0,Integer.MAX_VALUE);
            if(number!=null || string.isEmpty()){
                this.offsetXEntry.setTextColor(14737632);
            } else {
                this.offsetXEntry.setTextColor(16733525);
            }
        });
        this.addRenderableWidget(new StringWidget(width/2 - 75 - 30, getPosY(1), 150, defHeight, Component.literal("X Offset"), font));
        this.addRenderableWidget(offsetXEntry);

        this.offsetYEntry = new EditBox(Minecraft.getInstance().font,width/2 + 50, getPosY(2), entryW, defHeight, Component.literal("Int"));
        this.offsetYEntry.setResponder((string) -> {
            var number = tryParseIntWithLimits(string,0,99);
            if(number!=null || string.isEmpty()){
                this.offsetYEntry.setTextColor(14737632);
            } else {
                this.offsetYEntry.setTextColor(16733525);
            }
        });
        this.addRenderableWidget(new StringWidget(width/2 - 75 - 30, getPosY(2), 150, defHeight, Component.literal("Y Offset"), font));
        this.addRenderableWidget(offsetYEntry);
    }
    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().setScreen(oldScreen);
    }
    @Nullable
    private Integer tryParseIntWithLimits(String s, int min, int max) {
        if(s.isBlank())
            return null;
        try {
            int numb = Integer.parseInt(s);

            if (numb > min-1 && numb < max+1)
                return numb;
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private int getPosY(int modifier) {
        return height/3 + (defHeight*modifier) + (10*modifier);
    }
}
