package com.faffy.web.service;

import com.faffy.web.jpa.entity.FashionCategory;

import java.util.List;

public interface CategoryService {
    List<FashionCategory> getAllCategories();
}
