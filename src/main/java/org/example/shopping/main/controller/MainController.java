package org.example.shopping.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.shopping.product.dto.ProductDetail;
import org.example.shopping.util.common.CommonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class MainController {

    private final CommonService commonService;

    // default constructor 를 이용한 의존성 주입.
    // 다른 방법으로는 lombok의 @RequiredArgsConstructor 가 있음. 해당 방법은 lombok이 필요함.
    public MainController(CommonService commonService) {
        this.commonService = commonService;
    }


    /* 해당 방식은 기존 백앤드와 프론트를 같이 사용할 때 사용하는 방법
    @RequestMapping("/")
    public String main() {
        return "index";
    }*/

    @RequestMapping("/main")
    @ResponseBody
    public List<ProductDetail> main() throws IOException {

        return commonService.jsonToProdInfo();
    }
}
