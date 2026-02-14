package tsuteto.tofu.gui

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import tsuteto.tofu.TofuCraftMod

/**
 * GUI screen for the Salt Furnace.
 * Renders the background texture along with animated burn flame,
 * cook progress arrow, and nigari tank fill level.
 */
class SaltFurnaceScreen(
    menu: SaltFurnaceMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<SaltFurnaceMenu>(menu, playerInventory, title) {

    companion object {
        private val TEXTURE: ResourceLocation =
            ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "textures/gui/salt_furnace.png")

        /** Tank rendering constants (pixel coordinates in the GUI) */
        private const val TANK_X = 148
        private const val TANK_Y = 14
        private const val TANK_WIDTH = 12
        private const val TANK_HEIGHT = 58

        /** Flame indicator source in the texture atlas (u, v) */
        private const val FLAME_U = 176
        private const val FLAME_V = 0
        private const val FLAME_WIDTH = 14
        private const val FLAME_HEIGHT = 14

        /** Arrow indicator source in the texture atlas (u, v) */
        private const val ARROW_U = 176
        private const val ARROW_V = 14
        private const val ARROW_WIDTH = 24
        private const val ARROW_HEIGHT = 17
    }

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun init() {
        super.init()
        // Center the title label
        titleLabelX = (imageWidth - font.width(title)) / 2
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        renderTooltip(guiGraphics, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val x = leftPos
        val y = topPos

        // Draw background
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight)

        // Draw burn flame indicator
        if (menu.isBurning) {
            val flamePx = menu.scaledBurnTime
            guiGraphics.blit(
                TEXTURE,
                x + 56, y + 36 + 12 - flamePx,
                FLAME_U, FLAME_V + 12 - flamePx,
                FLAME_WIDTH, flamePx + 1
            )
        }

        // Draw cook progress arrow
        val arrowPx = menu.scaledCookProgress
        if (arrowPx > 0) {
            guiGraphics.blit(
                TEXTURE,
                x + 79, y + 34,
                ARROW_U, ARROW_V,
                arrowPx + 1, ARROW_HEIGHT
            )
        }

        // Draw nigari tank fill level
        renderNigariTank(guiGraphics, x, y)
    }

    /**
     * Renders the nigari fluid tank indicator on the right side of the GUI.
     * The fill level is based on the block entity's stored nigari amount.
     */
    private fun renderNigariTank(guiGraphics: GuiGraphics, x: Int, y: Int) {
        val be = menu.blockEntity ?: return
        val nigariAmount = be.nigariAmount
        val maxNigari = be.maxNigariAmount

        if (maxNigari > 0 && nigariAmount > 0) {
            var fillHeight = (nigariAmount.toFloat() / maxNigari * TANK_HEIGHT).toInt()
            if (fillHeight > TANK_HEIGHT) {
                fillHeight = TANK_HEIGHT
            }

            // Draw tank fill from the texture atlas (nigari fluid sprite area)
            guiGraphics.blit(
                TEXTURE,
                x + TANK_X, y + TANK_Y + TANK_HEIGHT - fillHeight,
                FLAME_U + FLAME_WIDTH, TANK_HEIGHT - fillHeight,
                TANK_WIDTH, fillHeight
            )
        }
    }
}
