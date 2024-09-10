package no.peron.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import no.peron.demo.model.VoteOption;
import no.peron.demo.model.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollId;

    private String question;
    private Instant publishedAt;
    private Instant validUntil;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VoteOption> options = new ArrayList<>();

    public Poll() {
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long id) {
        this.pollId = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<VoteOption> getOptions() {
        return options;
    }

    public void setOptions(List<VoteOption> options) {
        this.options = options;
    }
}