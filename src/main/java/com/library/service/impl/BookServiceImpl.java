package com.library.service.impl;

import com.library.dao.*;
import com.library.dto.BookDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.model.*;
import com.library.service.BookService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = mapper.map(bookDTO, Book.class);
        Book savedBook = bookRepository.save(book);
        BookDTO savedBookDTO = mapper.map(savedBook, BookDTO.class);
        logger.info("Book saved successfully");
        return savedBookDTO;
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO, int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book Not Found with id: " + bookId));
        book.setBookName(bookDTO.getBookName());
        book.setIsbn(bookDTO.getIsbn());
        book.setDescription(bookDTO.getDescription());
        Book updatedBook = bookRepository.save(book);
        BookDTO updatedBookDTO = mapper.map(updatedBook, BookDTO.class);
        logger.info("Book updated successfully");
        return updatedBookDTO;

    }

    @Override
    public BookDTO getBookById(int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book Not Found with id: " + bookId));
        BookDTO bookDTO = mapper.map(book, BookDTO.class);
        logger.info("Book found successfully");
        return bookDTO;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTO = books.stream().map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());
        logger.info("Books found successfully");
        return bookDTO;
    }

    @Override
    public void deleteBookById(int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book Not Found with id: " + bookId));
        bookRepository.delete(book);
        logger.info("Book deleted successfully");

    }

    @Override
    public List<BookDTO> getBookByBookName(String bookName) {
        List<Book> book = bookRepository.findByBookNameContainingIgnoreCase(bookName).orElseThrow(() -> new ResourceNotFoundException("Book Not Found with bookName: " + bookName));
        List<BookDTO> bookDTO = book.stream().map(book1 -> mapper.map(book1, BookDTO.class)).collect(Collectors.toList());
        logger.info("Books found successfully");
        return bookDTO;
    }

    @Override
    public BookDTO updateBookWithImage(int bookId, int imageId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book Not Found with id: " + bookId));
        FileData fileData = fileDataRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image Not Found with id: " + imageId));
        book.setImage(fileData);
        Book savedBook = bookRepository.save(book);
        BookDTO bookDTO = mapper.map(savedBook, BookDTO.class);
        return bookDTO;
    }

    @Override
    public BookDTO createBookWithCategory(BookDTO bookDTO, int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Found with id: " + categoryId));
        Book book = mapper.map(bookDTO, Book.class);
        book.setCategory(category);
        Book savedBook = bookRepository.save(book);
        BookDTO dto = mapper.map(savedBook, BookDTO.class);
        return dto;
    }

    @Override
    public BookDTO updateBookWithCategory(int bookId, int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Found with id: " + categoryId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book Not Found with id: " + bookId));
        book.setCategory(category);
        Book savedBook = bookRepository.save(book);
        BookDTO dto = mapper.map(savedBook, BookDTO.class);
        return dto;
    }

    @Override
    public List<BookDTO> getBookByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Found with id: " + categoryId));
        List<Book> bookList = bookRepository.findByCategory(category).orElseThrow(() -> new ResourceNotFoundException("Category Not Found "));
        List<BookDTO> bookDTOList = bookList.stream().map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());
        return bookDTOList;
    }

    @Override
    public BookDTO createBookWithAuthor(BookDTO bookDTO, int authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author Not Found with id: " + authorId));
        Book book = mapper.map(bookDTO, Book.class);
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);
        BookDTO dto = mapper.map(savedBook, BookDTO.class);
        return dto;
    }

    @Override
    public BookDTO updateBookWithAuthor(int bookId, int authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author Not Found with id: " + authorId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book Not Found with id: " + bookId));
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);
        BookDTO dto = mapper.map(savedBook, BookDTO.class);
        return dto;
    }

    @Override
    public BookDTO createBookWithPublisher(BookDTO bookDTO, int publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new ResourceNotFoundException("Author Not Found with id: " + publisherId));
        Book book = mapper.map(bookDTO, Book.class);
        book.setPublisher(publisher);
        Book savedBook = bookRepository.save(book);
        BookDTO dto = mapper.map(savedBook, BookDTO.class);
        return dto;
    }

    @Override
    public BookDTO updateBookWithPublisher(int bookId, int publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new ResourceNotFoundException("Author Not Found with id: " + publisherId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book Not Found with id: " + bookId));
        book.setPublisher(publisher);
        Book savedBook = bookRepository.save(book);
        BookDTO dto = mapper.map(savedBook, BookDTO.class);
        return dto;
    }

    @Override
    public List<BookDTO> getBookByPublisher(int publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new ResourceNotFoundException("Publisher Not Found with id: " + publisherId));
        List<Book> bookList = bookRepository.findByPublisher(publisher).orElseThrow(() -> new ResourceNotFoundException("Book Not Found "));
        List<BookDTO> bookDTOList = bookList.stream().map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());
        return bookDTOList;
    }

    @Override
    public List<BookDTO> getBookByAuthor(int authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author Not Found with id: " + authorId));
        List<Book> bookList = bookRepository.findByAuthor(author).orElseThrow(() -> new ResourceNotFoundException("Book Not Found "));
        List<BookDTO> bookDTOList = bookList.stream().map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());
        return bookDTOList;
    }

}
