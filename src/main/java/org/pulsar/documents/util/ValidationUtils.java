package org.pulsar.documents.util;


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
}
