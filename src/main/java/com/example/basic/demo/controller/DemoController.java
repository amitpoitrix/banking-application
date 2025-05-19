package com.example.basic.demo.controller;

import com.example.basic.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class DemoController {
    @GetMapping("/get")
    public String getString() {
        return "Hello from controller";
    }

    // Handle POST RequestBody
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        String result = "User created with name: " + user.getName() + " with age: " + user.getAge();
        return ResponseEntity.ok(result);
    }

    // Handle GET with PathVariable
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable("id") int id) {
        return "Fetching user with id: " + id;
    }

    // Handle GET with RequestParam (Query Parameter)
    @GetMapping("/search")
    public String searchUser(@RequestParam("name") String name, @RequestParam(value = "age", required = false) Integer age) {
        return "found name: " + name + " with age: " + (age != null ? age: "Not provided");
    }
}
