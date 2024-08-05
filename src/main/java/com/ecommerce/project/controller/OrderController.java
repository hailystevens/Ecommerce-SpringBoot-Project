package com.ecommerce.project.controller;

import com.ecommerce.project.DTO.OrderDTO;
import com.ecommerce.project.DTO.OrderRequestDTO;
import com.ecommerce.project.service.OrderService;
import com.ecommerce.project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/order/users/payments/{paymentMethod}")
    public String orderProducts(@PathVariable String paymentMethod,
                                @RequestBody OrderRequestDTO orderRequestDTO,
                                Model model) {
        String emailId = authUtil.loggedInEmail();
        OrderDTO order = orderService.placeOrder(
                emailId,
                orderRequestDTO.getAddressId(),
                paymentMethod,
                orderRequestDTO.getPgName(),
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgResponseMessage()
        );
        model.addAttribute("order", order);
        return "order/order-detail"; // Assuming you have order-detail.jsp in WEB-INF/jsp/order
    }
}
