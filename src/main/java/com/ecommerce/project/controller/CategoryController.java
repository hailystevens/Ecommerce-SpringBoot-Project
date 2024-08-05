package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.DTO.CategoryDTO;
import com.ecommerce.project.DTO.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public String getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
            Model model) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        model.addAttribute("categories", categoryResponse);
        return "category/category-list"; // Assuming you have category-list.jsp in WEB-INF/jsp/category
    }

    @PostMapping("/public/categories")
    public String createCategory(@Valid @RequestBody CategoryDTO categoryDTO, Model model){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        model.addAttribute("category", savedCategoryDTO);
        return "category/category-detail"; // Assuming you have category-detail.jsp in WEB-INF/jsp/category
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId, Model model){
        CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
        model.addAttribute("category", deletedCategory);
        return "category/category-detail"; // Assuming you have category-detail.jsp in WEB-INF/jsp/category
    }

    @PutMapping("/public/categories/{categoryId}")
    public String updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                 @PathVariable Long categoryId, Model model){
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        model.addAttribute("category", savedCategoryDTO);
        return "category/category-detail"; // Assuming you have category-detail.jsp in WEB-INF/jsp/category
    }
}
