package getta.gettaroo.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.render.RenderUtils;
import getta.gettaroo.features.CraftingPanelItemOutput;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultsCraftedWidget extends WidgetBase {

    @NotNull List<CraftingPanelItemOutput> results;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean showResults;
    protected String[] outputTypes = {"Items", "Chests", "Shulkers"};
    protected int cycle = 0;

    public ResultsCraftedWidget(int x, int y, int width, int height) {
        super(x, y, width, height);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.results = null;
        this.showResults = false;
    }

    private static String getItemStackName(ItemStack item) {

        String[] split = item.toString().toLowerCase().split(" ");
        return split[1];
    }

    public void cycle() {
        if(cycle == this.outputTypes.length - 1) {
            cycle = 0;
        } else {
            cycle++;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, MatrixStack matrixStack) {

        RenderUtils.color(1f, 1f, 1f, 1f);

        RenderUtils.drawOutlinedBox(this.x, this.y, this.width, this.height + 4, 0xA0000000, GuiBase.COLOR_HORIZONTAL_BAR);

        this.textRenderer.drawWithShadow(matrixStack, Text.of("Results: " + this.outputTypes[cycle]), this.x + 4, this.y + 5, Color.WHITE.getRGB());

        if(showResults && !this.results.isEmpty()) {

            int xQuantity = 0;
            int yQuantity = 0;

            List<CraftingPanelItemOutput> items = new ArrayList<>();

            for(CraftingPanelItemOutput item : this.results) {

                assert item.getMaterials() != null;
                for(CraftingPanelItemOutput recipe : item.getMaterials()) {

                    boolean repeated = false;

                    for(CraftingPanelItemOutput list : items) {
                        if(list.getName().equals(recipe.getName())) {

                            items.get(items.indexOf(list)).setCount(items.get(items.indexOf(list)).getCount() + item.getCount());
                            repeated = true;
                        }
                    }

                    if(!repeated) {

                        CraftingPanelItemOutput output = new CraftingPanelItemOutput();

                        output.setName(recipe.getName());
                        output.setCount(recipe.getCount() * item.getCount());
                        items.add(output);
                    }
                }
            }

            float biggest = 0;

            for(CraftingPanelItemOutput item : items) {

                if(this.cycle == 1) {
                    item.setCount(item.getCount() / 3456);
                } else if(this.cycle == 2) {
                    item.setCount(item.getCount() / 1728);
                }

                if(this.cycle ==1 || this.cycle == 2) {
                    biggest = 222;
                } else if(item.getCount() > biggest) {

                    biggest = item.getCount();
                }

                String identifier = "minecraft:" + getItemStackName(Registry.ITEM.get(new Identifier("minecraft:" + item.getName().replaceAll(" ", "_").toLowerCase())).getDefaultStack()).toLowerCase();
                ItemStack itemStack = Registry.ITEM.get(new Identifier(identifier)).getDefaultStack();
                String amount = String.valueOf(item.getCount());

                if(amount.contains(".0")) {
                    amount = String.valueOf((int)item.getCount());
                } else {

                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    amount = df.format(Float.parseFloat(amount));
                }

                this.mc.getItemRenderer().renderInGui(itemStack, this.x + 7 + xQuantity, this.y + 15 + yQuantity);
                this.textRenderer.drawWithShadow(matrixStack, "x" + amount, this.x + 30 + xQuantity, this.y + 20 + yQuantity, Color.WHITE.getRGB());

                yQuantity += 20;

                if (yQuantity + 20 >= this.height) {

                    yQuantity = 0;
                    xQuantity += 22 + this.textRenderer.getWidth("x " + String.valueOf(biggest));
                }
            }
        }
    }

    public void clearResults() {

        this.results = null;
        this.showResults = false;
    }

    public void recieveResults(@NotNull List<CraftingPanelItemOutput> results) {

        this.results = results;
        this.showResults = true;
    }




}
