package com.dpcode123.products.currencyconverter.hnbapi;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface HnbApiService {

    BigDecimal getEurToUsdExchangeRate();
}
