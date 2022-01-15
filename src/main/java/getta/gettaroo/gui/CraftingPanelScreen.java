package getta.gettaroo.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import getta.gettaroo.gui.widget.ItemsWidget;
import getta.gettaroo.gui.widget.ResultsCraftedWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class CraftingPanelScreen extends GuiBase {

    protected final ItemsWidget itemsWidget;
    protected final ResultsCraftedWidget resultsCraftedWidget;
    private Screen screen;

    public CraftingPanelScreen() {
        super();

        this.title = StringUtils.translate("gettaroo.gui.title.crafting_panel");

        this.screen = MinecraftClient.getInstance().currentScreen;

        this.itemsWidget = new ItemsWidget(10, (int)(screen.height / 4.2), (int)(screen.width - screen.width / 1.5), (int)(screen.height / 1.953));
        this.resultsCraftedWidget = new ResultsCraftedWidget((int)(screen.width / 1.6), (int)(screen.height / 4.2), ((screen.width - 10) - (int)(screen.width / 1.6)), (int)(screen.height / 1.46));
    }

    @Override
    public void initGui() {

        GuiConfig.tab = GuiConfig.ConfigGuiTab.CRAFTING_PANEL;

        super.initGui();

        this.clearWidgets();
        this.clearButtons();
        this.createButtons();
    }

    protected void createButtons() {

        super.initGui();

        int x = 10;
        int y = 26;
        int rows = 1;

        for (GuiConfig.ConfigGuiTab tab : GuiConfig.ConfigGuiTab.values()) {

            int width = this.getStringWidth(tab.getDisplayName()) + 10;

            if (x >= this.width - width - 10) {

                x = 10;
                y += 22;
                ++rows;
            }

            if(!GuiConfig.ConfigGuiTab.CRAFTING_PANEL.equals(tab)) {
                x += this.createTabButton(x, y, -1, tab);
            } else {
                this.createTabButton(this.width - 95, this.height - 30, -1, tab);
            }
        }

        //Widgets instance
        String name = StringUtils.translate("gettaroo.gui.button.get_materials");
        ButtonGeneric calculateMaterialsButton = new ButtonGeneric(120, this.screen.height - 30, -1, true, name);
        ButtonGeneric clearResults = new ButtonGeneric(this.resultsCraftedWidget.getX() + this.textRenderer.getWidth("Clear Result###|"), this.height - 30, -1, true, "Clear Results");
        ButtonGeneric addToList = new ButtonGeneric(this.itemsWidget.getX() + 3 + this.textRenderer.getWidth("Amount:") * 5,  (int)(this.itemsWidget.getHeight() * 1.5f - 6), -1, true, "Add To Selected");
        ButtonGeneric removeLastSelected = new ButtonGeneric((int)(this.screen.width / 2.9), (int)(this.itemsWidget.getHeight() * 1.5f - 6) + 22, -1, true, "Remove Last");
        ButtonGeneric clearSelected = new ButtonGeneric((int)(this.screen.width / 2.9), (int)(this.itemsWidget.getHeight() * 1.5f - 6) + 1, -1, true, "Clear Selected");
        ButtonGeneric changeTypeButton = new ButtonGeneric(this.resultsCraftedWidget.getX() + this.textRenderer.getWidth("Clear Result#############|@#~€###|") + 3, this.height - 30, -1, true, "Change Output Type");

        this.addWidget(this.itemsWidget);
        this.addWidget(this.resultsCraftedWidget);

        this.addButton(calculateMaterialsButton, (btn, mbtn) -> {
            this.resultsCraftedWidget.recieveResults(this.itemsWidget.convertSelectionsToResults());
        });

        this.addButton(clearResults, (btn, mbtn) -> {
            this.resultsCraftedWidget.clearResults();
        });

        this.addButton(addToList, (btn, mbtn) -> {
           this.itemsWidget.addToList();
        });

        this.addButton(removeLastSelected, (btn, mbtn) -> {
           this.itemsWidget.removeLast();
        });

        this.addButton(clearSelected, (btn, mbtn) -> {
            this.itemsWidget.clearSelected();
        });

        this.addButton(changeTypeButton, (btn, mbtn) -> {
           this.resultsCraftedWidget.cycle();
        });
    }

    protected int createTabButton(int x, int y, int width, GuiConfig.ConfigGuiTab tab) {

        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(GuiConfig.tab != tab);
        this.addButton(button, new ButtonListenerTab(tab));

        return button.getWidth() + 2;
    }

    public static class ButtonListenerTab implements IButtonActionListener {

        private final GuiConfig.ConfigGuiTab tab;

        public ButtonListenerTab(GuiConfig.ConfigGuiTab tab) {
            this.tab = tab;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            GuiConfig.tab = this.tab;
            GuiBase.openGui(new GuiConfig());
        }
    }
}
