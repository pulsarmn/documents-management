package org.pulsar.documents.util;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public abstract class StyleUtils {

    private static final Border OK_BORDER = new Border(new BorderStroke(
            Color.rgb(45, 108, 223),
            BorderStrokeStyle.SOLID,
            new CornerRadii(10),
            new BorderWidths(2)
    ));

    private static final Border OK_BORDER_FOCUSED = new Border(new BorderStroke(
            Color.rgb(31, 87, 194),
            BorderStrokeStyle.SOLID,
            new CornerRadii(10),
            new BorderWidths(2.5)
    ));

    private static final Border INVALID_BORDER = new Border(new BorderStroke(
            Color.rgb(226, 75, 75),
            BorderStrokeStyle.SOLID,
            new CornerRadii(10),
            new BorderWidths(2)
    ));

    private static final Border INVALID_BORDER_FOCUSED = new Border(new BorderStroke(
            Color.rgb(198, 46, 46),
            BorderStrokeStyle.SOLID,
            new CornerRadii(10),
            new BorderWidths(2.5)
    ));

    private static final DropShadow OK_SHADOW = new DropShadow(10, Color.rgb(45, 108, 223, 0.25));
    private static final DropShadow INVALID_SHADOW = new DropShadow(10, Color.rgb(226, 75, 75, 0.25));

    public static void applyValidStyle(TextField tf) {
        boolean focused = tf.isFocused();
        tf.setBorder(focused ? OK_BORDER_FOCUSED : OK_BORDER);
        tf.setEffect(focused ? OK_SHADOW : null);
    }

    public static void applyInvalidStyle(TextField tf) {
        boolean focused = tf.isFocused();
        tf.setBorder(focused ? INVALID_BORDER_FOCUSED : INVALID_BORDER);
        tf.setEffect(focused ? INVALID_SHADOW : null);
    }

    public static void applyNeutral(TextField tf) {
        tf.setBorder(new Border(new BorderStroke(
                Color.rgb(207, 207, 207),
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(1.5)
        )));
        tf.setBackground(new Background(new BackgroundFill(
                Color.WHITE,
                new CornerRadii(10),
                Insets.EMPTY
        )));
        tf.setEffect(null);
    }
}
