package foxtrot.uniform.charlie.kilo.controller;

import foxtrot.uniform.charlie.kilo.model.dto.UserResponseDto;
import foxtrot.uniform.charlie.kilo.service.UserService;
import foxtrot.uniform.charlie.kilo.service.implementation.dto.UserResponseMapperToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserResponseMapperToDto userResponseMapper;

    @Autowired
    public UserController(UserService userService, UserResponseMapperToDto userResponseMapper) {
        this.userService = userService;
        this.userResponseMapper = userResponseMapper;
    }

    @GetMapping("/by-email")
    public UserResponseDto getByEmail(@RequestParam String email) {
        return userResponseMapper.toDto(userService.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Can't find user with email " + email)));
    }
}
