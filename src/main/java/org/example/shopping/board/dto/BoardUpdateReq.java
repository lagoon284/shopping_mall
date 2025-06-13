package org.example.shopping.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardUpdateReq {

    private int seqNo;

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

    private String attachments;

    private String images;
}
