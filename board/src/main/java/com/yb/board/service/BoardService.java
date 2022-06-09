package com.yb.board.service;

import com.yb.board.Entity.BoardEntity;
import com.yb.board.dto.BoardDto;
import com.yb.board.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class BoardService {
    private BoardRepository boardRepository;
    
    private static final int BLOCK_PAGE_NUM_COUNT = 100; // 블럭에 존재하는 페이지
    private static final int PAGE_POST_COUNT = 4; // 한 페이지에 존재하는 게시글 수
    

    @Transactional
    public Long savePost(BoardDto boardDto){
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    public List<BoardDto> getBoardlist(Integer pageNum) {
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));

        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntities){
            BoardDto boardDto = this.convertEntityToDto(boardEntity);
            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    public BoardDto getPost(long postId){
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(postId);
        if(boardEntityWrapper.isPresent()){
            BoardEntity boardEntity = boardEntityWrapper.get();
            BoardDto boardDto = this.convertEntityToDto(boardEntity);
            return boardDto;
        }else{
            return null;
        }
    }

    @Transactional
    public void deletePost(long postId) {
        boardRepository.deleteById(postId);
    }

    public List<BoardDto> searchPosts(String keyword) {
        List<BoardEntity> boardEntityList = boardRepository.searchPostsByKeyword(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();
        // 추후 modelmapper 사용하여 더 간단하게 만들 예정

        if(boardEntityList.isEmpty())
            return boardDtoList;

        for(BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }

        log.info("boardEntityList : {}", boardDtoList);

        return boardDtoList;
    }

    private BoardDto convertEntityToDto(BoardEntity boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

// 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

// 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

// 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

// 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

// 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }
}
