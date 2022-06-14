package com.yb.board.Entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
@ToString
@Table(name = "comment")
public class CommentEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Builder
    public CommentEntity(Long id, String nickname, String password, String comment){
        this.id = id;
        this.comment = comment;
        this.nickname = nickname;
        this.password = password;
    }
}
