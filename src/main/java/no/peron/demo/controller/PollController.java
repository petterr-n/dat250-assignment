package no.peron.demo.controller;

import no.peron.demo.manager.PollManager;
import no.peron.demo.model.Poll;
import no.peron.demo.model.User;
import no.peron.demo.model.VoteOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollManager pollManager;

    @GetMapping
    public Collection<Poll> getAllPolls() {
        return pollManager.getPolls();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long id) {
        Optional<Poll> poll = pollManager.getPoll(id);
        return poll.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        if (poll.getOptions().isEmpty()) return ResponseEntity.badRequest().build();

        for (VoteOption option : poll.getOptions()) {
            option.setPoll(poll);
        }

        User creator = poll.getCreator();
        creator.getCreatedPolls().add(poll);

        pollManager.savePoll(poll);
        return ResponseEntity.ok(poll);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Poll> updatePoll(@PathVariable Long id, @RequestBody Poll poll) {
        Optional<Poll> pollOptional = pollManager.getPoll(id);
        if (pollOptional.isEmpty()) return ResponseEntity.notFound().build();

        Poll updatedPoll = pollOptional.get();
        updatedPoll.setQuestion(poll.getQuestion());
        updatedPoll.setPublishedAt(poll.getPublishedAt());
        updatedPoll.setValidUntil(poll.getValidUntil());
        pollManager.savePoll(updatedPoll);

        return ResponseEntity.ok(updatedPoll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable Long id) {
        if (pollManager.getPoll(id).isEmpty()) return ResponseEntity.notFound().build();

        pollManager.deletePoll(id);
        return ResponseEntity.noContent().build();
    }
}