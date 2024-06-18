package com.sammy.malum.client.screen.codex;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.client.screen.codex.objects.progression.ProgressionEntryObject;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import net.minecraft.ChatFormatting;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class PlacedBookEntryBuilder extends BookEntryBuilder {

    protected PlacedBookEntry.WidgetSupplier widgetSupplier = ProgressionEntryObject::new;
    @Nullable
    protected Consumer<ProgressionEntryObject> widgetConfig = null;

    @Nullable
    protected Consumer<PlacedBookEntryBuilder> fragmentProperties = null;

    protected final int xOffset;
    protected final int yOffset;

    protected PlacedBookEntryBuilder(String identifier, boolean isVoid, int xOffset, int yOffset) {
        super(identifier, isVoid);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public PlacedBookEntryBuilder(String identifier, int xOffset, int yOffset) {
        super(identifier);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public PlacedBookEntryBuilder setWidgetSupplier(PlacedBookEntry.WidgetSupplier widgetSupplier) {
        this.widgetSupplier = widgetSupplier;
        return this;
    }

    public PlacedBookEntryBuilder configureWidget(Consumer<ProgressionEntryObject> configure) {
        this.widgetConfig = this.widgetConfig == null ? configure : this.widgetConfig.andThen(configure);
        return this;
    }

    public PlacedBookEntryBuilder withEmptyFragmentEntry() {
        this.fragmentProperties = b -> b
                .configureWidget(widget -> widget.setStyle(BookWidgetStyle.WITHERED))
                .styleTitle(style -> style.withColor(ChatFormatting.GRAY))
                .styleSubtitle(style -> style.withColor(ChatFormatting.DARK_GRAY));
        return this;
    }

    public PlacedBookEntryBuilder withTraceFragmentEntry() {
        this.fragmentProperties = b -> b
                .configureWidget(widget -> widget.setStyle(BookWidgetStyle.FRAMELESS)) // todo: add cool visual effects for Traces
                .disableTooltip();

        return this;
    }

    public PlacedBookEntryBuilder withFragmentEntry(Consumer<PlacedBookEntryBuilder> properties) {
        this.fragmentProperties = this.fragmentProperties == null ? properties : this.fragmentProperties.andThen(properties);
        return this;
    }

    public boolean hasFragment() {
        return fragmentProperties != null;
    }

    public PlacedBookEntry buildFragment() {
        if (fragmentProperties == null)
            return null;

        PlacedBookEntryBuilder builder = new PlacedBookEntryBuilder("fragment." + identifier, isVoid, xOffset, yOffset);
        builder.configureWidget(widgetConfig)
                .setWidgetSupplier(widgetSupplier)
                .setEntryVisibleWhen(() -> !entryVisibleChecker.getAsBoolean())
                .styleTitle(style -> style.withItalic(true))
                .styleSubtitle(style -> style.withItalic(true));
        fragmentProperties.accept(builder);
        return builder.build();
    }

    @Override
    public PlacedBookEntry build() {
        ImmutableList<BookPage> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference> entryReferences = ImmutableList.copyOf(references);
        PlacedBookEntry.BookEntryWidgetPlacementData data = new PlacedBookEntry.BookEntryWidgetPlacementData(xOffset * 40, yOffset * 40, widgetSupplier, widgetConfig);
        PlacedBookEntry bookEntry = new PlacedBookEntry(identifier, isVoid, data, bookPages, entryReferences, entryVisibleChecker, titleStyle, subtitleStyle, tooltipDisabled);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}