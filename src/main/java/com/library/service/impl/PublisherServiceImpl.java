package com.library.service.impl;

import com.library.dao.FileDataRepository;
import com.library.dao.PublisherRepository;
import com.library.dto.AuthorDTO;
import com.library.dto.FileDataDTO;
import com.library.dto.PublisherDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.model.FileData;
import com.library.model.Publisher;
import com.library.service.PublisherService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PublisherServiceImpl.class);

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Override
    public PublisherDTO createPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = mapper.map(publisherDTO, Publisher.class);
        Publisher savedPublisher = publisherRepository.save(publisher);
        PublisherDTO publisherDTO1 = mapper.map(savedPublisher, PublisherDTO.class);
        logger.info("Publisher saved successfully");
        return publisherDTO1;
    }

    @Override
    public PublisherDTO updatePublisher(PublisherDTO publisherDTO, int publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + publisherId));
        publisher.setPublisherName(publisherDTO.getPublisherName());
        Publisher savedPublisher = publisherRepository.save(publisher);
        PublisherDTO savedPublisherDTO = mapper.map(savedPublisher, PublisherDTO.class);
        logger.info("Publisher updated successfully");
        return savedPublisherDTO;
    }

    @Override
    public PublisherDTO getPublisherById(int publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + publisherId));
        PublisherDTO publisherDTO = mapper.map(publisher, PublisherDTO.class);
        logger.info("Publisher found successfully");
        return publisherDTO;
    }

    @Override
    public void deletePublisherById(int publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + publisherId));
        publisherRepository.delete(publisher);
        logger.info("Publisher deleted successfully");
    }

    @Override
    public List<PublisherDTO> getAllPublisher() {
        List<Publisher> all = publisherRepository.findAll();
        List<PublisherDTO> publisherDTOS = all.stream().map(publisher -> mapper.map(publisher, PublisherDTO.class)).collect(Collectors.toList());
        logger.info("Publisher found successfully");
        return publisherDTOS;
    }

    @Override
    public PublisherDTO updatePublisherWithImage(int publisherId, int imageId) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: "));
        FileData fileData = fileDataRepository.findById(publisherId).orElseThrow(() -> new ResourceNotFoundException("FileData not found with id: " + publisherId));
        publisher.setImage(fileData);
        Publisher savedPublisher = publisherRepository.save(publisher);
        PublisherDTO publisherDTO = mapper.map(savedPublisher, PublisherDTO.class);
        return publisherDTO;
    }
}
