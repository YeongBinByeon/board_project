package com.yb.board.service;

import com.yb.board.Entity.BoardEntity;
import com.yb.board.Entity.CommentEntity;
import com.yb.board.dto.BoardDto;
import com.yb.board.dto.CommentDto;
import com.yb.board.repository.BoardRepository;
import com.yb.board.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public int saveComment(Long boardId, CommentDto commentDto){
        CommentEntity commentEntity = commentDto.toEntity();
        if(!boardRepository.findById(boardId).isPresent()){
            log.error("존재하지 않는 게시물 접근");
            return -1;
        }
        commentEntity.setBoardEntity(boardRepository.findById(boardId).get());

        commentRepository.save(commentEntity);
        log.info("commentEntity : {}, save finish", commentEntity);
        return 0;
    }

    @Transactional
    public List<CommentDto> findComments(Long boardId){

        List<CommentEntity> commentEntityList = commentRepository.searchCommentsByBoardId(boardRepository.findById(boardId).get());
        log.info("{}", commentEntityList);
        List<CommentDto> commentDtoList = new ArrayList<>();

        if(commentEntityList.isEmpty())
            return commentDtoList;

        for(CommentEntity commentEntity: commentEntityList){
            commentDtoList.add(this.convertEntityToDto(commentEntity));
        }

        log.info("commentDtoList : {}", commentDtoList);

        return commentDtoList;
    }

    private CommentDto convertEntityToDto(CommentEntity commentEntity) {
        return CommentDto.builder()
                .id(commentEntity.getId())
                .nickname(commentEntity.getNickname())
                .password(commentEntity.getPassword())
                .comment(commentEntity.getComment())
                .createdDate(commentEntity.getCreatedDate())
                .build();
    }
}
