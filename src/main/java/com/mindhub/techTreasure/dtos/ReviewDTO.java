package com.mindhub.techTreasure.dtos;

import com.mindhub.techTreasure.models.Review;

public class ReviewDTO {
    private Long id;
    private String comment;
    private Integer rating;

    public ReviewDTO() {

    }

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.comment = review.getComment();
        this.rating = review.getRating();
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Integer getRating() {
        return rating;
    }
}
