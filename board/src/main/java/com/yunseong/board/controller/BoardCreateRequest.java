package com.yunseong.board.controller;

import com.yunseong.board.domain.BoardCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BoardCreateRequest {

    private String subject;
    private String content;
    private BoardCategory category;
}