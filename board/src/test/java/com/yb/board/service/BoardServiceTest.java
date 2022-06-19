package com.yb.board.service;

import com.yb.board.Entity.BoardEntity;
import com.yb.board.dto.BoardDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    BoardDto createBoardDto(){
        return new BoardDto(null, "첫 번째 게시글", "내용", "글쓴이", "12341234", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("게시글 저장 테스트")
    void savePost(){
        BoardDto boardDto = createBoardDto();
        long savedId = boardService.savePost(boardDto);

        Assertions.assertEquals(1L, boardService.getPostEntity(savedId).getId());
        Assertions.assertEquals("첫 번째 게시글", boardService.getPostEntity(savedId).getTitle());
        Assertions.assertEquals("내용", boardService.getPostEntity(savedId).getContent());
        Assertions.assertEquals("글쓴이", boardService.getPostEntity(savedId).getWriter());
        Assertions.assertEquals("12341234", boardService.getPostEntity(savedId).getPassword());
    }

    //modify test는 비밀번호 입력 화면때문에 로직이 살짝 애매해서 추후 추가 예정

    @Test
    @DisplayName("게시글 삭제 성공 테스트")
    void deletePostSuccess(){
        BoardDto boardDto = createBoardDto();
        String password = boardDto.getPassword();
        long savedId = boardService.savePost(boardDto);

        boolean result = boardService.deletePost(savedId, password);

        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("게시글 삭제 실패 테스트")
    void deletePostFail(){
        BoardDto boardDto = createBoardDto();
        String password = boardDto.getPassword();
        long savedId = boardService.savePost(boardDto);

        boolean result = boardService.deletePost(savedId, "실패테스트");

        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(false);
    }


}