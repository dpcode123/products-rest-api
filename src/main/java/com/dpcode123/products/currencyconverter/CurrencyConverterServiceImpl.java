package com.dpcode123.products.currencyconverter;

import com.dpcode123.products.currencyconverter.hnbapi.HnbApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private final HnbApiService hnbApiService;

    @Override
    public BigDecimal convertEurToUsd(BigDecimal priceEur) {
        return convertEurByRate(priceEur, hnbApiService.getEurToUsdExchangeRate());
    }

    private BigDecimal convertEurByRate(BigDecimal priceEur, BigDecimal exchangeRate) {
        return priceEur.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
    }
}
