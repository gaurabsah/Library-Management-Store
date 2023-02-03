package com.library.service;

import com.library.dto.AuthorDTO;
import com.library.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {
    PublisherDTO createPublisher(PublisherDTO publisherDTO);

    PublisherDTO updatePublisher(PublisherDTO publisherDTO, int publisherId);

    PublisherDTO getPublisherById(int publisherId);

    void deletePublisherById(int publisherId);

    List<PublisherDTO> getAllPublisher();

    PublisherDTO updatePublisherWithImage(int publisherId, int imageId);
}
