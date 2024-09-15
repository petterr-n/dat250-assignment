package no.peron.demo.manager;

import no.peron.demo.model.Poll;
import no.peron.demo.model.User;
import no.peron.demo.model.VoteOption;
import no.peron.demo.model.Vote;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PollManager {

    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Poll> polls = new HashMap<>();

    // AtomicLongs for generating unique IDs
    private final AtomicLong userIdCounter = new AtomicLong(0);
    private final AtomicLong pollIdCounter = new AtomicLong(0);
    private final AtomicLong voteOptionIdCounter = new AtomicLong(0);
    private final AtomicLong voteIdCounter = new AtomicLong(0);

    public Collection<Poll> getPolls() {
        return polls.values();
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public void saveUser(User user) {
        if (user.getUserId() == null) {
            user.setUserId(userIdCounter.incrementAndGet()); // Generate a new unique ID
        }
        users.put(user.getUserId(), user);
    }

    public Long getNextVoteId() {
        return voteIdCounter.incrementAndGet();
    }

    public Optional<Poll> getPoll(Long id) {
        return Optional.ofNullable(polls.get(id));
    }

    public void savePoll(Poll poll) {
        if (poll.getPollId() == null) {
            poll.setPollId(pollIdCounter.incrementAndGet()); // Generate a new unique Poll ID
        }
        for (VoteOption option : poll.getOptions()) {
            if (option.getOptionId() == null) {
                option.setOptionId(voteOptionIdCounter.incrementAndGet()); // Generate a new unique VoteOption ID
            }
        }
        polls.put(poll.getPollId(), poll);
    }

    public void deleteUser(Long id) {
        User user = users.get(id);
        if (user != null) {
            // Remove user's votes and update option vote counts
            user.getVotes().forEach(vote -> {
                VoteOption option = vote.getOption();
                if (option != null) {
                    option.decrementVotes();
                }
            });
            users.remove(id);
        }
    }

    public void deletePoll(Long id) {
        Poll poll = polls.get(id);
        if (poll != null) {
            // Remove associated VoteOptions
            poll.getOptions().forEach(option -> {
                option.setPoll(null); // Clear the poll reference
            });
            polls.remove(id);
        }
    }

    public boolean userExist(Long id) {
        return users.containsKey(id);
    }

    public boolean pollExist(Long id) {
        return polls.containsKey(id);
    }

    public VoteOption findVoteOptionById(Poll poll, Long optionId) {
        return poll.getOptions().stream()
                .filter(option -> option.getOptionId().equals(optionId))
                .findFirst()
                .orElse(null);
    }

    public Vote findVoteById(Poll poll, Long voteId) {
        return poll.getVotes().stream()
                .filter(vote -> vote.getVoteId().equals(voteId))
                .findFirst()
                .orElse(null);
    }

    public VoteOption getVoteOption(Long pollId, Long optionId) {
        Poll poll = polls.get(pollId);
        if (poll != null) {
            return findVoteOptionById(poll, optionId);
        }
        return null;
    }

//    public void saveVote(Vote vote) {
//        // Implement logic to save the vote if you use a persistence layer.
//    }
//
//    public void saveVoteOption(VoteOption voteOption) {
//        // Implement logic to save the vote option if you use a persistence layer.
//    }
}
