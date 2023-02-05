package com.library.controller;

import com.library.dto.BookDTO;
import com.library.dto.FileDataDTO;
import com.library.dto.PublisherDTO;
import com.library.service.BookService;
import com.library.service.FileService;
import com.library.service.PublisherService;
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
@RequestMapping("/api/publisher")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @Autowired
    private FileService fileService;

    @Autowired
    private BookService bookService;

    @PostMapping("/addPublisher")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PublisherDTO> createPublisher(@Valid @RequestBody PublisherDTO publisherDTO) {
        PublisherDTO publisherDTO1 = publisherService.createPublisher(publisherDTO);
        return new ResponseEntity<>(publisherDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/updatePublisher/{publisherId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PublisherDTO> updatePublisher(@Valid @RequestBody PublisherDTO publisherDTO, @PathVariable int publisherId) {
        PublisherDTO publisherDTO1 = publisherService.updatePublisher(publisherDTO, publisherId);
        return new ResponseEntity<>(publisherDTO1, HttpStatus.OK);
    }

    @GetMapping("/getPublisher/{publisherId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable int publisherId) {
        PublisherDTO publisherDTO = publisherService.getPublisherById(publisherId);
        return new ResponseEntity<>(publisherDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{publisherId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deletePublisherById(@PathVariable int publisherId) {
        publisherService.deletePublisherById(publisherId);
        return new ResponseEntity<>("Publisher deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/getAllPublisher")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<PublisherDTO>> getAllPublisher() {
        List<PublisherDTO> publisherDTO = publisherService.getAllPublisher();
        return new ResponseEntity<>(publisherDTO, HttpStatus.OK);
    }

    @PostMapping("/uploadImage/{publisherId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ImageResponse> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file, @PathVariable int publisherId) throws IOException {
        FileDataDTO uploadImage = fileService.uploadImageToFileSystem(file);
        int imageId = uploadImage.getId();
        publisherService.updatePublisherWithImage(publisherId, imageId);
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

    @PostMapping("/{publisherId}/addBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> createBookWithPublisher(@Valid @RequestBody BookDTO bookDTO, @PathVariable int publisherId) {
        BookDTO bookWithPublisher = bookService.createBookWithPublisher(bookDTO, publisherId);
        return new ResponseEntity<>(bookWithPublisher, HttpStatus.CREATED);
    }

    @PutMapping("/{publisherId}/updateBook/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> updateBookWithPublisher(@PathVariable int bookId, @PathVariable int publisherId) {
        BookDTO bookWithPublisher = bookService.updateBookWithPublisher(bookId, publisherId);
        return new ResponseEntity<>(bookWithPublisher, HttpStatus.OK);
    }

    @GetMapping("/{publisherId}/getBook")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<BookDTO>> getBookByPublisherId(@PathVariable int publisherId) {
        List<BookDTO> bookByPublisher = bookService.getBookByPublisher(publisherId);
        return new ResponseEntity<>(bookByPublisher, HttpStatus.OK);
    }


}
