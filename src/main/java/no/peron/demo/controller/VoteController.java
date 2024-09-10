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
    public ResponseEntity<List<Vote>> getVotes(@PathVariable Long id) {
        Optional<User> user = pollManager.getUser(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Vote> votes = user.get().getVotes();
        if (votes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(votes);
    }

    @PostMapping
    public ResponseEntity<Vote> createVote(@PathVariable Long id, @RequestBody Vote vote) {
        Optional<User> userOptional = pollManager.getUser(id);
        if(userOptional.isEmpty()) return ResponseEntity.badRequest().build();

        User user = userOptional.get();

        vote.setVoteId(pollManager.getNextVoteId());

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
        Vote voteToRemove = pollManager.findVoteById(user, voteId);

        if (voteToRemove != null) {
            user.getVotes().remove(voteToRemove);
            pollManager.saveUser(user);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}