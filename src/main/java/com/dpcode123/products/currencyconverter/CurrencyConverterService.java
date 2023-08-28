package com.dpcode123.products.currencyconverter;

import java.math.BigDecimal;

public interface CurrencyConverterService {

    BigDecimal convertEurToUsd(BigDecimal priceEur);
}
