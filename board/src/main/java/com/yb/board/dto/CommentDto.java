package com.yb.board.dto;

import com.yb.board.Entity.BoardEntity;
import com.yb.board.Entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    public String nickname;
    public String password;
    public String comment;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public CommentEntity toEntity(){
        CommentEntity commentEntity = CommentEntity.builder()
                .id(id)
                .nickname(nickname)
                .password(password)
                .comment(comment)
                .build();
        return commentEntity;
    }

    @Builder
    public CommentDto(Long id, String nickname, String password, String comment, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.comment = comment;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
