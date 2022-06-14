package com.yb.board.dto;

import com.yb.board.Entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .password(password)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardDto(Long id, String title, String content, String writer, String password, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.password = password;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
