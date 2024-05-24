package com.sammy.malum.registry.common.block;

import com.sammy.malum.*;
import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.*;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.*;
import com.sammy.malum.common.block.curiosities.repair_pylon.*;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.common.block.curiosities.runic_workbench.*;
import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.block.curiosities.void_depot.*;
import com.sammy.malum.common.block.curiosities.weavers_workbench.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.common.block.mana_mote.*;
import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.block.storage.pedestal.*;
import com.sammy.malum.common.block.storage.stand.*;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;

import java.util.*;

import static com.sammy.malum.MalumMod.*;

public class BlockEntityRegistry {
    public static final LazyRegistrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = LazyRegistrar.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MALUM);

    public static final RegistryObject<BlockEntityType<VoidConduitBlockEntity>> VOID_CONDUIT = BLOCK_ENTITY_TYPES.register("void_conduit", () -> BlockEntityType.Builder.of(VoidConduitBlockEntity::new, BlockRegistry.VOID_CONDUIT.get()).build(null));
    public static final RegistryObject<BlockEntityType<VoidDepotBlockEntity>> VOID_DEPOT = BLOCK_ENTITY_TYPES.register("void_depot", () -> BlockEntityType.Builder.of(VoidDepotBlockEntity::new, BlockRegistry.VOID_DEPOT.get()).build(null));

    public static final RegistryObject<BlockEntityType<SpiritAltarBlockEntity>> SPIRIT_ALTAR = BLOCK_ENTITY_TYPES.register("spirit_altar", () -> BlockEntityType.Builder.of(SpiritAltarBlockEntity::new, BlockRegistry.SPIRIT_ALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<SpiritJarBlockEntity>> SPIRIT_JAR = BLOCK_ENTITY_TYPES.register("spirit_jar", () -> BlockEntityType.Builder.of(SpiritJarBlockEntity::new, BlockRegistry.SPIRIT_JAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<RitualPlinthBlockEntity>> RITUAL_PLINTH = BLOCK_ENTITY_TYPES.register("ritual_plinth", () -> BlockEntityType.Builder.of(RitualPlinthBlockEntity::new, BlockRegistry.RITUAL_PLINTH.get()).build(null));

    public static final RegistryObject<BlockEntityType<WeaversWorkbenchBlockEntity>> WEAVERS_WORKBENCH = BLOCK_ENTITY_TYPES.register("weavers_workbench", () -> BlockEntityType.Builder.of(WeaversWorkbenchBlockEntity::new, BlockRegistry.WEAVERS_WORKBENCH.get()).build(null));
    public static final RegistryObject<BlockEntityType<RunicWorkbenchBlockEntity>> RUNIC_WORKBENCH = BLOCK_ENTITY_TYPES.register("runic_workbench", () -> BlockEntityType.Builder.of(RunicWorkbenchBlockEntity::new, BlockRegistry.RUNIC_WORKBENCH.get()).build(null));

    public static final RegistryObject<BlockEntityType<SpiritCrucibleCoreBlockEntity>> SPIRIT_CRUCIBLE = BLOCK_ENTITY_TYPES.register("spirit_crucible", () -> BlockEntityType.Builder.of(SpiritCrucibleCoreBlockEntity::new, BlockRegistry.SPIRIT_CRUCIBLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<SpiritCatalyzerCoreBlockEntity>> SPIRIT_CATALYZER = BLOCK_ENTITY_TYPES.register("spirit_catalyzer", () -> BlockEntityType.Builder.of(SpiritCatalyzerCoreBlockEntity::new, BlockRegistry.SPIRIT_CATALYZER.get()).build(null));
    public static final RegistryObject<BlockEntityType<RepairPylonCoreBlockEntity>> REPAIR_PYLON = BLOCK_ENTITY_TYPES.register("repair_pylon", () -> BlockEntityType.Builder.of(RepairPylonCoreBlockEntity::new, BlockRegistry.REPAIR_PYLON.get()).build(null));
    public static final RegistryObject<BlockEntityType<RunewoodObeliskBlockEntity>> RUNEWOOD_OBELISK = BLOCK_ENTITY_TYPES.register("runewood_obelisk", () -> BlockEntityType.Builder.of(RunewoodObeliskBlockEntity::new, BlockRegistry.RUNEWOOD_OBELISK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BrilliantObeliskBlockEntity>> BRILLIANT_OBELISK = BLOCK_ENTITY_TYPES.register("brilliant_obelisk", () -> BlockEntityType.Builder.of(BrilliantObeliskBlockEntity::new, BlockRegistry.BRILLIANT_OBELISK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EtherBlockEntity>> ETHER = BLOCK_ENTITY_TYPES.register("ether", () -> BlockEntityType.Builder.of(EtherBlockEntity::new, getBlocks(EtherBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<ItemStandBlockEntity>> ITEM_STAND = BLOCK_ENTITY_TYPES.register("item_stand", () -> BlockEntityType.Builder.of(ItemStandBlockEntity::new, getBlocks(ItemStandBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<ItemPedestalBlockEntity>> ITEM_PEDESTAL = BLOCK_ENTITY_TYPES.register("item_pedestal", () -> BlockEntityType.Builder.of(ItemPedestalBlockEntity::new, getBlocks(ItemPedestalBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<TotemBaseBlockEntity>> TOTEM_BASE = BLOCK_ENTITY_TYPES.register("totem_base", () -> BlockEntityType.Builder.of(TotemBaseBlockEntity::new, getBlocks(TotemBaseBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<TotemPoleBlockEntity>> TOTEM_POLE = BLOCK_ENTITY_TYPES.register("totem_pole", () -> BlockEntityType.Builder.of(TotemPoleBlockEntity::new, getBlocks(TotemPoleBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<MoteOfManaBlockEntity>> MOTE_OF_MANA = BLOCK_ENTITY_TYPES.register("mote_of_mana", () -> BlockEntityType.Builder.of(MoteOfManaBlockEntity::new, getBlocks(SpiritMoteBlock.class)).build(null));

    public static Block[] getBlocks(Class<?>... blockClasses) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        List<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (registryObject.isPresent() && Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(registryObject.get()))) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static Block[] getBlocksExact(Class<?> clazz) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        List<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (clazz.equals(registryObject.get().getClass())) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static class ClientOnly {
        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(VOID_CONDUIT.get(), VoidConduitRenderer::new);
            event.registerBlockEntityRenderer(VOID_DEPOT.get(), VoidDepotRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_ALTAR.get(), SpiritAltarRenderer::new);
            event.registerBlockEntityRenderer(RUNIC_WORKBENCH.get(), MalumItemHolderRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_CRUCIBLE.get(), SpiritCrucibleRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_CATALYZER.get(), SpiritCatalyzerRenderer::new);
            event.registerBlockEntityRenderer(REPAIR_PYLON.get(), RepairPylonRenderer::new);
            event.registerBlockEntityRenderer(TOTEM_BASE.get(), TotemBaseRenderer::new);
            event.registerBlockEntityRenderer(TOTEM_POLE.get(), TotemPoleRenderer::new);
            event.registerBlockEntityRenderer(RITUAL_PLINTH.get(), RitualPlinthRenderer::new);
            event.registerBlockEntityRenderer(ITEM_STAND.get(), MalumItemHolderRenderer::new);
            event.registerBlockEntityRenderer(ITEM_PEDESTAL.get(), MalumItemHolderRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_JAR.get(), SpiritJarRenderer::new);
            event.registerBlockEntityRenderer(MOTE_OF_MANA.get(), MoteOfManaRenderer::new);
        }
    }
}
