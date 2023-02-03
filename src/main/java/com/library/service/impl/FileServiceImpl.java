package com.library.service.impl;

import com.library.dao.FileDataRepository;
import com.library.dto.FileDataDTO;
import com.library.model.FileData;
import com.library.service.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private ModelMapper mapper;

    private final String FOLDER_PATH = "C:\\Users\\GAURSAH\\Desktop\\MyFiles\\";

    @Override
    public FileDataDTO uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));
        FileDataDTO fileDataDTO = mapper.map(fileData, FileDataDTO.class);

        if (fileData != null) {
            return fileDataDTO;
        }
        return null;
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}
