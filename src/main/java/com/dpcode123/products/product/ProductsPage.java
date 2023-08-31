package com.dpcode123.products.product;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class ProductsPage {

    private int pageSize = 36;

    private String pageNumber = "0";

    private Sort.Direction sortDirection = Sort.Direction.DESC;

    private String sortBy = "id";

    public Pageable createPageable() {
        return PageRequest.of(Integer.parseInt(pageNumber), pageSize, Sort.by(sortDirection, sortBy));
    }
}
