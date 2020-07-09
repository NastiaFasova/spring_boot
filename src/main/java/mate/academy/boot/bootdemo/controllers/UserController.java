package mate.academy.boot.bootdemo.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mate.academy.boot.bootdemo.model.User;
import mate.academy.boot.bootdemo.model.dto.UserDto;
import mate.academy.boot.bootdemo.model.mapper.UserMapper;
import mate.academy.boot.bootdemo.service.ReviewService;
import mate.academy.boot.bootdemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(ReviewService reviewService, UserService userService,
                          UserMapper userMapper) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public void add(@RequestBody @Valid UserDto userDto) {
        userService.save(userMapper.getUser(userDto));
    }

    @GetMapping("/most-active/{/limit}")
    public List<UserDto> getMostActiveUsers(@PathVariable int limit) {
        List<User> users = reviewService.getMostActiveUsers(limit);
        return users.stream()
                .map(userMapper::getUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.getUserDto(userService.findById(id).orElseThrow());
    }

    @GetMapping
    public List<UserDto> getUsers() {
        List<User> users = userService.findAll();
        return users.stream()
                .map(userMapper::getUserDto)
                .collect(Collectors.toList());
    }
}