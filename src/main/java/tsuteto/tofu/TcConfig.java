package tsuteto.tofu;

import net.neoforged.neoforge.common.ModConfigSpec;

public class TcConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue ENABLE_ACHIEVEMENTS;
    public static final ModConfigSpec.BooleanValue ENABLE_UPDATE_CHECK;
    public static final ModConfigSpec.BooleanValue SALT_PAN_ALPHA;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("general");
        ENABLE_ACHIEVEMENTS = builder
                .comment("Enable achievements")
                .define("enableAchievements", true);
        ENABLE_UPDATE_CHECK = builder
                .comment("Enable update check on startup")
                .define("enableUpdateCheck", false);
        SALT_PAN_ALPHA = builder
                .comment("Enable alpha rendering for salt pan")
                .define("saltPanAlpha", true);
        builder.pop();

        SPEC = builder.build();
    }
}
