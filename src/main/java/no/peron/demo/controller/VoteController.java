package no.peron.demo.controller;

import no.peron.demo.manager.PollManager;
import no.peron.demo.model.User;
import no.peron.demo.model.Vote;
import no.peron.demo.model.VoteOption;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/{id}/votes")
public class VoteController {
    @Autowired
    private PollManager pollManager;

    @GetMapping
    public List<Vote> getVotes(@PathVariable Long id) {
        Optional<User> user = pollManager.getUser(id);
        return user.map(User::getVotes).orElse(new ArrayList<>());
    }

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestParam Long id, @RequestBody Vote vote) {
        Optional<User> userOptional = pollManager.getUser(id);
        if(userOptional.isEmpty()) return ResponseEntity.badRequest().build();

        User user = userOptional.get();
        vote.setPublishedAt(Instant.now());
        user.getVotes().add(vote);
        pollManager.saveUser(user);

        return ResponseEntity.ok(vote);
    }


    @DeleteMapping("/{voteId}")
    public ResponseEntity<Vote> deleteVote(@PathVariable Long id, @PathVariable Long voteId) {
        Optional<User> userOptional = pollManager.getUser(id);
        if(userOptional.isEmpty()) return ResponseEntity.notFound().build();

        User user = userOptional.get();
        if (user.getVotes().removeIf(vote -> vote.getId().equals(voteId))) {
            pollManager.saveUser(user);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}