package getta.gettaroo.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiScrollBar;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.gui.widgets.WidgetDropDownList;
import fi.dy.masa.malilib.gui.wrappers.TextFieldWrapper;
import fi.dy.masa.malilib.render.RenderUtils;
import getta.gettaroo.features.CraftingPanelItemOutput;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ItemsWidget extends WidgetBase {

    private final TextFieldWrapper<GuiTextFieldGeneric> searchBar;
    private TextFieldWrapper<GuiTextFieldGeneric> amountSelected;
    protected final GuiScrollBar scrollBar = new GuiScrollBar();
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean shouldFilter;
    private List<Item> itemsWithFilter;
    private List<CraftingPanelItemOutput> selected;
    private Item selectedItem;
    protected int scrollbarWidth = 10;
    private int filteredQuantity = 0;
    private SearchingItem searchingItem;

    public ItemsWidget(int x, int y, int width, int height) {
        super(x, y, width, height);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        TextFieldListener listener = new TextFieldListener(this);

        this.searchBar = new TextFieldWrapper<>(new GuiTextFieldGeneric(this.x + 1, y - 20, (width + x) / 2, 16, this.textRenderer), listener);

        this.amountSelected = new TextFieldWrapper<>(new GuiTextFieldGeneric(this.x + 3 + this.textRenderer.getWidth("Amount:"), (int)(this.height * 1.5f - 4), 50, 16, this.textRenderer), listener);

        this.scrollBar.setMaxValue(22);
        this.scrollBar.setValue(0);

        this.itemsWithFilter = new ArrayList<>();
        this.selected = new ArrayList<>();
    }

    protected void updateFilteredEntries() {

        if(this.searchBar.isFocused()) {

            String textToFilter = searchBar.getTextField().getText();

            if (!textToFilter.isEmpty()) {

                shouldFilter = true;
            } else {

                shouldFilter = false;
            }

            this.scrollBar.setValue(0);

            this.itemsWithFilter = new ArrayList<>();
        }

        if(shouldFilter) {
            for (Item item : Registry.ITEM) {

                if (applySearch(item)) {

                    if(provideCraftFromItem(item) == null || item.equals(Items.GLASS) || item.equals(Items.SPONGE) || item.getDefaultStack().toString().contains("arrow") || item.getDefaultStack().toString().contains("pattern")) {

                        continue;
                    }

                    this.itemsWithFilter.add(item);
                    this.filteredQuantity++;
                }
            }
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {

        if(this.searchBar.getTextField().isMouseOver(mouseX, mouseY)) {

            this.searchBar.getTextField().setFocused(true);
        } else {

            this.searchBar.getTextField().setFocused(false);
        }

        if(this.amountSelected.getTextField().isMouseOver(mouseX, mouseY)) {

            if(this.amountSelected.getTextField().getText().equals("Fill")) {

                this.amountSelected.getTextField().setEditableColor(Color.WHITE.getRGB());
                this.amountSelected.getTextField().setText("");
            }
            this.amountSelected.getTextField().setFocused(true);
        } else {

            this.amountSelected.getTextField().setFocused(false);
        }

        this.scrollBar.setIsDragging(false);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {

        if (this.searchingItem != null) {

            this.searchingItem = null;
            this.selectedItem = null;
        }

        this.searchingItem = new SearchingItem(mouseX, mouseY);

        int scrollBarX = this.x + this.width - this.scrollbarWidth - 1;
        int scrollbarY = this.y + 1;
        int scrollbarHeight = this.height - 1;

        if(this.scrollBar.wasMouseOver()) {

            this.scrollBar.setIsDragging(true);
        }

        return true;
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        if (this.searchBar.getTextField().isFocused()) {

            return this.searchBar.onCharTyped(charIn, modifiers);
        } else if(this.amountSelected.isFocused() && Character.isDigit(charIn)){

            return this.amountSelected.onCharTyped(charIn, modifiers);
        }

        return false;
    }

    @Override
    protected boolean onKeyTypedImpl(int keyCode, int scanCode, int modifiers) {

        if (this.searchBar.getTextField().isFocused()) {


            return this.searchBar.onKeyTyped(keyCode, scanCode, modifiers);
        } else if(this.amountSelected.isFocused()){

            return this.amountSelected.onKeyTyped(keyCode, scanCode, modifiers);
        }

        return false;
    }

    @Override
    public void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        this.scrollBar.setIsDragging(false);
    }

    @Override
    public boolean onMouseScrolledImpl(int mouseX, int mouseY, double mouseWheelDelta) {

        if (this.isMouseOver(mouseX, mouseY)) {

            int amount = mouseWheelDelta < 0 ? 1 : -1;
            this.scrollBar.offsetValue(amount);
        }

        return false;
    }

    //Provided by akali, he's the best
    private List<CraftingPanelItemOutput> provideCraftFromItem(Item item) {

        //ITEM, BLOCK, POTION, ENCHANTMENT, ENTITIES: Minecart, Boat.

        Optional<? extends Recipe<?>> recipe = MinecraftClient.getInstance().player.world.getRecipeManager().get(new Identifier(item.toString()));
        if (!recipe.isPresent()) {

            return null;
        }

        Recipe<?> finalRecipe = recipe.get();
        List<CraftingPanelItemOutput> itemsOfRecipe = getIngredients(finalRecipe.getPreviewInputs());

        return itemsOfRecipe;
    }

    //Provided by akali, he's the best
    private List<CraftingPanelItemOutput> getIngredients(List<Ingredient> ingredients) {
        Map<String, CraftingPanelItemOutput> history = new HashMap<>();

        for(Ingredient ingredient : ingredients) {

            CraftingPanelItemOutput item = itemFromIngredient(ingredient);
            if (item != null) {

                if(history.containsKey(item.getName())) {

                    history.get(item.getName()).incrementCount();
                } else {

                    history.put(item.getName(), item);
                }
            }
        }

        return new ArrayList<CraftingPanelItemOutput>(history.values());
    }

    //Provided by akali, he's the best
    private static CraftingPanelItemOutput itemFromIngredient(Ingredient ingredient) {

        ItemStack[] itemStackOfIngredient = ingredient.getMatchingStacksClient();

        if(itemStackOfIngredient.length == 0) {

            return null;
        }

        ItemStack itemStack = itemStackOfIngredient[0];
        CraftingPanelItemOutput result = new CraftingPanelItemOutput();
        result.setName(getItemStackName(itemStack));
        result.incrementCount();

        return result;
    }

    public List<CraftingPanelItemOutput> convertSelectionsToResults() {

        List<CraftingPanelItemOutput> output = new ArrayList<>();

        for(CraftingPanelItemOutput item : this.selected) {

            List<CraftingPanelItemOutput> listMaterials = provideCraftFromItem(Registry.ITEM.get(new Identifier("minecraft:" + item.getName().replaceAll(" ", "_").toLowerCase())));

            if(listMaterials != null && !listMaterials.isEmpty()) {

                float totalQuantityOfItem = item.getCount();

                CraftingPanelItemOutput result = new CraftingPanelItemOutput();

                result.setCount(totalQuantityOfItem);
                result.setName(item.getName());
                result.setMaterials(new ArrayList<>());

                for(CraftingPanelItemOutput material : listMaterials) {

                    result.addMaterial(material);
                 }

                output.add(result);
            }
        }

        return  output;
    }

    private boolean applySearch(Item item) {

        String filteredText = this.searchBar.getTextField().getText();
        String itemName = item.getName().getString();


        return itemName.toLowerCase().contains(filteredText.toLowerCase());
    }

    private static String getItemStackName(ItemStack item) {

        String[] split = item.toString().toLowerCase().split(" ");
        return split[1];
    }

    public void addToList() {

        if(this.selectedItem != null) {
            String pito = this.selectedItem.getDefaultStack().getTag() != null ? this.selectedItem.getDefaultStack().getTag().asString() : this.selectedItem.getDefaultStack().getName().getString();

            CraftingPanelItemOutput addingItem = new CraftingPanelItemOutput();
            addingItem.setName(getItemStackName(this.selectedItem.getDefaultStack()));
            try {

                if(Integer.parseInt(this.amountSelected.getTextField().getText()) > 0) {

                    for(CraftingPanelItemOutput item : this.selected) {
                        if(item.getName().equals(getItemStackName(this.selectedItem.getDefaultStack()))) {

                            float previousCount = item.getCount();
                            item.setCount(previousCount + Integer.parseInt(this.amountSelected.getTextField().getText()));
                            return;
                        }
                    }

                    addingItem.setCount(Integer.parseInt(this.amountSelected.getTextField().getText()));
                    addingItem.setMaterials(provideCraftFromItem(this.selectedItem));
                    this.selected.add(addingItem);
                }

            } catch (NumberFormatException e) {

                this.amountSelected.getTextField().setEditableColor(Color.RED.getRGB());
                this.amountSelected.getTextField().setText("Fill");
            }
        }
    }

    public void clearSelected() {
        this.selected = new ArrayList<>();
    }

    public void removeLast() {
        try {
            this.selected.remove(this.selected.size() - 1);
        } catch (IndexOutOfBoundsException ignored){}
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, MatrixStack matrixStack) {

        RenderUtils.color(1f, 1f, 1f, 1f);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0, 0, 1);

        Screen screen = this.mc.currentScreen;

        RenderUtils.drawOutlinedBox(this.x, this.y, this.width, this.height, 0xA0000000, GuiBase.COLOR_HORIZONTAL_BAR);
        RenderUtils.drawOutlinedBox(this.width + 15, this.y, screen.width / 6, this.height + 50, 0xA0000000, GuiBase.COLOR_HORIZONTAL_BAR);

        if(this.selected != null && !this.selected.isEmpty()) {

            int xLayers = 0;
            int yLayers = 0;

            for(CraftingPanelItemOutput item : this.selected) {

                String identifier = "minecraft:" + item.getName().replaceAll(" ", "_").toLowerCase();
                ItemStack itemStack = Registry.ITEM.get(new Identifier(identifier)).getDefaultStack();
                int amount = (int)item.getCount();

                this.mc.getItemRenderer().renderInGui(itemStack, this.width + 17 + xLayers, this.y + 7 + yLayers);
                this.textRenderer.drawWithShadow(matrixStack, "x" + amount,this.width + 35 + xLayers, this.y + 12 + yLayers, Color.WHITE.getRGB());
                yLayers += 20;

                if(yLayers + 20 >= this.height + 50) {

                    if(xLayers - 50 >= this.width / 6) {

                        break;
                    }

                    yLayers = 0;
                    xLayers += 40;
                }
            }
        }

        int xAmount = 0;
        int yAmount = 0;
        int layer = this.scrollBar.getValue();

        int helper = 0;

        List<Item> items = new ArrayList<>();

        if(shouldFilter) {

            items = this.itemsWithFilter;

            if(222 >= this.filteredQuantity) {
                this.scrollBar.setMaxValue(0);
            } else {
                this.scrollBar.setMaxValue(this.filteredQuantity / 17);
            }

        } else {

            int amount = 0;

            for(Item item : Registry.ITEM) {

                if(provideCraftFromItem(item) == null || item.equals(Items.GLASS) || item.equals(Items.SPONGE) || item.getDefaultStack().toString().contains("arrow") || item.getDefaultStack().toString().contains("pattern")) {

                    continue;
                }

                amount++;
                items.add(item);
            }

            this.scrollBar.setMaxValue(22);
        }

        for(Item item : items) {

            if(layer > 0 || helper > 0) {
                if(helper == 0) {
                    helper = 17;
                    if(layer != 0) {
                        layer--;
                    }
                }
                if(helper != 0) {
                    helper--;
                }
                continue;
            }
            xAmount += 18;

            this.mc.getItemRenderer().renderInGui(item.getDefaultStack(), this.x - 16 + xAmount, this.y + 2 + yAmount);

            if(this.searchingItem != null && this.selectedItem == null) {

                if(this.x - 16 + xAmount <= this.searchingItem.getMouseX() && this.x - 16 + xAmount + 18 >= this.searchingItem.getMouseX() && this.y + 2 + yAmount <= this.searchingItem.getMouseY() && this.y + 2 + yAmount + 18 >= this.searchingItem.getMouseY()) {

                    this.selectedItem = item;
                }
            }

            try {

                if (item.equals(this.selectedItem)) {

                    RenderUtils.drawOutline(this.x - 14 + xAmount - 4, this.y + 2 + yAmount - 2, 20, 20, Color.WHITE.getRGB());
                }
            } catch (NullPointerException ignored) {}

            if (xAmount - scrollbarWidth + 25 >= this.width) {

                xAmount = 0;
                yAmount += 20;
            }

            if(yAmount + 18 >= this.height) {

                break;
            }

        }

        int scrollWidth = 10;
        this.searchBar.draw(mouseX, mouseY, matrixStack);
        this.amountSelected.draw(mouseX, mouseY, matrixStack);
        this.textRenderer.drawWithShadow(matrixStack, "Amount:", this.x + 1, this.height * 1.5f, Color.WHITE.getRGB());

        int x = this.x + this.width - this.scrollbarWidth - 1;
        int y = this.y + 1;
        this.scrollBar.render(mouseX, mouseY, 0, x, y, this.scrollbarWidth, this.height -1, this.height * 4);

        RenderSystem.popMatrix();
    }

    protected static class TextFieldListener implements ITextFieldListener<GuiTextFieldGeneric> {

        protected final ItemsWidget widget;

        protected TextFieldListener(ItemsWidget widget)
        {
            this.widget = widget;
        }

        @Override
        public boolean onTextChange(GuiTextFieldGeneric textField) {

            this.widget.updateFilteredEntries();
            return true;
        }
    }

    public List<CraftingPanelItemOutput> getSelected() {
        return this.selected;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private static class SearchingItem {

        private int mouseX;
        private int mouseY;

        public SearchingItem(int mouseX, int mouseY) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        public int getMouseX() {
            return mouseX;
        }

        public int getMouseY() {
            return mouseY;
        }


    }
}
