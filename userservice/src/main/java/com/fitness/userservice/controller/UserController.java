package com.fitness.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/users")
public class UserController {



    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId){
        return ResponseEntity.ok("");
    }


    @GetMapping("/register")
    public ResponseEntity<?> getUserProfile(@RequestBody String Dto){
        return ResponseEntity.ok("");
    }



}
