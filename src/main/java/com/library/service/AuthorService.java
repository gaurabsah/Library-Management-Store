package com.library.service;

import com.library.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO createAuthor(AuthorDTO authorDTO);

    AuthorDTO updateAuthor(AuthorDTO authorDTO, int authorId);

    void deleteAuthor(int authorId);

    AuthorDTO getAuthor(int authorId);

    Iterable<AuthorDTO> getAllAuthors();

    List<AuthorDTO> searchAuthors(String authorName);

    AuthorDTO updateAuthorWithImage(int authorId, int imageId);
}
