package org.pulsar.documents.util;


import java.math.BigDecimal;

public abstract class ValidationUtils {

    public static boolean hasAnyNullOrBlank(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            } else if (obj instanceof String str && str.isBlank()) {
                return true;
            }
        }
        return false;
    }

    public static boolean canBeDecimal(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean canBeInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
