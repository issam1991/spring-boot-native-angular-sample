package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@SpringBootApplication
public class NativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NativeApplication.class, args);
    }

}

@RestController
@Tag(name = "Application API", description = "Main application endpoints for user management and status")
class HelloController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Welcome message", description = "Returns a welcome message from the application")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved welcome message")
    })
    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Native!";
    }

    @Operation(summary = "Application status", description = "Check if the application is running")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Application is running")
    })
    @GetMapping("/api/status")
    public String status() {
        return "Application is running successfully!";
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    })
    @GetMapping("/api/users")
    public java.util.List<User> getUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user count", description = "Get the total number of users in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user count")
    })
    @GetMapping("/api/users/count")
    public String getUserCount() {
        return "Total users: " + userService.getUserCount();
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create new user", description = "Create a new user in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or user already exists")
    })
    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try {
            User user = userService.createUser(userRequest.getName(), userRequest.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Update user", description = "Update an existing user's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID of the user to update") @PathVariable Long id,
            @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest.getName(), userRequest.getEmail())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete user", description = "Delete a user from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

class UserRequest {
    private String name;
    private String email;

    public UserRequest() {
    }

    public UserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
