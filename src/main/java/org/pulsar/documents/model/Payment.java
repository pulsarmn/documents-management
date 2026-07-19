package org.pulsar.documents.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public class Payment extends Document {

    private String employee;

    public Payment(String number, LocalDate date, String user, BigDecimal sum, String employee) {
        super(number, date, user, sum);
        this.employee = employee;
    }

    public String getEmployee() {
        return employee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), employee);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Payment payment = (Payment) obj;
        return Objects.equals(employee, payment.employee);
    }
}
