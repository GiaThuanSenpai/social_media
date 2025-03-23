package com.social.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reel_id;

    private String title;

    private String video;

    @ManyToOne
    private User user;
}
