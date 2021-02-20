package com.ih.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ih.enums.MerchantRole;
import com.ih.model.Merchant;
import com.ih.model.Transaction;
import com.ih.service.MerchantService;
import com.ih.service.SecurityService;
import com.ih.validator.MerchantValidator;

@Controller
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MerchantValidator merchantValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("merchantForm", new Merchant());
        model.addAttribute("roleList", MerchantRole.values());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("merchantForm") Merchant merchantForm, BindingResult bindingResult) {
        merchantValidator.validate(merchantForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        merchantService.save(merchantForm);

        securityService.autoLogin(merchantForm.getUsername(), merchantForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }

    @GetMapping("/merchant/merchantList")
    public String merchantList(Model model) {
        List<Merchant> merchantList = merchantService.findAll();
        model.addAttribute("merchantList", merchantList);

        return "/merchant/merchantList";
    }
}
