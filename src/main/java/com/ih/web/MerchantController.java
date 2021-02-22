package com.ih.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ih.enums.MerchantRole;
import com.ih.enums.MerchantStatus;
import com.ih.helper.MerchantHelper;
import com.ih.message.ResponseMessage;
import com.ih.model.Merchant;
import com.ih.model.Transaction;
import com.ih.service.MerchantImportServiceImpl;
import com.ih.service.MerchantService;
import com.ih.service.SecurityService;
import com.ih.validator.MerchantEditValidator;
import com.ih.validator.MerchantValidator;

@Controller
public class MerchantController {

    private String fileLocation;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    MerchantImportServiceImpl merchantImportService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MerchantValidator merchantValidator;

    @Autowired
    private MerchantEditValidator merchantEditValidator;

    @Autowired
    MerchantHelper merchantHelper;

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

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (merchantHelper.hasCSVFormat(file)) {
            try {
                merchantImportService.saveAll(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

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
        Merchant merchant = merchantService.findByMerchantId(merchantId);
        modelAndView.addObject("merchant", merchant);
        modelAndView.addObject("roleList", MerchantRole.values());
        modelAndView.addObject("statusList", MerchantStatus.values());

        return modelAndView;
    }

    @PostMapping(path = "/merchant/merchantEdit/{id}", params = "save")
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

    @PostMapping(path = "/merchant/merchantEdit/{id}", params = "cancel")
    public String cancelMerchantEdit(HttpServletRequest request) {
        return "redirect:/merchant/merchantList";
    }

    @GetMapping("/merchant/merchantDelete/{id}")
    public String deleteMerchant(@PathVariable(name = "id") Long merchantId, final ModelMap model) {

        Merchant merchant = merchantService.findByMerchantId(merchantId);
        if (merchant.getTransactionList() != null && merchant.getTransactionList().size() > 0) {
            model.addAttribute("error", "Merchant " + merchant.getName() + " can't be deleted, because is referenced from transaction/s.");
        } else {
            model.addAttribute("message", "Merchant " + merchant.getName() + " has been deleted successfully.");
            merchantService.delete(merchant);
        }
        List<Merchant> merchantList = merchantService.findAll();
        model.addAttribute("merchantList", merchantList);

        return "redirect:/merchant/merchantList";
    }

//    @PostMapping("/api/merchant/upload")
//    public String uploadFile(Model model, MultipartFile file) throws IOException {
//        InputStream in = file.getInputStream();
//        File currDir = new File(".");
//        String path = currDir.getAbsolutePath();
//        fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
//        FileOutputStream f = new FileOutputStream(fileLocation);
//        int ch = 0;
//        while ((ch = in.read()) != -1) {
//            f.write(ch);
//        }
//        f.flush();
//        f.close();
//        model.addAttribute("message", "File: " + file.getOriginalFilename()
//            + " has been uploaded successfully!");
//        return "/welcome";
//    }
}
