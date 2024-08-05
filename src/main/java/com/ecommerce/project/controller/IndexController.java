package com.ecommerce.project.controller;

import com.ecommerce.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private CartService cartService;

    private void addCartItemCount(Model model) {
        int cartItemCount = cartService.getCartItemCount(); // Method to get the cart item count
        model.addAttribute("cartItemCount", cartItemCount);
    }

    @GetMapping("/")
    public String homePage(Model model) {
        addCartItemCount(model);
        model.addAttribute("title", "Home Page");
        model.addAttribute("message", "Welcome to our E-commerce Application!");
        return "index"; // This refers to index.html in the templates directory
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        addCartItemCount(model);
        model.addAttribute("title", "About Us");
        model.addAttribute("message", "Learn more about our company.");
        return "about"; // This refers to about.html in the templates directory
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        addCartItemCount(model);
        model.addAttribute("title", "Contact Us");
        model.addAttribute("message", "Get in touch with us.");
        return "contact"; // This refers to contact.html in the templates directory
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model) {
        addCartItemCount(model);
        // Add other necessary attributes for the checkout page
        return "checkout"; // This refers to checkout.html in the templates directory
    }

    @GetMapping("/orders")
    public String ordersPage(Model model) {
        addCartItemCount(model);
        // Add other necessary attributes for the orders page
        return "orders"; // This refers to orders.html in the templates directory
    }

    @GetMapping("/account/loginPageUrl")
    public String loginPage(Model model) {
        addCartItemCount(model);
        return "login"; // This refers to login.html in the templates directory
    }

    @GetMapping("/account/logout")
    public String logoutPage() {
        // Handle logout logic here
        return "redirect:/"; // Redirect to home page after logout
    }
}
