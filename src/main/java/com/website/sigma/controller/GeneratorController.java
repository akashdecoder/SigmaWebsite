package com.website.sigma.controller;

import com.website.sigma.model.User;
import com.website.sigma.service.ExcelSheetGenerator;
import com.website.sigma.service.FirebaseService;
import com.website.sigma.service.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@Controller
public class GeneratorController {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @Autowired
    private ExcelSheetGenerator excelSheetGenerator;


    @RequestMapping(value = "/generatePDF", method = RequestMethod.GET)
    public String generatePDF() throws Exception {

        List<User> users = firebaseService.getAllUsers();

        for(User user: users) {
            String html = pdfGeneratorService.parseThymeleafTemplate(user);
            pdfGeneratorService.generatePDFFromHTML(user, html);
        }

        System.out.println("PDFs generated");

        return "redirect:/recruitments";
    }

    @RequestMapping(value = "/generateSheet", method = RequestMethod.GET)
    public String generateSheet() throws IOException {
        List<User> users = firebaseService.getAllUsers();

        excelSheetGenerator.generateRecruitmentSheet(users);

        System.out.println("Generated Sheet");

        return "redirect:/recruitments";
    }
}
