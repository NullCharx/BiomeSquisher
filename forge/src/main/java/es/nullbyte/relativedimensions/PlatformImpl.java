package es.nullbyte.relativedimensions;

import com.google.auto.service.AutoService;
import es.nullbyte.squishedforge.impl.Platform;
import es.nullbyte.squishedforge.impl.Utils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.ModList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@AutoService(Platform.class)
public class PlatformImpl implements Platform {
    @Override
    public Path gameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public Optional<Path> getRootResource(String resource) {
        Path path = ModList.get().getModFileById(Utils.MOD_ID).getFile().findResource(resource);
        if (Files.exists(path)) {
            return Optional.of(path);
        }
        return Optional.empty();
    }

    @Override
    public boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }
}
