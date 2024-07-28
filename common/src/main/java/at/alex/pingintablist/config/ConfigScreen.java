package at.alex.pingintablist.config;

import at.alex.pingintablist.CommonClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class ConfigScreen extends Screen {
    private final Screen oldScreen;
    private final int entryW = 30;
    private final int defHeight = 25;
    private EditBox offsetXEntry;
    private EditBox offsetYEntry;
    private final Config config = CommonClass.config;
    public ConfigScreen(Component pTitle) {
        super(pTitle);
        this.oldScreen = Minecraft.getInstance().screen;
    }

    @Override
    protected void init() {
        this.config.read();
        super.init();
        this.offsetXEntry = new EditBox(Minecraft.getInstance().font,width/2 + 50, getPosY(0), entryW, defHeight, Component.literal("Int"));
        this.offsetXEntry.setResponder((string) -> {
            var number = tryParseIntWithLimits(string,Integer.MIN_VALUE,Integer.MAX_VALUE);
            if(number!=null || string.isEmpty()){
                this.offsetXEntry.setTextColor(14737632);
            } else {
                this.offsetXEntry.setTextColor(16733525);
            }
        });
        this.offsetXEntry.setValue(String.valueOf(this.config.offsetX));
        this.addRenderableWidget(new StringWidget(width/2 - 75 - 30, getPosY(0), 150, defHeight, Component.literal("X Offset"), font));
        this.addRenderableWidget(offsetXEntry);

        this.offsetYEntry = new EditBox(Minecraft.getInstance().font,width/2 + 50, getPosY(1), entryW, defHeight, Component.literal("Int"));
        this.offsetYEntry.setResponder((string) -> {
            var number = tryParseIntWithLimits(string,Integer.MIN_VALUE,Integer.MAX_VALUE);
            if(number!=null || string.isEmpty()){
                this.offsetYEntry.setTextColor(14737632);
            } else {
                this.offsetYEntry.setTextColor(16733525);
            }
        });
        this.offsetYEntry.setValue(String.valueOf(this.config.offsetY));
        this.addRenderableWidget(new StringWidget(width/2 - 75 - 30, getPosY(1), 150, defHeight, Component.literal("Y Offset"), font));
        this.addRenderableWidget(offsetYEntry);
        this.addRenderableWidget(new Button.Builder(Component.literal("Save"), (button -> {
            this.onClose();
        })).pos(width/2-75, height-35).size(150,20).build());
    }
    @Override
    public void onClose() {
        super.onClose();
        this.config.offsetX = Integer.parseInt(this.offsetXEntry.getValue());
        this.config.offsetY = Integer.parseInt(this.offsetYEntry.getValue());
        this.config.save();
        Minecraft.getInstance().setScreen(oldScreen);
    }
    @Nullable
    private Integer tryParseIntWithLimits(String s, int min, int max) {
        if(s.isBlank())
            return null;
        try {
            int numb = Integer.parseInt(s);
            if (numb >= min && numb <= max)
                return numb;
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private int getPosY(int row) {
        return height/3 + (defHeight*row) + (10*row);
    }
}
