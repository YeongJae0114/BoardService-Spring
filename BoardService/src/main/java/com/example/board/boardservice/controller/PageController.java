package com.example.board.boardservice.controller;

import com.example.board.boardservice.dto.PostDto;
import com.example.board.boardservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final PostService postService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("posts", postService.getAllPosts());
        return "index";
    }

    @GetMapping("/posts/new")
    public String PostForm(Model model){
        model.addAttribute("postDto", new PostDto());
        return "post_form";
    }

    @PostMapping("/posts/new")
    public String NewPost(PostDto postDto){
        postService.create(postDto);
        return "redirect:/";
    }



}
