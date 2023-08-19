package phbc.diabeticregistry.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phbc.diabeticregistry.models.UserModel;
import phbc.diabeticregistry.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserModel userModel) {
        boolean susCardExists = userRepository.findBySusCard(userModel.getSusCard()).isPresent();
        boolean cpfExists = userRepository.findByCpf(userModel.getCpf()).isPresent();
        boolean rgExists = userRepository.findByRg(userModel.getRg()).isPresent();
        if (susCardExists || cpfExists || rgExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already registered");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List> findAll() {
        List<UserModel> userList = userRepository.findAll();
        if (!userList.isEmpty()) {
            for (UserModel user : userList) {
                String id = user.getSusCard();
                user.add(linkTo(methodOn(UserController.class).findUser(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @GetMapping("/user/{susCard}")
    public ResponseEntity<Object> findUser(@PathVariable(value = "susCard") String susCard) {
        boolean userExists = userRepository.findById(susCard).isPresent();
        if (!userExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not registered");
        } else {
            var userModel = userRepository.findById(susCard).get();
            userModel.add(linkTo(methodOn(UserController.class).findAll()).withRel("Users list"));
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

    @DeleteMapping("/user/{susCard}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "susCard") String susCard) {
        boolean userExists = userRepository.findById(susCard).isPresent();
        if (!userExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not registered");
        } else {
            var userModel = userRepository.findById(susCard).get();
            userRepository.delete(userModel);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User deleted");
        }
    }

    @PutMapping("/user/{susCard}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "susCard") String susCard, @RequestBody @Valid UserModel oldUser) {
        boolean userExists = userRepository.findById(susCard).isPresent();
        if (!userExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF not registered");
        } else {
            var updatedUser = userRepository.findById(susCard).get();
            BeanUtils.copyProperties(oldUser, updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(updatedUser));
        }
    }
}

