package facebookCloneApp.demo.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_tbl")
public class Post extends BaseClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @OneToMany
    private List<Like> likes = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany
    private List<Comment> comment = new ArrayList<>();
}
