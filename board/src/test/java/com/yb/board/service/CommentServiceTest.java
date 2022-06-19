package com.yb.board.service;

import com.yb.board.dto.BoardDto;
import com.yb.board.dto.CommentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
class CommentServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private CommentService commentService;

    BoardDto createBoardDto(){
        return new BoardDto(null, "첫 번째 게시글", "내용", "글쓴이", "12341234", LocalDateTime.now(), LocalDateTime.now());
    }

    CommentDto commentDto(){
        return new CommentDto(null, "테스트 닉네임", "testpwd", "test comment", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("코멘트 저장 성공 테스트")
    public void saveCommentSuccess(){
        BoardDto boardDto = createBoardDto();
        Long postId = boardService.savePost(boardDto);

        CommentDto commentDto = commentDto();
        Long commentId = commentService.saveComment(postId, commentDto);

        CommentDto findCommentDto = commentService.findComments(postId).get(0);

        Assertions.assertEquals(commentId, findCommentDto.getId());
        Assertions.assertEquals(commentDto.getNickname(), findCommentDto.getNickname());
        Assertions.assertEquals(commentDto().getPassword(), findCommentDto.getPassword());
        Assertions.assertEquals(commentDto.getComment(), findCommentDto.getComment());
    }

    @Test
    @DisplayName("코멘트 저장 실패 테스트")
    public void saveCommentFail(){
        BoardDto boardDto = createBoardDto();
        Long postId = boardService.savePost(boardDto);

        CommentDto commentDto = commentDto();

        Throwable e = Assertions.assertThrows(NoSuchElementException.class, ()->{
            commentService.saveComment(999L, commentDto);
        });

        Assertions.assertEquals("존재하지 않는 게시글의 코멘트 저장 요청 발생", e.getMessage());
    }

}