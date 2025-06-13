package org.example.shopping.board.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;
import java.io.File;
import java.util.List;

@Data
@AllArgsConstructor
public class BoardInsertReq {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String writer;

    @NotBlank
    private String writerId;

    private boolean isNotice;

    private boolean isSecret;

    private boolean isDraft;

    private int status;

    private int parentId;

    private int depth;

    private List<File> attachments;

    private List<Image> images;
}
