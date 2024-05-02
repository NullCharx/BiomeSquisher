package es.nullbyte.squishedforge;

import es.nullbyte.squishedforge.impl.Utils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class BiomeSquisherRegistries {
    private BiomeSquisherRegistries() {}

    public static final ResourceKey<Registry<es.nullbyte.squishedforge.Series>> SERIES = ResourceKey.createRegistryKey(Utils.id("series"));
    public static final ResourceKey<Registry<Squisher>> SQUISHER = ResourceKey.createRegistryKey(Utils.id("squisher"));
}
