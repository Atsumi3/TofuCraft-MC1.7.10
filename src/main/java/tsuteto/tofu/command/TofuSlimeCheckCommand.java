package tsuteto.tofu.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

/**
 * Brigadier command that checks whether the player's current chunk
 * is a valid tofu slime spawning chunk.
 *
 * <p>Usage: {@code /tofuslimecheck}</p>
 *
 * <p>The algorithm mirrors the one in
 * {@link tsuteto.tofu.entity.EntityTofuSlime#checkTofuSlimeSpawnRules}.</p>
 */
public final class TofuSlimeCheckCommand {

    private TofuSlimeCheckCommand() {
        // Utility class; no instantiation
    }

    /**
     * Registers the {@code /tofuslimecheck} command with the dispatcher.
     *
     * @param dispatcher the command dispatcher
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("tofuslimecheck")
                        .requires(source -> source.hasPermission(0))
                        .executes(TofuSlimeCheckCommand::execute)
        );
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        Entity entity = source.getEntity();

        if (entity == null) {
            source.sendFailure(Component.literal("This command must be run by an entity."));
            return 0;
        }

        ServerLevel level = source.getLevel();
        BlockPos pos = entity.blockPosition();
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        boolean isTofuSlimeChunk = checkTofuSlimeChunk(level, chunkX, chunkZ);

        Component message;
        if (isTofuSlimeChunk) {
            message = Component.literal("Chunk [" + chunkX + ", " + chunkZ + "] ")
                    .append(Component.literal("CAN").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD))
                    .append(Component.literal(" spawn tofu slimes (y=15-40)."));
        } else {
            message = Component.literal("Chunk [" + chunkX + ", " + chunkZ + "] ")
                    .append(Component.literal("CANNOT").withStyle(ChatFormatting.RED, ChatFormatting.BOLD))
                    .append(Component.literal(" spawn tofu slimes."));
        }

        source.sendSuccess(() -> message, false);
        return isTofuSlimeChunk ? 1 : 0;
    }

    /**
     * Determines if the given chunk coordinates constitute a tofu slime
     * chunk for the given level. Uses the same seed-based algorithm as
     * {@link tsuteto.tofu.entity.EntityTofuSlime}.
     *
     * @param level  the server level
     * @param chunkX chunk X coordinate
     * @param chunkZ chunk Z coordinate
     * @return true if this is a tofu slime chunk
     */
    private static boolean checkTofuSlimeChunk(ServerLevel level, int chunkX, int chunkZ) {
        long seed = level.getSeed();
        long chunkSeed = seed
                + (long) (chunkX * chunkX * 4987142)
                + (long) (chunkX * 5947611)
                + (long) (chunkZ * chunkZ) * 4392871L
                + (long) (chunkZ * 389711) ^ 987234911L;
        RandomSource chunkRandom = RandomSource.create(chunkSeed);
        return chunkRandom.nextInt(10) == 0;
    }
}
