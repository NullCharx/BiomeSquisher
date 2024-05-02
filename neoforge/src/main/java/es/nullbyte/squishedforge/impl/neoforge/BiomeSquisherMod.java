package es.nullbyte.squishedforge.impl.neoforge;

import com.mojang.serialization.Codec;
import es.nullbyte.squishedforge.BiomeSquisherRegistries;
import es.nullbyte.squishedforge.Series;
import es.nullbyte.squishedforge.Squisher;
import es.nullbyte.squishedforge.impl.BiomeSquisher;
import es.nullbyte.squishedforge.impl.BiomeSquisherCommands;
import es.nullbyte.squishedforge.impl.InternalScalingSampler;
import es.nullbyte.squishedforge.impl.Utils;
import es.nullbyte.squishedforge.impl.server.WebServerThread;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Utils.MOD_ID)
public class BiomeSquisherMod {
    private static final DeferredRegister<Codec<? extends DensityFunction>> DENSITY_FUNCTION_TYPE = DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, Utils.MOD_ID);

    public BiomeSquisherMod() {
        BiomeSquisher.init();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        var gameBus = NeoForge.EVENT_BUS;

        DENSITY_FUNCTION_TYPE.register(modBus);
        DENSITY_FUNCTION_TYPE.register(InternalScalingSampler.LOCATION.getPath(), InternalScalingSampler.CODEC::codec);

        modBus.addListener(DataPackRegistryEvent.NewRegistry.class, this::createDatapackRegistries);
        gameBus.addListener(RegisterCommandsEvent.class, this::registerCommands);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            gameBus.addListener(ServerStoppingEvent.class, event -> WebServerThread.stopServer());
        }
    }

    private void createDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(BiomeSquisherRegistries.SERIES, Series.CODEC);
        event.dataPackRegistry(BiomeSquisherRegistries.SQUISHER, Squisher.CODEC);
    }

    private void registerCommands(RegisterCommandsEvent event) {
        BiomeSquisherCommands.register(event.getDispatcher());
    }
}
