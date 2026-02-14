package tsuteto.tofu.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.util.ModLog;

import java.util.HashSet;
import java.util.Set;

/**
 * Persistent per-level saved data for TofuCraft.
 * Currently stores the set of block positions where morijio
 * (salt mounds) have been placed so that their fermentation
 * aura can be queried without iterating loaded block entities.
 *
 * <p>Data is saved under the key {@value DATA_NAME} in the
 * level's data folder.</p>
 */
public class TcSavedData extends SavedData {

    private static final String DATA_NAME = TofuCraftMod.MOD_ID + "_data";

    private static final String TAG_MORIJIO_LIST = "MorijioPositions";
    private static final String TAG_X = "X";
    private static final String TAG_Y = "Y";
    private static final String TAG_Z = "Z";

    /** Set of block positions where morijio blocks are placed. */
    private final Set<BlockPos> morijioPositions = new HashSet<>();

    /**
     * Constructs a new, empty saved data instance.
     */
    public TcSavedData() {
        super();
    }

    /**
     * Constructs a saved data instance from a previously serialised
     * {@link CompoundTag}.
     *
     * @param tag              the compound tag to load from
     * @param registryLookup   holder lookup provider (required by NeoForge API)
     * @return a populated TcSavedData
     */
    public static TcSavedData load(CompoundTag tag, HolderLookup.Provider registryLookup) {
        TcSavedData data = new TcSavedData();

        if (tag.contains(TAG_MORIJIO_LIST, Tag.TAG_LIST)) {
            ListTag listTag = tag.getList(TAG_MORIJIO_LIST, Tag.TAG_COMPOUND);
            for (int i = 0; i < listTag.size(); i++) {
                CompoundTag posTag = listTag.getCompound(i);
                int x = posTag.getInt(TAG_X);
                int y = posTag.getInt(TAG_Y);
                int z = posTag.getInt(TAG_Z);
                data.morijioPositions.add(new BlockPos(x, y, z));
            }
        }

        ModLog.debug("Loaded TcSavedData with {} morijio positions", data.morijioPositions.size());
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registryLookup) {
        ListTag listTag = new ListTag();
        for (BlockPos pos : morijioPositions) {
            CompoundTag posTag = new CompoundTag();
            posTag.putInt(TAG_X, pos.getX());
            posTag.putInt(TAG_Y, pos.getY());
            posTag.putInt(TAG_Z, pos.getZ());
            listTag.add(posTag);
        }
        tag.put(TAG_MORIJIO_LIST, listTag);
        return tag;
    }

    // ---- Morijio position management ----

    /**
     * Registers a morijio block position.
     *
     * @param pos the block position
     */
    public void addMorijio(BlockPos pos) {
        if (morijioPositions.add(pos.immutable())) {
            setDirty();
        }
    }

    /**
     * Unregisters a morijio block position (e.g. when the block is broken).
     *
     * @param pos the block position
     */
    public void removeMorijio(BlockPos pos) {
        if (morijioPositions.remove(pos)) {
            setDirty();
        }
    }

    /**
     * Returns whether there is a morijio at the given position.
     *
     * @param pos the position to check
     * @return true if a morijio is registered at this position
     */
    public boolean hasMorijio(BlockPos pos) {
        return morijioPositions.contains(pos);
    }

    /**
     * Returns an unmodifiable view of all registered morijio positions.
     *
     * @return set of morijio block positions
     */
    public Set<BlockPos> getMorijioPositions() {
        return Set.copyOf(morijioPositions);
    }

    /**
     * Returns the number of registered morijio positions.
     *
     * @return count
     */
    public int getMorijioCount() {
        return morijioPositions.size();
    }

    // ---- Factory ----

    /**
     * Retrieves or creates the {@link TcSavedData} instance for the given
     * server level.
     *
     * @param level the server level
     * @return the TcSavedData for this level
     */
    public static TcSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(TcSavedData::new, TcSavedData::load),
                DATA_NAME
        );
    }
}
