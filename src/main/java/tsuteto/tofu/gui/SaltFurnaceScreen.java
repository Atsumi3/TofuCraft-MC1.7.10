package tsuteto.tofu.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tsuteto.tofu.TofuCraftMod;

/**
 * GUI screen for the Salt Furnace.
 * Renders the background texture along with animated burn flame,
 * cook progress arrow, and nigari tank fill level.
 */
public class SaltFurnaceScreen extends AbstractContainerScreen<SaltFurnaceMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "textures/gui/salt_furnace.png");

    /** Tank rendering constants (pixel coordinates in the GUI) */
    private static final int TANK_X = 148;
    private static final int TANK_Y = 14;
    private static final int TANK_WIDTH = 12;
    private static final int TANK_HEIGHT = 58;

    /** Flame indicator source in the texture atlas (u, v) */
    private static final int FLAME_U = 176;
    private static final int FLAME_V = 0;
    private static final int FLAME_WIDTH = 14;
    private static final int FLAME_HEIGHT = 14;

    /** Arrow indicator source in the texture atlas (u, v) */
    private static final int ARROW_U = 176;
    private static final int ARROW_V = 14;
    private static final int ARROW_WIDTH = 24;
    private static final int ARROW_HEIGHT = 17;

    public SaltFurnaceScreen(SaltFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        // Center the title label
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        // Draw background
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        // Draw burn flame indicator
        if (this.menu.isBurning()) {
            int flamePx = this.menu.getScaledBurnTime();
            guiGraphics.blit(TEXTURE,
                    x + 56, y + 36 + 12 - flamePx,
                    FLAME_U, FLAME_V + 12 - flamePx,
                    FLAME_WIDTH, flamePx + 1);
        }

        // Draw cook progress arrow
        int arrowPx = this.menu.getScaledCookProgress();
        if (arrowPx > 0) {
            guiGraphics.blit(TEXTURE,
                    x + 79, y + 34,
                    ARROW_U, ARROW_V,
                    arrowPx + 1, ARROW_HEIGHT);
        }

        // Draw nigari tank fill level
        renderNigariTank(guiGraphics, x, y);
    }

    /**
     * Renders the nigari fluid tank indicator on the right side of the GUI.
     * The fill level is based on the block entity's stored nigari amount.
     */
    private void renderNigariTank(GuiGraphics guiGraphics, int x, int y) {
        if (this.menu.getBlockEntity() != null) {
            int nigariAmount = this.menu.getBlockEntity().getNigariAmount();
            int maxNigari = this.menu.getBlockEntity().getMaxNigariAmount();

            if (maxNigari > 0 && nigariAmount > 0) {
                int fillHeight = (int) ((float) nigariAmount / maxNigari * TANK_HEIGHT);
                if (fillHeight > TANK_HEIGHT) {
                    fillHeight = TANK_HEIGHT;
                }

                // Draw tank fill from the texture atlas (nigari fluid sprite area)
                guiGraphics.blit(TEXTURE,
                        x + TANK_X, y + TANK_Y + TANK_HEIGHT - fillHeight,
                        FLAME_U + FLAME_WIDTH, TANK_HEIGHT - fillHeight,
                        TANK_WIDTH, fillHeight);
            }
        }
    }
}
