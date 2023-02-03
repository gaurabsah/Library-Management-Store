package com.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private int bookId;

    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;

    @NotBlank(message = "Book name cannot be blank")
    @Size(min = 3, max = 100, message = "Book name should be between 3 and 100")
    private String bookName;

    @NotBlank(message = "Book description cannot be blank")
    @Size(min = 3, max = 1000, message = "Book description should between 3 and 1000")
    private String description;

    private FileDataDTO image;

    private AuthorDTO author;

    private CategoryDTO category;

    private PublisherDTO publisher;
}
