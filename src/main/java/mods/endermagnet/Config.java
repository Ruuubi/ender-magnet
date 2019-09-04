package mods.endermagnet;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class Config {

	public static final Config CONFIG;
	public static final ForgeConfigSpec CONFIG_SPEC;
	
	static {
		final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
		CONFIG_SPEC = specPair.getRight();
		CONFIG = specPair.getLeft();
	}
	
	public static boolean CLIENT_ENABLE_PARTICLES = true;
	
	public static BooleanValue CLIENT_ENABLE_PARTICLES_VALUE;
	
	public Config(ForgeConfigSpec.Builder builder) {
		builder.push("Particles");
		CLIENT_ENABLE_PARTICLES_VALUE = builder.comment("Enable particles").define("enable_particles", true);
		builder.pop();
	}
	
	public static void refreshConfigValues() {
		CLIENT_ENABLE_PARTICLES = CLIENT_ENABLE_PARTICLES_VALUE.get();
	}
	
}
