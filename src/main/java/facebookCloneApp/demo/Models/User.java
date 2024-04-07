package facebookCloneApp.demo.Models;

import facebookCloneApp.demo.Enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tbl")
public class User extends BaseClass{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer age;
    private String email;
    private String password;

    @OneToMany
    private List<Post> posts = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();


}
