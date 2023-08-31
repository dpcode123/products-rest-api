package com.dpcode123.products.currency.converter.hnbapi;

import com.dpcode123.products.currency.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@ConditionalOnExpression("${hnbapi.testing.controller.enabled}")
@RestController
@RequestMapping(path = "hnb-api-test")
@RequiredArgsConstructor
public class HnbApiTestingController {

    private final HnbApiService hnbApiService;

    @GetMapping(path = "/usd")
    public ResponseEntity<BigDecimal> getExchangeRateEurToUsd() {
        return ResponseEntity.ok(hnbApiService.getExchangeRateEurTo(Currency.USD));
    }

}
