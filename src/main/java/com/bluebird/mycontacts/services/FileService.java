package com.bluebird.mycontacts.services;

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

    final String PATH = "/Users/tursunali/IdeaProjects/mycontacts/src/main/resources/storage";

    public byte[] convert(File file) throws IOException {
        final FileInputStream inputStream = new FileInputStream(file);
        byte[] arr = new byte[(int) file.length()];
        inputStream.read(arr);
        inputStream.close();
        return arr;
    }

    public byte[] saveFile(MultipartFile file) throws IOException {
        final File path = new File(PATH + "/" + hashids.encode(new Date().getTime()) + "." + getFileExtension(file.getOriginalFilename()));
        file.transferTo(path);
//        int stIndex = path.getAbsolutePath().lastIndexOf("\\");
        return convert(path);
    }

    public String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        return filename.substring(lastIndex + 1);
    }

}

