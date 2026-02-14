package tsuteto.tofu.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Abstract base screen for TofuFactory machine GUIs.
 * Provides common rendering for the background, energy bar, and progress arrow.
 * Subclasses specify their own texture and override hook methods for custom rendering.
 */
public abstract class TfMachineScreen<T extends TfMachineMenu> extends AbstractContainerScreen<T> {

    /** The GUI background texture for this machine */
    protected final ResourceLocation texture;

    /** Energy bar rendering constants (default positions, overridable) */
    protected int energyBarX = 10;
    protected int energyBarY = 14;
    protected int energyBarWidth = 12;
    protected int energyBarHeight = 58;

    /** Energy bar source in texture atlas */
    protected int energyBarU = 176;
    protected int energyBarV = 0;

    /** Progress arrow rendering constants (default positions, overridable) */
    protected int arrowX = 79;
    protected int arrowY = 34;
    protected int arrowWidth = 24;
    protected int arrowHeight = 17;

    /** Progress arrow source in texture atlas */
    protected int arrowU = 176;
    protected int arrowV = 58;

    protected TfMachineScreen(T menu, Inventory playerInventory, Component title, ResourceLocation texture) {
        super(menu, playerInventory, title);
        this.texture = texture;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        renderExtraTooltips(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        // Draw background
        guiGraphics.blit(texture, x, y, 0, 0, this.imageWidth, this.imageHeight);

        // Draw energy bar
        renderEnergyBar(guiGraphics, x, y);

        // Draw progress arrow
        renderProgressArrow(guiGraphics, x, y);

        // Hook for subclass-specific rendering
        renderExtra(guiGraphics, x, y, partialTick);
    }

    /**
     * Renders the TF energy bar indicator.
     * The bar fills from bottom to top proportional to stored energy.
     */
    protected void renderEnergyBar(GuiGraphics guiGraphics, int x, int y) {
        int scaledEnergy = this.menu.getScaledEnergy(energyBarHeight);
        if (scaledEnergy > 0) {
            guiGraphics.blit(texture,
                    x + energyBarX,
                    y + energyBarY + energyBarHeight - scaledEnergy,
                    energyBarU,
                    energyBarV + energyBarHeight - scaledEnergy,
                    energyBarWidth,
                    scaledEnergy);
        }
    }

    /**
     * Renders the progress arrow indicator.
     * The arrow fills from left to right proportional to processing progress.
     */
    protected void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        int scaledProgress = this.menu.getScaledProgress(arrowWidth);
        if (scaledProgress > 0) {
            guiGraphics.blit(texture,
                    x + arrowX,
                    y + arrowY,
                    arrowU,
                    arrowV,
                    scaledProgress + 1,
                    arrowHeight);
        }
    }

    /**
     * Hook for subclasses to render additional elements on the background layer.
     */
    protected void renderExtra(GuiGraphics guiGraphics, int x, int y, float partialTick) {
        // Override in subclass if needed
    }

    /**
     * Hook for subclasses to render additional tooltips.
     */
    protected void renderExtraTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Override in subclass if needed
    }

    /**
     * Utility: checks if the mouse is hovering over a rectangular region.
     */
    protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        int guiX = this.leftPos + x;
        int guiY = this.topPos + y;
        return mouseX >= guiX && mouseX < guiX + width && mouseY >= guiY && mouseY < guiY + height;
    }
}
