package com.dpcode123.products.currencyconverter;

import com.dpcode123.products.currency.Currency;
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
    public BigDecimal convertEurTo(Currency currency, BigDecimal priceEur) {
        if (Currency.EUR.equals(currency)) return priceEur;

        return convertEurByRate(priceEur, hnbApiService.getExchangeRateEurTo(currency));
    }

    private BigDecimal convertEurByRate(BigDecimal priceEur, BigDecimal exchangeRate) {
        return priceEur.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
    }
}
