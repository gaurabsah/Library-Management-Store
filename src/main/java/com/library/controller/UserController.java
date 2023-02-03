package com.library.controller;

import com.library.dto.FileDataDTO;
import com.library.dto.UserDTO;
import com.library.service.FileService;
import com.library.service.UserService;
import com.library.util.ImageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO userDTO1 = userService.createUser(userDTO);
        return new ResponseEntity<>(userDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable int userId) {
        UserDTO userDTO1 = userService.updateUser(userDTO, userId);
        return new ResponseEntity<>(userDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int userId) {
        UserDTO userDTO = userService.getUser(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOList = userService.getAllUsers();
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<UserDTO>> searchUser(@PathVariable String name) {
        List<UserDTO> userDTOList = userService.searchUser(name);
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<UserDTO> searchUserByEmail(@PathVariable String email) {
        UserDTO userDTO = userService.searchUserByEmail(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/uploadUserImage/{userId}")
    public ResponseEntity<ImageResponse> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file, @PathVariable int userId) throws IOException {
        FileDataDTO uploadImage = fileService.uploadImageToFileSystem(file);
        int imageId = uploadImage.getId();
        userService.updateUserWithImage(imageId, userId);
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(file.getOriginalFilename())
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Image uploaded successfully!!!")
                .build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getUserImage/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = fileService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

}
