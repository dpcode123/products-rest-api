package com.dpcode123.products.exception;

import java.util.Date;

public record ApiException(Date timestamp, String message, String details) {

}