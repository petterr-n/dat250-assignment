package no.peron.demo.controller;

import no.peron.demo.manager.PollManager;
import no.peron.demo.model.Poll;
import no.peron.demo.model.Vote;
import no.peron.demo.model.VoteOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class VoteController {

    @Autowired
    private PollManager pollManager;

    @PostMapping("/polls/{pollId}/votes")
    public ResponseEntity<Vote> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
        // Check if the poll exists
        Optional<Poll> pollOptional = pollManager.getPoll(pollId);
        if (pollOptional.isEmpty()) {
            return ResponseEntity.notFound().build();  // Return 404 if the poll is not found
        }

        Poll poll = pollOptional.get();

        // Retrieve the associated VoteOption
        VoteOption voteOption = pollManager.findVoteOptionById(poll, vote.getOption().getOptionId());
        if (voteOption == null) {
            return ResponseEntity.badRequest().body(null);  // Return 400 if the option is not found
        }

        // Increment the votes for the selected option
        voteOption.incrementVotes();
        pollManager.savePoll(poll);  // Persist the updated poll with new vote count

        // Set the vote ID and timestamp
        vote.setVoteId(pollManager.getNextVoteId());
        vote.setPublishedAt(Instant.now());
        vote.setOption(voteOption);

        // Save the vote (ensure you are actually saving it in your database)
        // pollManager.saveVote(vote);

        return ResponseEntity.ok(vote);  // Return the persisted vote
    }




    @DeleteMapping("/polls/{pollId}/votes/{voteId}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long pollId, @PathVariable Long voteId) {
        // Retrieve the poll
        Optional<Poll> pollOptional = pollManager.getPoll(pollId);
        if (pollOptional.isEmpty()) {
            return ResponseEntity.notFound().build();  // Return 404 if the poll is not found
        }

        Poll poll = pollOptional.get();

        // Find the vote to remove
        Vote voteToRemove = pollManager.findVoteById(poll, voteId);
        if (voteToRemove == null) {
            return ResponseEntity.notFound().build();  // Return 404 if the vote is not found
        }

        // Find the option and decrement the vote count
        VoteOption voteOption = pollManager.findVoteOptionById(poll, voteToRemove.getOption().getOptionId());
        if (voteOption != null) {
            voteOption.decrementVotes();
            //pollManager.saveVoteOption(voteOption); // Persist the updated vote option with decreased votes
        }

        // Remove the vote
        poll.getVotes().remove(voteToRemove);
        pollManager.savePoll(poll);

        return ResponseEntity.ok().build();  // Return 200 OK if deletion is successful
    }
}
