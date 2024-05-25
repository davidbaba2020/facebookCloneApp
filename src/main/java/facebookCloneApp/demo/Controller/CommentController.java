package facebookCloneApp.demo.Controller;

import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import facebookCloneApp.demo.Services.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/createComment")
    public String createComment(@RequestParam Long userId, @RequestParam Long postId, @ModelAttribute Comment comment) {
        // Save the comment
        commentService.createComment(comment, postId, userId);
        return "redirect:/dashboard";
    }

    @PostMapping("/updateComment")
    public String updateComment(@RequestParam Long commentId, @ModelAttribute Comment updatedComment, @RequestParam Long userId) {

        this.commentService.updateComment(updatedComment, commentId, userId);
        return "redirect:/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable (value = "id") Long id){
        this.commentService.deleteComment(id);
        return "redirect:/dashboard";
    }

}
