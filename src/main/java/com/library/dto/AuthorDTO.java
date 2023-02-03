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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {

    private int id;

    @NotBlank(message = "Author name cannot be blank")
    @Size(min = 3, max = 100, message = "Author name should be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Author description cannot be blank")
    @Size(min = 3, max = 1000, message = "Author description should be between 3 and 1000 characters")
    private String description;

    private FileDataDTO image;

    private Set<BookDTO> books = new HashSet<>();
}
