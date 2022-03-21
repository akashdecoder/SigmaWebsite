package com.website.sigma.service;

import com.website.sigma.model.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.FileSystems;

@Service
public class PDFGeneratorService {

    public void generatePDFFromHTML(User user, String html) throws Exception {
        String outputFolder = "";

        String xHtml = convertToXHtml(html);

        if(user.getYear().equals("1st Year")) {
            outputFolder= "/Users/personal/IdeaProjects/SigmaWebsite/src/main/resources/generatedPDFs/first_years" + File.separator + user.getUsn() + "_" + user.getFirstName() + user.getLastName() +".pdf";
        }

        if(user.getYear().equals("2nd Year")) {
            outputFolder= "/Users/personal/IdeaProjects/SigmaWebsite/src/main/resources/generatedPDFs/second_years" + File.separator + user.getUsn() + "_" + user.getFirstName() + user.getLastName() +".pdf";
        }

        OutputStream outputStream = new FileOutputStream(outputFolder);

        String baseUrl = FileSystems.getDefault().getPath("src", "main", "resources").toUri().toURL().toString();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

    public String parseThymeleafTemplate(User user)
    {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("name", user.getFirstName() + " " + user.getLastName());
        context.setVariable("usn", user.getUsn());
        context.setVariable("branch", user.getBranch());
        context.setVariable("year", user.getYear());
        context.setVariable("email", user.getEmail());
        context.setVariable("contact",user.getContact());
        context.setVariable("pLanguage",user.getPLanguage());
        context.setVariable("groups",user.getGroups());
        context.setVariable("github",user.getGithub());
        context.setVariable("linkedin",user.getLinkedin());
        context.setVariable("why",user.getWhy());
        context.setVariable("whys", user.getWhys());
        context.setVariable("about", user.getAbout());
        context.setVariable("specialization", user.getSpecialization());
        context.setVariable("interest", user.getInterest());
        context.setVariable("fileurl", user.getFileUrl());


        return templateEngine.process("user_template", context);
    }

    public String convertToXHtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        tidy.parseDOM(byteArrayInputStream, byteArrayOutputStream);

        return byteArrayOutputStream.toString("UTF-8");
    }
}
