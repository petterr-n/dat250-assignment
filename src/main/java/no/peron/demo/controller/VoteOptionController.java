package no.peron.demo.controller;

import no.peron.demo.manager.PollManager;
import no.peron.demo.model.Poll;
import no.peron.demo.model.VoteOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/polls/{pollId}/voteOptions")
public class VoteOptionController {

    @Autowired
    private PollManager pollManager;

    @GetMapping
    public List<VoteOption> getOptions(@PathVariable Long pollId) {
        Optional<Poll> pollOptional = pollManager.getPoll(pollId);
        return pollOptional.map(Poll::getOptions).orElse(new ArrayList<>());
    }

    @PostMapping
    public ResponseEntity<VoteOption> createOption(@PathVariable Long pollId, @RequestBody VoteOption option) {
        Optional<Poll> pollOptional = pollManager.getPoll(pollId);
        if (pollOptional.isEmpty()) return ResponseEntity.notFound().build();

        Poll poll = pollOptional.get();
        poll.getOptions().add(option);
        pollManager.savePoll(poll);

        return ResponseEntity.ok(option);
    }

    @DeleteMapping("/{voteOptionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long pollId, @PathVariable Long voteOptionId) {
        Optional<Poll> pollOptional = pollManager.getPoll(pollId);
        if (pollOptional.isEmpty()) return ResponseEntity.notFound().build();

        Poll poll = pollOptional.get();
        if (poll.getOptions().removeIf(option -> option.getOptionId().equals(voteOptionId))) {
            pollManager.savePoll(poll);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }




}
