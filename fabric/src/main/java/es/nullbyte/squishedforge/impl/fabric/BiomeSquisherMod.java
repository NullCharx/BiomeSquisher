package es.nullbyte.squishedforge.impl.fabric;

import es.nullbyte.squishedforge.BiomeSquisherRegistries;
import es.nullbyte.squishedforge.Series;
import es.nullbyte.squishedforge.Squisher;
import es.nullbyte.squishedforge.impl.BiomeSquisher;
import es.nullbyte.squishedforge.impl.BiomeSquisherCommands;
import es.nullbyte.squishedforge.impl.InternalScalingSampler;
import es.nullbyte.squishedforge.impl.server.WebServerThread;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class BiomeSquisherMod implements ModInitializer {

    @Override
    public void onInitialize() {
        BiomeSquisher.init();
        Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, InternalScalingSampler.LOCATION, InternalScalingSampler.CODEC.codec());
        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) ->
            BiomeSquisherCommands.register(dispatcher));
        DynamicRegistries.register(BiomeSquisherRegistries.SERIES, Series.CODEC);
        DynamicRegistries.register(BiomeSquisherRegistries.SQUISHER, Squisher.CODEC);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ServerLifecycleEvents.SERVER_STOPPING.register(server -> WebServerThread.stopServer());
        }
    }
}
