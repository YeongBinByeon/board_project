package com.yb.board.controller;

import com.yb.board.Entity.CommentEntity;
import com.yb.board.dto.CommentDto;
import com.yb.board.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/comment")
    public String writeHandlerFormRequest(@RequestParam("boardId") Long boardId, CommentDto commentDto, Model model) {
        log.info("boardId = {}, commentDto = {}", boardId, commentDto.toString());
        commentService.saveComment(boardId, commentDto);
        return "redirect:/post/"+boardId;

    }
}
