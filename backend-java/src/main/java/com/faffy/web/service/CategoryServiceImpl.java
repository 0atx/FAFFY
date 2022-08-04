package com.faffy.web.service;

import com.faffy.web.jpa.entity.FashionCategory;
import com.faffy.web.jpa.repository.FashionCategoryRepository;
import com.faffy.web.jpa.repository.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    FashionCategoryRepository fashionCategoryRepository;


    @Override
    public List<FashionCategory> getAllCategories() {
        return fashionCategoryRepository.findAll();
    }
}
