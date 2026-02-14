package tsuteto.tofu.gui

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

/**
 * Abstract base screen for TofuFactory machine GUIs.
 * Provides common rendering for the background, energy bar, and progress arrow.
 * Subclasses specify their own texture and override hook methods for custom rendering.
 */
abstract class TfMachineScreen<T : TfMachineMenu>(
    menu: T,
    playerInventory: Inventory,
    title: Component,
    /** The GUI background texture for this machine */
    protected val texture: ResourceLocation
) : AbstractContainerScreen<T>(menu, playerInventory, title) {

    /** Energy bar rendering constants (default positions, overridable) */
    protected var energyBarX = 10
    protected var energyBarY = 14
    protected var energyBarWidth = 12
    protected var energyBarHeight = 58

    /** Energy bar source in texture atlas */
    protected var energyBarU = 176
    protected var energyBarV = 0

    /** Progress arrow rendering constants (default positions, overridable) */
    protected var arrowX = 79
    protected var arrowY = 34
    protected var arrowWidth = 24
    protected var arrowHeight = 17

    /** Progress arrow source in texture atlas */
    protected var arrowU = 176
    protected var arrowV = 58

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun init() {
        super.init()
        titleLabelX = (imageWidth - font.width(title)) / 2
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        renderTooltip(guiGraphics, mouseX, mouseY)
        renderExtraTooltips(guiGraphics, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val x = leftPos
        val y = topPos

        // Draw background
        guiGraphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight)

        // Draw energy bar
        renderEnergyBar(guiGraphics, x, y)

        // Draw progress arrow
        renderProgressArrow(guiGraphics, x, y)

        // Hook for subclass-specific rendering
        renderExtra(guiGraphics, x, y, partialTick)
    }

    /**
     * Renders the TF energy bar indicator.
     * The bar fills from bottom to top proportional to stored energy.
     */
    protected open fun renderEnergyBar(guiGraphics: GuiGraphics, x: Int, y: Int) {
        val scaledEnergy = menu.getScaledEnergy(energyBarHeight)
        if (scaledEnergy > 0) {
            guiGraphics.blit(
                texture,
                x + energyBarX,
                y + energyBarY + energyBarHeight - scaledEnergy,
                energyBarU,
                energyBarV + energyBarHeight - scaledEnergy,
                energyBarWidth,
                scaledEnergy
            )
        }
    }

    /**
     * Renders the progress arrow indicator.
     * The arrow fills from left to right proportional to processing progress.
     */
    protected open fun renderProgressArrow(guiGraphics: GuiGraphics, x: Int, y: Int) {
        val scaledProgress = menu.getScaledProgress(arrowWidth)
        if (scaledProgress > 0) {
            guiGraphics.blit(
                texture,
                x + arrowX,
                y + arrowY,
                arrowU,
                arrowV,
                scaledProgress + 1,
                arrowHeight
            )
        }
    }

    /**
     * Hook for subclasses to render additional elements on the background layer.
     */
    protected open fun renderExtra(guiGraphics: GuiGraphics, x: Int, y: Int, partialTick: Float) {
        // Override in subclass if needed
    }

    /**
     * Hook for subclasses to render additional tooltips.
     */
    protected open fun renderExtraTooltips(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        // Override in subclass if needed
    }

    /**
     * Utility: checks if the mouse is hovering over a rectangular region.
     */
    protected fun isHovering(x: Int, y: Int, width: Int, height: Int, mouseX: Double, mouseY: Double): Boolean {
        val guiX = leftPos + x
        val guiY = topPos + y
        return mouseX >= guiX && mouseX < guiX + width && mouseY >= guiY && mouseY < guiY + height
    }
}
