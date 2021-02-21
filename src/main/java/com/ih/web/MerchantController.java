package com.ih.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ih.enums.MerchantRole;
import com.ih.enums.MerchantStatus;
import com.ih.model.Merchant;
import com.ih.model.Transaction;
import com.ih.service.MerchantService;
import com.ih.service.SecurityService;
import com.ih.validator.MerchantEditValidator;
import com.ih.validator.MerchantValidator;

@Controller
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MerchantValidator merchantValidator;

    @Autowired
    private MerchantEditValidator merchantEditValidator;

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

    @RequestMapping("/merchant/merchantEdit/{id}")
    public ModelAndView showMerchantEditPage(@PathVariable(name = "id") Long merchantId) {
        ModelAndView modelAndView = new ModelAndView("/merchant/merchantEdit");
        Merchant merchant = merchantService.findByMerchantId( merchantId );
        modelAndView.addObject("merchant", merchant);
        modelAndView.addObject("roleList", MerchantRole.values());
        modelAndView.addObject("statusList", MerchantStatus.values());

        return modelAndView;
    }

    @PostMapping( path = "/merchant/merchantEdit/{id}", params = "save")
    public String saveMerchantEdit(@Valid @ModelAttribute("merchant") final Merchant merchant,
        final BindingResult bindingResult, final ModelMap model) {
        merchantEditValidator.validate(merchant, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("roleList", MerchantRole.values());
            model.addAttribute("statusList", MerchantStatus.values());
            return "/merchant/merchantEdit";
        }
        merchantService.edit(merchant);
        return "redirect:/merchant/merchantList";
    }

    @PostMapping( path = "/merchant/merchantEdit/{id}", params = "cancel")
    public String cancelMerchantEdit(HttpServletRequest request) {
        return "redirect:/merchant/merchantList";
    }

    @GetMapping("/merchant/merchantDelete/{id}")
    public String deleteMerchant(@PathVariable(name = "id") Long merchantId, final ModelMap model) {


        Merchant merchant = merchantService.findByMerchantId( merchantId );
        if (merchant.getTransactionList() != null && merchant.getTransactionList().size() > 0){
            model.addAttribute("error", "Merchant " + merchant.getName() + " can't be deleted, because is referenced from transaction/s.");
        } else {
            model.addAttribute("message", "Merchant " + merchant.getName() + " has been deleted successfully.");
            merchantService.delete(merchant);
        }
        List<Merchant> merchantList = merchantService.findAll();
        model.addAttribute("merchantList", merchantList);

        return "redirect:/merchant/merchantList";
    }
}
