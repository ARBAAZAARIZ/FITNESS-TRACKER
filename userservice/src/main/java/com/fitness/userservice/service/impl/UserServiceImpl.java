package com.fitness.userservice.service.impl;


import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import com.fitness.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;



     public UserResponse register(RegisterRequest request){

         UserResponse response = new UserResponse();

         if(userRepository.existsByEmail(request.getEmail())){

             response.setApiStatus(false);
             response.setMessage("Email already exist");

             return response;
         }



         User user = new User();
         user.setEmail(request.getEmail());
         user.setPassword(request.getPassword());
         user.setFirstName(request.getFirstName());
         user.setLastName(request.getLastName());

       User savedUser = userRepository.save(user);

       if(savedUser.getId()!=null){

           response.setApiStatus(true);
           response.setMessage("User registered successfully");
           return response;
       }else {
           response.setApiStatus(false);
           response.setMessage("User registration failed");
           return response;
       }

     }

    public UserResponse getUserProfile(String userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("User not found")
        );
        return  convertToResponse(user);
    }




    private UserResponse convertToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setRole(user.getRole());
        response.setCreateAt(user.getCreateAt());
        response.setUpdateAt(user.getUpdateAt());
        response.setApiStatus(true);
        response.setMessage("User found successfully");

        return response;
    }




}
