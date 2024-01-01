package com.rishabh.service;
import com.rishabh.entity.Category;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Integer categoryId);

    Category addNewCategory(Category category);

    void deleteCategoryById(Integer categoryId);

    Category updateCategory(Category category, Integer categoryId);

    List<Category> getCategoryByName(String name);
}
