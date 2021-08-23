package com.milewczyk.cartservice.model.models_from_other_services.productinfoservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@Embeddable
public class Price {

    public static final Currency CURRENCY_USD = Currency.getInstance("USD");
    public static final Price ZERO = new Price();

    private final Currency currency;
    private final BigDecimal value;

    private Price(Currency currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    protected Price() {
        this(CURRENCY_USD, BigDecimal.ZERO);
    }
}
