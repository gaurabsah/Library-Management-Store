package com.library.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FileDataDTO {

    private int id;

    private String name;

    private String type;

    private String filePath;
}
