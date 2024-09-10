package no.peron.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import no.peron.demo.model.Poll;
import no.peron.demo.model.Vote;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String email;

    @OneToMany(mappedBy = "creator")
    @JsonIgnore
    private List<Poll> createdPolls = new ArrayList<>();

    @OneToMany(mappedBy = "voter", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Vote> votes = new ArrayList<>();

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Poll> getCreatedPolls() {
        return createdPolls;
    }

    public void setCreatedPolls(List<Poll> createdPolls) {
        this.createdPolls = createdPolls;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}