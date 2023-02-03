package com.library.controller;

import com.library.dto.FileDataDTO;
import com.library.service.BookService;
import com.library.dto.BookDTO;
import com.library.service.FileService;
import com.library.util.ImageResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private FileService fileService;

    @PostMapping("/create")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO newBook = bookService.createBook(bookDTO);
        logger.info("Book created: {}", newBook.getBookId());
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}/update")
    public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO bookDTO, @PathVariable int bookId) {
        BookDTO updatedBook = bookService.updateBook(bookDTO, bookId);
        logger.info("Book updated: {}", updatedBook.getBookId());
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int bookId) {
        BookDTO bookDTO = bookService.getBookById(bookId);
        logger.info("Book found: {}", bookDTO.getBookId());
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        logger.info("Books found: {}", books.size());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBookById(@PathVariable int bookId) {
        bookService.deleteBookById(bookId);
        logger.info("Book deleted: {}", bookId);
        return new ResponseEntity<>("Book deleted", HttpStatus.OK);
    }

    @GetMapping("/search/{bookName}")
    public ResponseEntity<List<BookDTO>> getBookByBookName(@PathVariable String bookName) {
        List<BookDTO> bookDTO = bookService.getBookByBookName(bookName);
        logger.info("Book found: {}", bookDTO.size());
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PostMapping("/uploadImage/{bookId}")
    public ResponseEntity<ImageResponse> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file, @PathVariable int bookId) throws IOException {
        FileDataDTO uploadImage = fileService.uploadImageToFileSystem(file);
        int id = uploadImage.getId();
        bookService.updateBookWithImage(bookId,id);
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(file.getOriginalFilename())
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Image uploaded successfully!!!")
                .build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getImage/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = fileService.downloadImageFromFileSystem(fileName);
        logger.info("Image downloaded Successfully");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }


}
