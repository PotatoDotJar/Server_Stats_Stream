package net.rmnad.minecraft.forge.serverstatsstream;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.util.UUID;

public class Config {
    public static class Common {
        public final String CATEGORY_GENERAL = "general";

        // General Settings
        public ForgeConfigSpec.ConfigValue<String> SERVER_NAME;
        public ForgeConfigSpec.IntValue REPORT_INTERVAL;


        Common(final ForgeConfigSpec.Builder builder) {
            // General Settings
            builder.comment("General configuration").push(CATEGORY_GENERAL);

            SERVER_NAME = builder.comment("Server name")
                    .worldRestart()
                    .define("serverName", UUID.randomUUID().toString());

            REPORT_INTERVAL = builder.comment("Interval in ms for reporting")
                    .worldRestart()
                    .defineInRange("reportInterval", 10000, 100, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    private static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, commonSpec);
    }
}
