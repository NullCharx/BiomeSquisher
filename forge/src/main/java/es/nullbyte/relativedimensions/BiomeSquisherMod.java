package es.nullbyte.relativedimensions;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.DeferredRegister;

@Mod(Utils.MOD_ID)
public class BiomeSquisherMod {
    private static final DeferredRegister<Codec<? extends DensityFunction>> DENSITY_FUNCTION_TYPE = DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, Utils.MOD_ID);

    public BiomeSquisherMod() {
        BiomeSquisher.init();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        var gameBus = MinecraftForge.EVENT_BUS;

        DENSITY_FUNCTION_TYPE.register(modBus);
        DENSITY_FUNCTION_TYPE.register(InternalScalingSampler.LOCATION.getPath(), InternalScalingSampler.CODEC::codec);

        modBus.addListener(EventPriority.HIGH, this::createDatapackRegistries);
        gameBus.addListener(EventPriority.HIGH, this::registerCommands);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            gameBus.addListener(EventPriority.HIGH, event -> WebServerThread.stopServer());
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
