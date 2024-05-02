package es.nullbyte.squishedforge.impl.fabric;

import com.google.auto.service.AutoService;
import es.nullbyte.squishedforge.impl.Platform;
import es.nullbyte.squishedforge.impl.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.Optional;

@AutoService(Platform.class)
public class PlatformImpl implements Platform {
    @Override
    public Path gameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    @Override
    public Optional<Path> getRootResource(String resource) {
        return FabricLoader.getInstance().getModContainer(Utils.MOD_ID).orElseThrow().findPath(resource);
    }

    @Override
    public boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
