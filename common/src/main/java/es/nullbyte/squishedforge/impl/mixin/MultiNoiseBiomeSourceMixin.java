package es.nullbyte.squishedforge.impl.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import es.nullbyte.squishedforge.impl.Squishers;
import es.nullbyte.squishedforge.impl.injected.Squishable;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin(MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin {

    @ModifyReturnValue(
        method = "collectPossibleBiomes",
        at = @At("RETURN")
    )
    private Stream<Holder<Biome>> biomesquisher_wrapBiomes(Stream<Holder<Biome>> biomes) {
        Squishers squishers = ((Squishable) ((MultiNoiseBiomeSourceAccessor) this).biomesquisher_parameters()).biomesquisher_squishers();
        if (squishers == null) {
            return biomes;
        }
        return Stream.concat(biomes, squishers.possibleBiomes());
    }
}
