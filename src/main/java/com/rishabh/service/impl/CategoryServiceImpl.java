package com.rishabh.service.impl;
import com.rishabh.entity.Category;
import com.rishabh.repository.CategoryRepository;
import com.rishabh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;


    @Override
    public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryById(Integer categoryId){
        return categoryRepo.findById(categoryId).get();
    }

    @Override
    public Category addNewCategory(Category category){
        return categoryRepo.save(category);
    }

    @Override
    public void deleteCategoryById(Integer categoryId){
        categoryRepo.deleteById(categoryId);
    }

    @Override
    public Category updateCategory(Category category, Integer categoryId){
        Category existingCategory = categoryRepo.findById(categoryId).get();
        existingCategory.setCategoryName(category.getCategoryName());
        return categoryRepo.save(existingCategory);
    }

    @Override
    public List<Category> getCategoryByName(String name){
        return categoryRepo.findByCategoryNameContainingIgnoreCase(name);
    }

}
