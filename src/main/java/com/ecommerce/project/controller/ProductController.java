package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.DTO.ProductResponse;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public String addProduct(@Valid @RequestBody ProductDTO productDTO,
                             @PathVariable Long categoryId, Model model){
        ProductDTO savedProductDTO = productService.addProduct(categoryId, productDTO);
        model.addAttribute("product", savedProductDTO);
        return "product/product-detail"; // Assuming you have product-detail.jsp in WEB-INF/jsp/product
    }

    @GetMapping("/public/products")
    public String getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
            Model model){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        model.addAttribute("products", productResponse);
        return "product/product-list"; // Assuming you have product-list.jsp in WEB-INF/jsp/product
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public String getProductsByCategory(@PathVariable Long categoryId,
                                        @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                        @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                        @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                        @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
                                        Model model){
        ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        model.addAttribute("products", productResponse);
        return "product/product-list"; // Assuming you have product-list.jsp in WEB-INF/jsp/product
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public String getProductsByKeyword(@PathVariable String keyword,
                                       @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                       @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                       @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                       @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
                                       Model model){
        ProductResponse productResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        model.addAttribute("products", productResponse);
        return "product/product-list"; // Assuming you have product-list.jsp in WEB-INF/jsp/product
    }

    @PutMapping("/admin/products/{productId}")
    public String updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                @PathVariable Long productId, Model model){
        ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO);
        model.addAttribute("product", updatedProductDTO);
        return "product/product-detail"; // Assuming you have product-detail.jsp in WEB-INF/jsp/product
    }

    @DeleteMapping("/admin/products/{productId}")
    public String deleteProduct(@PathVariable Long productId, Model model){
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        model.addAttribute("product", deletedProduct);
        return "product/product-detail"; // Assuming you have product-detail.jsp in WEB-INF/jsp/product
    }

    @PutMapping("/products/{productId}/image")
    public String updateProductImage(@PathVariable Long productId,
                                     @RequestParam("image") MultipartFile image, Model model) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        model.addAttribute("product", updatedProduct);
        return "product/product-detail"; // Assuming you have product-detail.jsp in WEB-INF/jsp/product
    }
}
