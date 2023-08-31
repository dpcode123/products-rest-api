package com.dpcode123.products.currencyconverter;

import com.dpcode123.products.currency.Currency;

import java.math.BigDecimal;

public interface CurrencyConverterService {

    BigDecimal convertEurTo(Currency currency, BigDecimal priceEur);
}
