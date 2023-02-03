package com.library.service;

import com.library.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, int userId);

    void deleteUser(int userId);

    UserDTO getUser(int userId);

    List<UserDTO> getAllUsers();

    List<UserDTO> searchUser(String name);

    UserDTO searchUserByEmail(String email);

    UserDTO updateUserWithImage(int imageId, int userId);
}
