package getta.gettaroo.gui;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import getta.gettaroo.Constants;
import getta.gettaroo.config.Cheats;
import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;
import getta.gettaroo.config.Hotkeys;

import java.util.Collections;
import java.util.List;

public class GuiConfig extends GuiConfigsBase {

    public static ConfigGuiTab tab = ConfigGuiTab.TOGGLES;

    public GuiConfig()
    {
        super(10, 50, Constants.MOD_ID, null, "getta.gettaroo.gui.title.configs");
    }

    @Override
    public void initGui()
    {

        if(GuiConfig.tab == ConfigGuiTab.CRAFTING_PANEL) {
            GuiBase.openGui(new CraftingPanelScreen());
            return;
        }
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        for (ConfigGuiTab tab : ConfigGuiTab.values())
        {
            if(!ConfigGuiTab.CRAFTING_PANEL.equals(tab)) {
                x += this.createButton(x, y, -1, tab);
            } else {
                this.createButton(this.width - 95, this.height - 30, -1, tab);
            }
        }
    }

    private int createButton(int x, int y, int width, ConfigGuiTab tab)
    {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(GuiConfig.tab != tab);
        this.addButton(button, new ButtonListener(tab, this));

        return button.getWidth() + 2;
    }

    @Override
    protected int getConfigWidth()
    {
        ConfigGuiTab tab = GuiConfig.tab;

        if (tab == ConfigGuiTab.TOGGLES || tab == ConfigGuiTab.CHEAT_TOGGLES)
        {
            return 120;
        }
        else if (tab == ConfigGuiTab.HOTKEYS || tab == ConfigGuiTab.UTILS || tab == ConfigGuiTab.CHEAT_HOTKEYS)
        {
            return 80;
        }
        else if (tab == ConfigGuiTab.SERVER ||tab == ConfigGuiTab.LISTS)
        {
            return 200;
        } else if(tab == ConfigGuiTab.CRAFTING_PANEL) {

        }

        return super.getConfigWidth();
    }

    @Override
    protected boolean useKeybindSearch()
    {
        return GuiConfig.tab == ConfigGuiTab.TOGGLE_HOTKEYS ||
                GuiConfig.tab == ConfigGuiTab.HOTKEYS;
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs()
    {
        List<? extends IConfigBase> configs;
        ConfigGuiTab tab = GuiConfig.tab;

        if (tab == ConfigGuiTab.LISTS)
        {
            configs = Configs.Lists.OPTIONS;
        }
        else if (tab == ConfigGuiTab.TOGGLES)
        {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, ImmutableList.copyOf(FeatureToggle.values()));
        }
        else if (tab == ConfigGuiTab.TOGGLE_HOTKEYS)
        {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, ImmutableList.copyOf(FeatureToggle.values()));
        }
        else if (tab == ConfigGuiTab.HOTKEYS)
        {
            configs = Hotkeys.HOTKEY_LIST;
        }else if(tab == ConfigGuiTab.UTILS){

            configs = Configs.Utils.OPTIONS;
        }
        else if(tab == ConfigGuiTab.SERVER){
            configs = Configs.Server.OPTIONS;
        }else if(tab == ConfigGuiTab.CHEAT_TOGGLES){

            configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, ImmutableList.copyOf(Cheats.values()));
        }else if(tab == ConfigGuiTab.CHEAT_HOTKEYS){

            configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, ImmutableList.copyOf(Cheats.values()));
        }
        else
        {
            return Collections.emptyList();
        }
        return ConfigOptionWrapper.createFor(configs);
    }

    private static class ButtonListener implements IButtonActionListener{

        private final GuiConfig parent;
        private final ConfigGuiTab tab;

        public ButtonListener(ConfigGuiTab tab, GuiConfig parent)
        {
            this.tab = tab;
            this.parent = parent;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton)
        {
            GuiConfig.tab = this.tab;

            this.parent.reCreateListWidget(); // apply the new config width
            this.parent.getListWidget().resetScrollbarPosition();
            this.parent.initGui();


        }
    }

    public enum ConfigGuiTab{
        TOGGLES("getta.gettaroo.gui.button.config_gui.toggles"),
        TOGGLE_HOTKEYS("getta.gettaroo.gui.button.config_gui.toggle_hotkeys"),
        HOTKEYS("getta.gettaroo.gui.button.config_gui.hotkeys"),
        LISTS("getta.gettaroo.gui.button.config_gui.lists"),
        UTILS("getta.gettaroo.gui.button.config_gui.utils"),
        CHEAT_TOGGLES("getta.gettaroo.gui.button.config_gui.cheat_toggles"),
        CHEAT_HOTKEYS("getta.gettaroo.gui.button.config_gui.cheat_hotkeys"),
        SERVER("getta.gettaroo.gui.button.config_gui.server"),
        CRAFTING_PANEL("getta.gettaroo.gui.button.config_gui.crafting_panel");


        private final String translationKey;

        private ConfigGuiTab(String translationKey)
        {
            this.translationKey = translationKey;
        }

        public String getDisplayName()
        {
            return StringUtils.translate(this.translationKey);
        }
    }


}


