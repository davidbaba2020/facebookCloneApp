package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.Post;

import java.util.List;

public interface CommentService {
    void createComment(Comment comment);
    Comment getCommentById(Long commentId);
    void updateComment(Comment comment);
    void deleteComment(Long commentId);
    public List<Comment> commentListByUserId(Long userId);
}
