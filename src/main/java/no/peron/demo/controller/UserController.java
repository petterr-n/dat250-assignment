package no.peron.demo.controller;

import no.peron.demo.manager.PollManager;
import no.peron.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PollManager pollManager;

    @GetMapping
    public Collection<User> getAllUsers() {
        return pollManager.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = pollManager.getUser(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        pollManager.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = pollManager.getUser(id);
        if(userOptional.isEmpty()) return ResponseEntity.notFound().build();

        User updatedUser = userOptional.get();
        updatedUser.setUsername(user.getUsername());
        updatedUser.setEmail(user.getEmail());
        pollManager.saveUser(updatedUser);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!pollManager.userExist(id)) return ResponseEntity.notFound().build();

        pollManager.deleteUser(id);
        return ResponseEntity.ok().build();
    }



}