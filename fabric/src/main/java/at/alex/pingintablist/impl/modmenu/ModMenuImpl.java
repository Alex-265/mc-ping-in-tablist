package at.alex.pingintablist.impl.modmenu;

import at.alex.pingintablist.config.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.network.chat.Component;

@SuppressWarnings("unused")
public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ConfigScreen(Component.empty());
    }
}
