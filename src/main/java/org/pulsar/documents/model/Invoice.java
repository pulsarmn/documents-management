package org.pulsar.documents.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public class Invoice extends Document {

    private Currency currency;
    private BigDecimal currencyRate;
    private String product;
    private int count;

    public Invoice() {}

    public Invoice(String number, LocalDate date, String user, BigDecimal sum, Currency currency, BigDecimal currencyRate, String product, int count) {
        super(number, date, user, sum);
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.product = product;
        this.count = count;
    }

    @Override
    protected String getType() {
        return "Накладная";
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getCurrencyRate() {
        return currencyRate;
    }

    public String getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), currency, currencyRate, product, count);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Invoice invoice = (Invoice) obj;
        return Objects.equals(currency, invoice.currency) &&
                Objects.equals(currencyRate, invoice.currencyRate) &&
                Objects.equals(product, invoice.product) &&
                Objects.equals(count, invoice.count);
    }
}
