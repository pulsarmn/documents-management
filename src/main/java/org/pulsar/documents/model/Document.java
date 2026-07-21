package org.pulsar.documents.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Invoice.class, name = "INVOICE"),
        @JsonSubTypes.Type(value = Payment.class, name = "PAYMENT"),
        @JsonSubTypes.Type(value = PaymentRequest.class, name = "PAYMENT_REQUEST")
})
public abstract class Document {

    private String number;
    private LocalDate date;
    private String user;
    private BigDecimal sum;

    public Document() {}

    public Document(String number, LocalDate date, String user, BigDecimal sum) {
        this.number = number;
        this.date = date;
        this.user = user;
        this.sum = sum;
    }

    protected abstract String getType();

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public BigDecimal getSum() {
        return sum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, user, sum);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Document document = (Document) obj;
        return Objects.equals(number, document.number) &&
                Objects.equals(date, document.date) &&
                Objects.equals(user, document.user) &&
                Objects.equals(sum, document.sum);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return "%s от %s номер %s".formatted(getType(), date.format(formatter), number);
    }
}
