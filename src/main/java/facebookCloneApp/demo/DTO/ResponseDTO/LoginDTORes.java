package facebookCloneApp.demo.DTO.ResponseDTO;

import facebookCloneApp.demo.Enums.Gender;
import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.Post;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class LoginDTORes {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String firstname;
    private String lastname;
    private Gender gender;
    private Integer age;
    private String email;
    private String password;
    private List<Post> postList;
    private List<Comment> commentList;
}
