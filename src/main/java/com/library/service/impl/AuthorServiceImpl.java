package com.library.service.impl;

import com.library.dao.AuthorRepository;
import com.library.dao.FileDataRepository;
import com.library.dto.AuthorDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Author;
import com.library.model.FileData;
import com.library.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = mapper.map(authorDTO, Author.class);
        Author savedAuthor = authorRepository.save(author);
        AuthorDTO savedAuthorDTO = mapper.map(savedAuthor, AuthorDTO.class);
        logger.info("Author created: {}", savedAuthorDTO.getId());
        return savedAuthorDTO;
    }

    @Override
    public AuthorDTO updateAuthor(AuthorDTO authorDTO, int authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        author.setAuthorName(authorDTO.getName());
        author.setDescription(authorDTO.getDescription());
        Author savedAuthor = authorRepository.save(author);
        AuthorDTO savedAuthorDTO = mapper.map(savedAuthor, AuthorDTO.class);
        logger.info("Author updated: {}", savedAuthorDTO.getId());
        return savedAuthorDTO;
    }

    @Override
    public void deleteAuthor(int authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        authorRepository.delete(author);
        logger.info("Author deleted: {}", authorId);
    }

    @Override
    public AuthorDTO getAuthor(int authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        AuthorDTO authorDTO = mapper.map(author, AuthorDTO.class);
        logger.info("Author found: {}", authorDTO.getId());
        return authorDTO;
    }

    @Override
    public Iterable<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOS = authors.stream().map(author -> mapper.map(author, AuthorDTO.class)).collect(Collectors.toList());
        logger.info("Authors found: {}", authorDTOS.size());
        return authorDTOS;
    }

    @Override
    public List<AuthorDTO> searchAuthors(String authorName) {
        List<Author> authors = authorRepository.findByAuthorNameContainingIgnoreCase(authorName).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        List<AuthorDTO> authorDTOS = authors.stream().map(author -> mapper.map(author, AuthorDTO.class)).collect(Collectors.toList());
        logger.info("Authors found: {}", authorDTOS.size());
        return authorDTOS;
    }

    @Override
    public AuthorDTO updateAuthorWithImage(int authorId, int imageId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        FileData fileData = fileDataRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
        author.setImage(fileData);
        Author savedAuthor = authorRepository.save(author);
        AuthorDTO authorDTO = mapper.map(savedAuthor, AuthorDTO.class);
        return authorDTO;
    }
}
