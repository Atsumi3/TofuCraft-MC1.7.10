package tsuteto.tofu

import net.neoforged.neoforge.common.ModConfigSpec

object TcConfig {
    @JvmField
    val ENABLE_ACHIEVEMENTS: ModConfigSpec.BooleanValue

    @JvmField
    val ENABLE_UPDATE_CHECK: ModConfigSpec.BooleanValue

    @JvmField
    val SALT_PAN_ALPHA: ModConfigSpec.BooleanValue

    @JvmField
    val SPEC: ModConfigSpec

    init {
        val builder = ModConfigSpec.Builder()

        builder.push("general")
        ENABLE_ACHIEVEMENTS = builder
            .comment("Enable achievements")
            .define("enableAchievements", true)
        ENABLE_UPDATE_CHECK = builder
            .comment("Enable update check on startup")
            .define("enableUpdateCheck", false)
        SALT_PAN_ALPHA = builder
            .comment("Enable alpha rendering for salt pan")
            .define("saltPanAlpha", true)
        builder.pop()

        SPEC = builder.build()
    }
}
