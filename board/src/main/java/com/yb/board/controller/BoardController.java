package com.yb.board.controller;

import com.yb.board.dto.BoardDto;
import com.yb.board.dto.CommentDto;
import com.yb.board.service.BoardService;
import com.yb.board.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;
    private CommentService commentService;

    @GetMapping(value = "/")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum){
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);


        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        return "board/list.html";
    }

    @GetMapping(value = "/post")
    public String write(){
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto){
        boardService.savePost(boardDto);

        return "redirect:/";
    }

    @GetMapping(value = "/post/{postId}")
    public String detail(@PathVariable("postId") long postId, Model model){
        BoardDto boardDto = boardService.getPost(postId);
        List<CommentDto> commentDtoList = commentService.findComments(postId);
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("commentDtoList", commentDtoList);
        return "board/detail.html";
    }

    @GetMapping(value = "/post/edit/{postId}")
    public String edit(@PathVariable("postId") long postId, Model model){
        BoardDto boardDto = boardService.getPost(postId);
        model.addAttribute("boardDto", boardDto);
        return "board/update.html";
    }

    @PutMapping("/post/edit/{postId}")
    public String update(BoardDto boardDto){
        boardService.savePost(boardDto);

        return "redirect:/";
    }

    @DeleteMapping("/post/{postId}")
    public String delete(@PathVariable("postId") long postId){
        boardService.deletePost(postId);

        return "redirect:/";
    }

    @GetMapping("/board/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model){
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/list.html";
    }




}
