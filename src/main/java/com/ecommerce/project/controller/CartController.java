package com.ecommerce.project.controller;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.DTO.CartDTO;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.service.CartService;
import com.ecommerce.project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public String addProductToCart(@PathVariable Long productId,
                                   @PathVariable Integer quantity, Model model) {
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        model.addAttribute("cart", cartDTO);
        return "cart/cart-detail"; // Assuming you have cart-detail.jsp in WEB-INF/jsp/cart
    }

    @GetMapping("/carts")
    public String getCarts(Model model) {
        List<CartDTO> cartDTOs = cartService.getAllCarts();
        model.addAttribute("carts", cartDTOs);
        return "cart/cart-list"; // Assuming you have cart-list.jsp in WEB-INF/jsp/cart
    }

    @GetMapping("/carts/users/cart")
    public String getCartById(Model model) {
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDTO cartDTO = cartService.getCart(emailId, cartId);
        model.addAttribute("cart", cartDTO);
        return "cart/cart-detail"; // Assuming you have cart-detail.jsp in WEB-INF/jsp/cart
    }

    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public String updateCartProduct(@PathVariable Long productId,
                                    @PathVariable String operation, Model model) {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);

        model.addAttribute("cart", cartDTO);
        return "cart/cart-detail"; // Assuming you have cart-detail.jsp in WEB-INF/jsp/cart
    }

    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public String deleteProductFromCart(@PathVariable Long cartId,
                                        @PathVariable Long productId, Model model) {
        String status = cartService.deleteProductFromCart(cartId, productId);
        model.addAttribute("message", status);
        return "cart/cart-detail"; // Assuming you have cart-detail.jsp in WEB-INF/jsp/cart
    }
}
