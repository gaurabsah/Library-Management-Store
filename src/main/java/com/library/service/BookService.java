package com.library.service;

import com.library.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO createBook(BookDTO bookDTO);

    BookDTO updateBook(BookDTO bookDTO, int bookId);

    BookDTO getBookById(int bookId);

    List<BookDTO> getAllBooks();

    void deleteBookById(int bookId);

    List<BookDTO> getBookByBookName(String bookName);

    BookDTO updateBookWithImage(int bookId, int imageId);

    BookDTO createBookWithCategory(BookDTO bookDTO, int categoryId);

    BookDTO updateBookWithCategory(int bookId, int categoryId);

    List<BookDTO> getBookByCategory(int categoryId);

    BookDTO createBookWithAuthor(BookDTO bookDTO, int categoryId);

    BookDTO updateBookWithAuthor(int bookId, int categoryId);

    BookDTO createBookWithPublisher(BookDTO bookDTO, int publisherId);

    BookDTO updateBookWithPublisher(int bookId, int publisherId);

    List<BookDTO> getBookByPublisher(int publisherId);

    List<BookDTO> getBookByAuthor(int authorId);
}
