package org.pulsar.documents.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public abstract class Document {

    private String number;
    private LocalDate date;
    private String user;
    private BigDecimal sum;

    public Document(String number, LocalDate date, String user, BigDecimal sum) {
        this.number = number;
        this.date = date;
        this.user = user;
        this.sum = sum;
    }

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
}
