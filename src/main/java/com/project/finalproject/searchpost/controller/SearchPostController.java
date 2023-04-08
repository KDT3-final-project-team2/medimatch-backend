package com.project.finalproject.searchpost.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.searchpost.dto.PostReqDTO;
import com.project.finalproject.searchpost.dto.PostResDTO;
import com.project.finalproject.searchpost.service.SearchPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SearchPostController {

    private final SearchPostService searchPostService;

    /**
     * 공고 조건 검색
     */
    @GetMapping("/jobposts/search/{type}")
    public Page<PostResDTO> searchPosts(@PathVariable String type,
                                   @RequestBody PostReqDTO reqDTO,
                                   @PageableDefault Pageable pageable){
        return searchPostService.searchPost(type, reqDTO, pageable);
    }

    /**
     * 전체 공고 조회
     */
    @GetMapping("/jobposts/posts")
    public Page<PostResDTO> allPosts(@PageableDefault Pageable pageable){
        return searchPostService.allPosts(pageable);
    }

    /**
     * 상세 공고 조회
     */
    @GetMapping("/jobposts/posts/{postId}")
    public ResponseDTO detailPost(@PathVariable Long postId){

        return searchPostService.detailPost(postId);
    }
}
