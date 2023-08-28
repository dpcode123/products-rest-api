package com.dpcode123.products.exception.exceptions;

import org.springframework.web.reactive.function.client.WebClientException;

public class HnbApiRequestException extends WebClientException {

    public HnbApiRequestException(String message) { super(message); }

}
