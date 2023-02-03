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
public class PublisherDTO {

    public int publisherId;

    @NotBlank(message = "Publisher name cannot be empty")
    @Size(min = 3, max = 50, message = "Publisher name must be between 3 and 50 characters")
    public String publisherName;

    private FileDataDTO image;

    private Set<BookDTO> books = new HashSet<>();
}
