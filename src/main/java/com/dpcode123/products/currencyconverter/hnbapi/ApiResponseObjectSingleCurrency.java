package com.dpcode123.products.currencyconverter.hnbapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Single currency exchange rate JSON response object example.
 * Request URI for USD: https://api.hnb.hr/tecajn-eur/v3?valuta=USD
 * Response example:
 *        [
*           {
*             "broj_tecajnice": "165",
*             "datum_primjene": "2023-08-23",
*             "drzava": "SAD",
*             "drzava_iso": "USA",
*             "sifra_valute": "840",
*             "valuta": "USD",
*             "kupovni_tecaj": "1,0903",
*             "srednji_tecaj": "1,0887",
*             "prodajni_tecaj": "1,0871"
*           }
 *        ]
 */
@Data
@AllArgsConstructor
public class ApiResponseObjectSingleCurrency {

    @JsonProperty("datum_primjene")
    private String effectiveDate;

    @JsonProperty("drzava_iso")
    private String countryISO;

    @JsonProperty("valuta")
    private String currency;

    @JsonProperty("srednji_tecaj")
    private String exchangeRate;
}
