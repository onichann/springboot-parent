package com.wt.springboot.controller;

import com.wt.springboot.pojo.swagger.Pet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v2.0")
public class SwaggerController {

    @PostMapping("/pet")
    public void addPet(@RequestBody Pet pet){
        System.out.println(pet);
    }
}
