package com.dpcode123.products.currencyconverter.hnbapi;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class HnbApiServiceImpl implements HnbApiService {

    private final HnbApiRepository hnbApiRepository;

    @Override
    @Cacheable(cacheNames = "currencies", key = "'usd'")
    public BigDecimal getEurToUsdExchangeRate() {
        return hnbApiRepository.getEurToUsdExchangeRate();
    }

}
