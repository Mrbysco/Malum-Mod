package com.sammy.malum.config;


import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import team.lodestar.lodestone.systems.config.LodestoneConfig;

import static com.sammy.malum.MalumMod.MALUM;

public class ClientConfig extends LodestoneConfig {

    public static ConfigValueHolder<ArcanaCodexHelper.BookTheme> BOOK_THEME = new ConfigValueHolder<>(MALUM, "client/codex", (builder ->
            builder.comment("What theme should the encyclopedia arcana be in?")
                    .defineEnum("bookTheme", ArcanaCodexHelper.BookTheme.DEFAULT)));

    public static ConfigValueHolder<Boolean> SCROLL_DIRECTION = new ConfigValueHolder<>(MALUM, "client/codex", (builder ->
            builder.comment("Should the scroll direction be reversed in the encyclopedia arcana entry screen? This simply affects how you move through pages in an entry.")
                    .define("scrollDirection", false)));

    public ClientConfig(ModConfigSpec.Builder builder) {
        super(MALUM, "client", builder);
    }

    public static final ClientConfig INSTANCE;
    public static final ModConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
