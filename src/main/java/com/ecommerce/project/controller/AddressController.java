package com.ecommerce.project.controller;

import com.ecommerce.project.DTO.AddressDTO;
import com.ecommerce.project.model.User;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtil authUtil;

    @GetMapping
    public String getAllAddresses(Model model) {
        List<AddressDTO> addresses = addressService.getAddresses();
        model.addAttribute("addresses", addresses);
        return "address-list"; // Corresponds to address-list.html
    }

    @GetMapping("/new")
    public String showAddressForm(Model model) {
        model.addAttribute("address", new AddressDTO());
        return "address-form"; // Corresponds to address-form.jsp
    }

    @PostMapping("/save")
    public String saveAddress(@ModelAttribute("address") AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        addressService.createAddress(addressDTO, user);
        return "redirect:/addresses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        AddressDTO address = addressService.getAddressesById(id);
        model.addAttribute("address", address);
        return "address-form"; // Reusing the address-form.jsp for edit
    }

    @PostMapping("/update/{id}")
    public String updateAddress(@PathVariable("id") Long id, @ModelAttribute("address") AddressDTO addressDTO) {
        addressService.updateAddress(id, addressDTO);
        return "redirect:/addresses";
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
        return "redirect:/addresses";
    }
}
