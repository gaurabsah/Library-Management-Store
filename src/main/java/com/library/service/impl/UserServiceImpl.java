package com.library.service.impl;

import com.library.dao.FileDataRepository;
import com.library.dao.UserRepository;
import com.library.dto.UserDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.model.FileData;
import com.library.model.User;
import com.library.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        UserDTO savedUserDTO = mapper.map(savedUser, UserDTO.class);
        logger.info("User saved successfully");
        return savedUserDTO;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        user.setGender(userDTO.getGender());
        User savedUser = userRepository.save(user);
        UserDTO userDTO1 = mapper.map(savedUser, UserDTO.class);
        logger.info("User updated successfully");
        return userDTO1;
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
        logger.info("User deleted successfully");
    }

    @Override
    public UserDTO getUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        logger.info("User fetched successfully");
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = userList.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
        logger.info("Users fetched successfully");
        return userDTOList;
    }

    @Override
    public List<UserDTO> searchUser(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<UserDTO> userDTOList = users.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
        logger.info("Users fetched successfully");
        return userDTOList;
    }

    @Override
    public UserDTO searchUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        logger.info("User fetched successfully");
        return userDTO;
    }

    @Override
    public UserDTO updateUserWithImage(int imageId, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        FileData fileData = fileDataRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("File not found"));
        user.setImage(fileData);
        User savedUser = userRepository.save(user);
        UserDTO userDTO = mapper.map(savedUser, UserDTO.class);
        return userDTO;
    }
}
