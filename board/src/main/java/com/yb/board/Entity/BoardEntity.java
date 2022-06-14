package com.yb.board.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "board")
public class BoardEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 10, nullable = false)
    private String password;

    @OneToMany(mappedBy = "boardEntity")
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @Builder
    public BoardEntity(Long id, String title, String content, String writer, String password){
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.password = password;
    }

}
