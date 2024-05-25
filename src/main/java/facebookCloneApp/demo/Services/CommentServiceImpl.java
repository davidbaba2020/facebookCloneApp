package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import facebookCloneApp.demo.Repository.CommentRepository;
import facebookCloneApp.demo.Repository.PostRepository;
import facebookCloneApp.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createComment(Comment comment, Long postId, Long userId) {
        postRepository.findById(postId).ifPresent(comment::setPost);
        userRepository.findById(userId).ifPresent(comment::setUser);
        this.commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        Optional<Comment> optionalComment= commentRepository.getCommentById(commentId);
        Comment comment = null;
        if (optionalComment.isPresent()){
            comment = optionalComment.get();
        }else {
            throw new RuntimeException("No comment found for that id");
        }
        return comment;
    }
    @Override
    public List<Comment> commentListByUserId(Long userId){
      return commentRepository.findByUserId(userId);
    }

    @Override
    public void updateComment(Comment comment, Long commentId, Long userId) {
        Comment existingComment = null;
        Optional<Comment> optionalComment = commentRepository.getCommentByIdAndUserId(commentId, userId);
        if (optionalComment.isPresent()){
            existingComment = optionalComment.get();
            existingComment.setContent(comment.getContent());

            this.commentRepository.save(existingComment);
        }

    }



    @Override
    public void deleteComment(Long commentId) {
            this.commentRepository.deleteById(commentId);
    }
}
