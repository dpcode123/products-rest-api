package com.dpcode123.products.codegenerator;

import com.dpcode123.products.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    private final ProductRepository productRepository;

    @Override
    public String generateUniqueProductCode() {
        String code = null;
        boolean generating = true;

        while (generating) {
            code = generateRandomProductCode();
            if (!productRepository.existsByCode(code)) {
                generating = false;
            }
        }
        return code;
    }

    private String generateRandomProductCode() {
        final int length = 10;
        final String characters = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
