package org.pulsar.documents.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public class PaymentRequest extends Document {

    private String counterparty;
    private Currency currency;
    private BigDecimal currencyRate;
    private BigDecimal commission;

    public PaymentRequest() {}

    public PaymentRequest(String number, LocalDate date, String user, BigDecimal sum, String counterparty, Currency currency, BigDecimal currencyRate, BigDecimal commission) {
        super(number, date, user, sum);
        this.counterparty = counterparty;
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.commission = commission;
    }

    @Override
    protected String getType() {
        return "Заявка на оплату";
    }

    public String getCounterparty() {
        return counterparty;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getCurrencyRate() {
        return currencyRate;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), counterparty, currency, currencyRate, commission);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        PaymentRequest paymentRequest = (PaymentRequest) obj;
        return Objects.equals(counterparty, paymentRequest.counterparty) &&
                Objects.equals(currency, paymentRequest.currency) &&
                Objects.equals(currencyRate, paymentRequest.currencyRate) &&
                Objects.equals(commission, paymentRequest.commission);
    }
}
