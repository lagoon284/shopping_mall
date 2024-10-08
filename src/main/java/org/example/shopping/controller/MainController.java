package org.example.shopping.controller;

import org.example.shopping.model.ProductInfo;
import org.example.shopping.service.CommonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    private final CommonService commonService;

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
    public List main() throws IOException {

        List<ProductInfo> prodInfo = commonService.jsonToProdInfo();
        System.out.println(prodInfo);

        return prodInfo;
    }
}
