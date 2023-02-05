package com.library.controller;

import com.library.dto.AuthorDTO;
import com.library.dto.BookDTO;
import com.library.dto.FileDataDTO;
import com.library.service.AuthorService;
import com.library.service.BookService;
import com.library.service.FileService;
import com.library.util.ImageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private FileService fileService;

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO author = authorService.createAuthor(authorDTO);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PutMapping("/update/{authorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AuthorDTO> updateAuthor(@Valid @RequestBody AuthorDTO authorDTO, @PathVariable int authorId) {
        AuthorDTO author = authorService.updateAuthor(authorDTO, authorId);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{authorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAuthor(@PathVariable int authorId) {
        authorService.deleteAuthor(authorId);
        return new ResponseEntity<>("Author deleted", HttpStatus.OK);
    }

    @GetMapping("/get/{authorId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable int authorId) {
        AuthorDTO author = authorService.getAuthor(authorId);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Iterable<AuthorDTO>> getAllAuthors() {
        Iterable<AuthorDTO> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/search/{authorName}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<AuthorDTO>> searchAuthors(@PathVariable String authorName) {
        List<AuthorDTO> authors = authorService.searchAuthors(authorName);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping("/uploadImage/{authorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ImageResponse> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file, @PathVariable int authorId) throws IOException {
        FileDataDTO uploadImage = fileService.uploadImageToFileSystem(file);
        int id = uploadImage.getId();
        authorService.updateAuthorWithImage(authorId, id);
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(file.getOriginalFilename())
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Image uploaded successfully!!!")
                .build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getImage/{fileName}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = fileService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/{authorId}/addBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> createBookWithAuthor(@Valid @RequestBody BookDTO bookDTO, @PathVariable int authorId) {
        BookDTO bookWithCategory = bookService.createBookWithAuthor(bookDTO, authorId);
        return new ResponseEntity<>(bookWithCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{authorId}/updateBook/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> updateBookWithCategory(@PathVariable int bookId, @PathVariable int authorId) {
        BookDTO bookDTO = bookService.updateBookWithAuthor(bookId, authorId);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping("/{authorId}/getBook")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<BookDTO>> getBookByPublisherId(@PathVariable int authorId) {
        List<BookDTO> bookByPublisher = bookService.getBookByAuthor(authorId);
        return new ResponseEntity<>(bookByPublisher, HttpStatus.OK);
    }
}
