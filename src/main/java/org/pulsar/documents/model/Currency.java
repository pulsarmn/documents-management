package org.pulsar.documents.model;


public enum Currency {
    RUB("Рубль"),
    USD("Доллар"),
    EUR("Евро");

    private final String title;

    Currency(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
