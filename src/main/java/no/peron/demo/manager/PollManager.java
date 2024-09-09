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
        if (user.getId() == null) {
            user.setId(userIdCounter.incrementAndGet()); // Generate a new unique ID
        }
        users.put(user.getId(), user);
    }

    public Optional<Poll> getPoll(Long id) {
        return Optional.ofNullable(polls.get(id));
    }

    public void savePoll(Poll poll) {
        if (poll.getId() == null) {
            poll.setId(pollIdCounter.incrementAndGet()); // Generate a new unique ID
        }
        polls.put(poll.getId(), poll);
    }

    public void deleteUser(Long id) {
        users.remove(id);
    }

    public void deletePoll(Long id) {
        polls.remove(id);
    }

    public boolean userExist(Long id) {
        return users.containsKey(id);
    }

    public boolean pollExist(Long id) {
        return polls.containsKey(id);
    }

    public VoteOption findVoteOptionById(Poll poll, Long optionId) {
        return poll.getOptions().stream()
                .filter(option -> option.getId().equals(optionId))
                .findFirst()
                .orElse(null);
    }

    public Vote findVoteById(User user, Long voteId) {
        return user.getVotes().stream()
                .filter(vote -> vote.getId().equals(voteId))
                .findFirst()
                .orElse(null);
    }
}
