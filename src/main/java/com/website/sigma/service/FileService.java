package com.website.sigma.service;

import com.website.sigma.model.FileUser;
import com.website.sigma.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileService {

    private static String TEMP_URL = "";

    @Autowired
    private FirebaseService firebaseService;

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String upload(MultipartFile multipartFile, User user) throws IOException{
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = user.getUsn().concat(this.getExtension(fileName));
            File file = this.convertToFile(multipartFile, fileName);
            TEMP_URL = firebaseService.uploadFile(file, fileName);
            file.delete();
            return TEMP_URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Hello";
        }
    }
}
