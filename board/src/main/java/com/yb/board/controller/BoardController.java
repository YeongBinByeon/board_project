package com.yb.board.controller;

import com.yb.board.Entity.BoardEntity;
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

    @GetMapping(value = "/post/view/{postId}")
    public String detail(@PathVariable("postId") long postId, Model model){
        BoardDto boardDto = boardService.getPostDto(postId);
        List<CommentDto> commentDtoList = commentService.findComments(postId);
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("commentDtoList", commentDtoList);
        return "board/detail.html";
    }

    @GetMapping(value = "/post/modify/{postId}")
    public String editPrepared(@PathVariable("postId") long postId, Model model){
        model.addAttribute("postId", postId);
        return "board/board_edit_password.html";
    }

    @GetMapping(value = "/post/edit/{postId}")
    public String edit(@PathVariable("postId") long postId,@RequestParam("password") String password, Model model){
        BoardDto boardDto = boardService.modifyPost(postId, password);

        if(boardDto == null){
//            return "redirect:/post/view/"+postId;
            model.addAttribute("message","비밀번호가 일치하지 않습니다.");
            //model.addAttribute("searchUrl","");
            return "board/board_edit_password.html";
        }

        model.addAttribute("boardDto", boardDto);
        return "board/update.html";
    }

    @PutMapping("/post/edit/{postId}")
    public String update(@PathVariable("postId") long postId, BoardDto boardDto, Model model){
        boardService.savePost(boardDto);

        model.addAttribute("message", "수정이 완료되었습니다.");
        return "redirect:/post/view/"+postId;
    }

    @GetMapping(value = "/post/delete/{postId}")
    public String deletePrepared(@PathVariable("postId") long postId, Model model){
        model.addAttribute("postId", postId);
        return "board/board_delete_password.html";
    }

    @DeleteMapping("/post/delete/{postId}")
    public String delete(@PathVariable("postId") long postId, @RequestParam("password") String password, Model model){
        boolean deleteCheckFlag = boardService.deletePost(postId, password);
        if(deleteCheckFlag == false){
            model.addAttribute("message","비밀번호가 일치하지 않습니다.");
            //model.addAttribute("searchUrl","");
            return "board/board_delete_password.html";
        }
        model.addAttribute("message","게시글 삭제가 성공하였습니다.");
        return "board/list.html";
    }

    @GetMapping("/board/search")
        public String search(@RequestParam(value = "keyword") String keyword, Model model){
            List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/list.html";
    }




}
