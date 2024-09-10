package no.peron.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    private String caption;
    private int presentationOrder;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    @JsonBackReference
    private Poll poll;

    public VoteOption() {
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long id) {
        this.optionId = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}