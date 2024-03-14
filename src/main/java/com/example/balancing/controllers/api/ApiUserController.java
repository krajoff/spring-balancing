package com.example.balancing.controllers.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "The Users API")
@RestController
@RequestMapping("/api/users")
public class ApiUserController {
}
