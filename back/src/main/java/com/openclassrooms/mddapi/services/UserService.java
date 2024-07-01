package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public void createUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.error("User email {} already exists in DB", request.getEmail());
            throw new EntityExistsException("User email already exists");
        }
        if(userRepository.existsByUsername(request.getUsername())) {
            logger.error("User username {} already exists in DB", request.getUsername());
            throw new EntityExistsException("User username already exists");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(newUser);
    }

    public void updateUser(UserDto userDto) {
        User newUser = userMapper.toEntity(userDto);
        Optional<User> dbUser = userRepository.findById(userDto.getId());

        if(!dbUser.isPresent()) {
            logger.error("User {} does not exist in db", newUser.getId());
            throw new EntityNotFoundException("User does not exist in db");
        }

        User existingDbUser = dbUser.get();

        existingDbUser.setUsername(newUser.getUsername());
        existingDbUser.setEmail(newUser.getEmail());
        existingDbUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(existingDbUser);
    }

    public UserDto findById(Long id){
        User user = this.userRepository.findById(id).orElse(null);

        if(user != null) {
            return userMapper.toDto(user);
        }

        return null;
    }
}
