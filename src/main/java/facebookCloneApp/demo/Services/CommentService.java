package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.Post;

import java.util.List;

public interface CommentService {
    void createComment(Comment comment);
    void updateComment(Comment comment);
    void deleteComment(Long commentId);
}
