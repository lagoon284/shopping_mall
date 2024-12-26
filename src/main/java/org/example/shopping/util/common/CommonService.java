package org.example.shopping.util.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.shopping.product.dto.ProductInfo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class CommonService {

    // 지금은 사용하지 않음.
    // json 파일 test method
    public List<ProductInfo> jsonToProdInfo() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/product_info.json"), new TypeReference<List<ProductInfo>>() {});
    }
}
