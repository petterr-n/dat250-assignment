package no.peron.demo;

import no.peron.demo.model.Poll;
import no.peron.demo.model.User;
import no.peron.demo.model.Vote;
import no.peron.demo.model.VoteOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestApi {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testUserAndPollOperations() throws Exception {
        // Create user 1
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        ResponseEntity<User> response = restTemplate.postForEntity(URI.create("/users"), user1, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User createdUser1 = response.getBody();
        Long userId1 = createdUser1.getUserId();

        // List all users
        ResponseEntity<User[]> allUsersResponse = restTemplate.getForEntity(URI.create("/users"), User[].class);
        assertEquals(HttpStatus.OK, allUsersResponse.getStatusCode());

        // Create user 2
        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        response = restTemplate.postForEntity(URI.create("/users"), user2, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User createdUser2 = response.getBody();
        Long userId2 = createdUser2.getUserId();

        // List all users
        allUsersResponse = restTemplate.getForEntity(URI.create("/users"), User[].class);
        assertEquals(HttpStatus.OK, allUsersResponse.getStatusCode());

        // User 1 creates a poll
        Poll poll = new Poll();
        poll.setCreator(createdUser1); // Set the creator as the user created in step 1
        poll.setQuestion("What is your favorite programming language?");
        poll.setPublishedAt(Instant.parse("2024-09-08T12:00:00Z"));
        poll.setValidUntil(Instant.parse("2024-12-31T23:59:59Z"));

        List<VoteOption> options = new ArrayList<>();
        VoteOption option1 = new VoteOption();
        option1.setCaption("Java");
        option1.setPresentationOrder(1);
        options.add(option1);

        VoteOption option2 = new VoteOption();
        option2.setCaption("Python");
        option2.setPresentationOrder(2);
        options.add(option2);

        poll.setOptions(options);

        ResponseEntity<Poll> pollResponse = restTemplate.postForEntity(URI.create("/polls"), poll, Poll.class);
        assertEquals(HttpStatus.OK, pollResponse.getStatusCode());
        Poll createdPoll = pollResponse.getBody();
        Long pollId = createdPoll.getPollId();

        // List all polls
        ResponseEntity<Poll[]> allPollsResponse = restTemplate.getForEntity(URI.create("/polls"), Poll[].class);
        assertEquals(HttpStatus.OK, allPollsResponse.getStatusCode());

        // User 2 votes on the poll
        Vote vote = new Vote();
        vote.setVoter(createdUser2); // Set the voter as the user created in step 3
        vote.setOption(option1); // Choose the first option

        ResponseEntity<Vote> voteResponse = restTemplate.postForEntity(URI.create("/users/" + userId2 + "/votes"), vote, Vote.class);
        assertEquals(HttpStatus.OK, voteResponse.getStatusCode());
        Vote createdVote = voteResponse.getBody();
        Long voteId = createdVote.getVoteId();

        // User 2 votes on another option on the poll
        Vote updatedVote = new Vote();
        //updatedVote.setVoteId(voteId);
        updatedVote.setVoter(createdUser2);
        updatedVote.setOption(option2);

        restTemplate.put(URI.create("/users/" + userId2 + "/votes/" + voteId), updatedVote);

        // List all user 2 votes
        ResponseEntity<Vote[]> userVotesResponse = restTemplate.getForEntity(URI.create("/users/" + userId2 + "/votes"), Vote[].class);
        assertEquals(HttpStatus.OK, userVotesResponse.getStatusCode());

        // Delete user 2
        restTemplate.delete(URI.create("/users/" + userId2));

        // List all users 2 votes again
        ResponseEntity<Vote[]> userVotesAfterDeletionResponse = restTemplate.getForEntity(URI.create("/users/" + userId2 + "/votes"), Vote[].class);
        assertEquals(HttpStatus.NOT_FOUND, userVotesAfterDeletionResponse.getStatusCode());
    }
}
