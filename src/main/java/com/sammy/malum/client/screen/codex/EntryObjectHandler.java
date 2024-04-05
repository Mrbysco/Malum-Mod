package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.screens.*;

public class EntryObjectHandler<T extends AbstractProgressionCodexScreen<T>> extends BookObjectHandler<T>{
    public EntryObjectHandler() {
        super();
    }

    public void setupEntryObjects(T screen) {
        clear();
        int left = screen.getGuiLeft() + screen.bookInsideWidth;
        int top = screen.getGuiTop() + screen.bookInsideHeight;
        for (BookEntry<T> entry : screen.getEntries()) {
            entry.getWidgetData().ifPresent(data -> {
                final ProgressionEntryObject<T> bookObject = data.widgetSupplier().getBookObject(entry, left + data.xOffset(), top - data.yOffset());
                var config = data.widgetConfig();
                if (config != null) {
                    config.accept(bookObject);
                }
                add(bookObject);
            });
        }
        screen.faceObject(get(1));
    }
}
