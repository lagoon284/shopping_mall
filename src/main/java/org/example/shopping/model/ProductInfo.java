package org.example.shopping.model;

import lombok.*;
import org.example.shopping.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductInfo {

    private Long seqNo;

    private String prodNo;

    private String Name;

    private String price;

    private String provider;

    private String info;

    private String regDate;

    private String updDate;

}
