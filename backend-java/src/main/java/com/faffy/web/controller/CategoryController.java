package com.faffy.web.controller;

import com.faffy.web.jpa.entity.FashionCategory;
import com.faffy.web.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;

    @GetMapping("")
    public ResponseEntity getAllCategories() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            List<FashionCategory> categories = categoryService.getAllCategories();
            resultMap.put("content", categories);
        } catch(Exception e) {
            resultMap.put("msg","카테고리 목록 요청 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(resultMap,status);
    }
}
