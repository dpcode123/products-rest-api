package com.dpcode123.products.currencyconverter.hnbapi;

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
    public ResponseEntity<BigDecimal> getUsdToEurExchangeRate() {
        return ResponseEntity.ok(hnbApiService.getEurToUsdExchangeRate());
    }
}
