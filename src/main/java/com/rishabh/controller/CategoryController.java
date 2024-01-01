package com.rishabh.controller;
import com.rishabh.service.CategoryService;
import com.rishabh.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryServiceImpl;

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryServiceImpl.getAllCategories();
    }

    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category){
        return categoryServiceImpl.addNewCategory(category);
    }

    @GetMapping("/getbyid{id}")
    public Category getCategoryById(@PathVariable Integer categoryId){
        return categoryServiceImpl.getCategoryById(categoryId);
    }

    @GetMapping("/getbyname{categoryName}")
    public List<Category> getCategoryByName(@PathVariable String categoryName){
        return categoryServiceImpl.getCategoryByName(categoryName);
    }

    @PutMapping("/update/{id}")
    public Category updateCategory(@RequestBody Category category, @PathVariable Integer categoryId){
        return categoryServiceImpl.updateCategory(category, categoryId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategoryById(@PathVariable Integer categoryId){
        categoryServiceImpl.deleteCategoryById(categoryId);
    }



}
