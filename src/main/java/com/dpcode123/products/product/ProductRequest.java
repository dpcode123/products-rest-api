package com.dpcode123.products.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "Name is empty")
    private String name;

    @NotNull(message = "Price is null")
    @Min(value = 0, message = "Price is less than 0")
    private BigDecimal priceEur;
    @NotBlank(message = "Description is empty")
    private String description;

    @NotNull(message = "IsAvailable is null")
    private Boolean isAvailable;

    public ProductRequest(@JsonProperty("name") String name,
                          @JsonProperty("priceEur") BigDecimal priceEur,
                          @JsonProperty("description") String description,
                          @JsonProperty("isAvailable") Boolean isAvailable) {
        this.name = name;
        this.priceEur = priceEur;
        this.description = description;
        this.isAvailable = isAvailable;
    }

}
