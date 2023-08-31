package com.dpcode123.products.currencyconverter.hnbapi;

import com.dpcode123.products.currency.Currency;

import java.math.BigDecimal;

public interface HnbApiService {

    BigDecimal getExchangeRateEurTo(Currency currency);
}
