package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.extra.AppVariables;
import org.hashids.Hashids;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class FileService {

    private final Hashids hashids;

    public FileService() {
        this.hashids = new Hashids(getClass().getName(), 8);
    }

    public byte[] convert(File file) throws IOException {
        final FileInputStream inputStream = new FileInputStream(file);
        byte[] arr = new byte[(int) file.length()];
        inputStream.read(arr);
        inputStream.close();
        return arr;
    }

    public String saveFile(MultipartFile file) throws IOException {
        final File path = new File(AppVariables.PATH + hashids.encode(new Date().getTime()) + "." + getFileExtension(file.getOriginalFilename()));
        file.transferTo(path);
        return path.getAbsolutePath();
    }

    public byte[] imageBytes(String path) throws IOException {
        final File file = new File(path);
        return convert(file);
    }

    public String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        return filename.substring(lastIndex + 1);
    }

    public String getFileName(String absolutePath){
        int lastIndex = absolutePath.lastIndexOf("/");
        return absolutePath.substring(lastIndex);
    }

}

