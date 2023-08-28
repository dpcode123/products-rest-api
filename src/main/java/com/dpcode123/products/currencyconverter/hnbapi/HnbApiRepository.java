package com.dpcode123.products.currencyconverter.hnbapi;

import com.dpcode123.products.exception.exceptions.HnbApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HnbApiRepository {

    private final WebClient.Builder webClientBuilder;

    private static final String API_REQUEST_URI = "https://api.hnb.hr/tecajn-eur/v3?valuta=USD";

    public BigDecimal getEurToUsdExchangeRate() {
        List<ApiResponseObjectSingleCurrency> response = webClientBuilder
                .build()
                .get()
                .uri(API_REQUEST_URI)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ApiResponseObjectSingleCurrency>>() {})
                .block();

        if (response == null) {
            throw new HnbApiRequestException("Exchange rate can not be retrieved.");
        }
        String exchangeRate = response.get(0).getExchangeRate().replace(",", ".");
        return new BigDecimal(exchangeRate);

    }
}
