    package facebookCloneApp.demo.Controller;

    import facebookCloneApp.demo.DTO.RequestDTO.LoginDTOReq;
    import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
    import facebookCloneApp.demo.Models.Comment;
    import facebookCloneApp.demo.Models.Post;
    import facebookCloneApp.demo.Models.User;
    import facebookCloneApp.demo.Services.CommentServiceImpl;
    import facebookCloneApp.demo.Services.PostServiceImpl;
    import facebookCloneApp.demo.Services.UserServiceImpl;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.bind.support.SessionStatus;


    import java.util.List;
    import java.util.Objects;

    @Controller
    @SessionAttributes("loggedInUser")
    public class UserController {

        private final UserServiceImpl userServiceImpl;
        private final PostServiceImpl postServiceImpl;
        private final CommentServiceImpl commentServiceImpl;

        public UserController(UserServiceImpl userServiceImpl, PostServiceImpl postServiceImpl, CommentServiceImpl commentServiceImpl) {
            this.userServiceImpl = userServiceImpl;
            this.postServiceImpl = postServiceImpl;
            this.commentServiceImpl = commentServiceImpl;
        }

        @GetMapping("/")
        public String viewHomePage(Model model){
            model.addAttribute("listOfUsers", userServiceImpl.getAllUser());
            return "index";
        }
        @GetMapping("/signup")
        public String signupPage(Model model){
            User user = new User();
            model.addAttribute("user", user);
            return "/signup";
        }
        @PostMapping("/signup")
        public String saveUser(@ModelAttribute("user") User user){
            if (userServiceImpl.userAlreadyExist(user.getEmail())){
                return "redirect:/signup";
            }else {
                userServiceImpl.saveUser(user);
                return "redirect:/login";

            }
        }
        @GetMapping("/login")
        public String loginPage(Model model){
            LoginDTOReq loginDTOReq = new LoginDTOReq();
            model.addAttribute("loginDTOReq", loginDTOReq);
            return "/login";
        }
        @PostMapping("/login")
        public String login(@ModelAttribute LoginDTOReq loginDTOReq, Model model){
            LoginDTORes loginDTORes = userServiceImpl.getUserByEmailAndPassword(loginDTOReq);
            if (loginDTORes == null) {
                return "redirect:/login";

            }else
            {
                model.addAttribute("loggedInUser", loginDTORes);
                return "redirect:/dashboard";
            }
        }
        @GetMapping("/dashboard")
        public String dashboard(@ModelAttribute ("loggedInUser") LoginDTORes loginDTORes, Model model){
            if (loginDTORes != null){
                List<Post> postList = postServiceImpl.getAllPostById(loginDTORes.getId());
                List<Comment> commentList = commentServiceImpl.commentListByUserId(loginDTORes.getId());
                model.addAttribute("user", loginDTORes);
                model.addAttribute("post", new Post());
                model.addAttribute("comment", new Comment());
                model.addAttribute("posts", postList);
                model.addAttribute("comments", commentList);


                return "/dashboard";
            }

            return "redirect:/login";
        }

        @PostMapping("/posts/create")
        public String createPost(@ModelAttribute("loggedInUser") LoginDTORes loginDTORes, @ModelAttribute Post post){

            User userLoggedIn = postServiceImpl.createUserTemplate(loginDTORes);
            post.setUser(userLoggedIn);

        postServiceImpl.createPost(post);
        return "redirect:/dashboard";
        }

        @GetMapping("/post-edit/{id}")
        public String editPost(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
            LoginDTORes loggedInUser = (LoginDTORes) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                // Handle the case where the user is not logged in
                return "redirect:/login"; // Redirect to the login page
            }

            Post post = postServiceImpl.getPostById(id);
            model.addAttribute("post", post);
            model.addAttribute("loggedInUser", loggedInUser);
            return "update_post";
        }

        @PostMapping("/post-update/{id}")
        public String updatePost(@PathVariable(value = "id") Long id, @ModelAttribute Post updatedPost, HttpSession session) {
            LoginDTORes loggedInUser = (LoginDTORes) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                // Handle the case where the user is not logged in
                return "redirect:/login"; // Redirect to the login page
            }

            Post existingPost = postServiceImpl.getPostById(id);
            if (!Objects.equals(existingPost.getUser().getId(), loggedInUser.getId())) {
                // Handle unauthorized access (e.g., redirect to an login page)
                return "redirect:/login"; // Redirect to an error page
            }

            existingPost.setContent(updatedPost.getContent());
            this.postServiceImpl.updatePost(existingPost);
            return "redirect:/dashboard";
        }



        @GetMapping("/post-delete/{id}")
        public String deletePost(@PathVariable (value = "id") Long id){
            this.postServiceImpl.deletePost(id);
            return "redirect:/dashboard";
        }

        @PostMapping("/post/{postId}/comment")
        public String createComment(@PathVariable Long postId, @ModelAttribute("comment") Comment comment, @ModelAttribute("loggedInUser") LoginDTORes loginDTORes) {
            // Retrieve the logged-in user based on principal
            User user = postServiceImpl.createUserTemplate(loginDTORes);

            // Retrieve the post based on postId
            Post post = postServiceImpl.getPostById(postId);

            // Set the post and user for the comment
            comment.setPost(post);
            comment.setUser(user);
//            post.getComment().add(comment);
            // Save the comment
            commentServiceImpl.createComment(comment);
            return "redirect:/dashboard";
        }



        @GetMapping("/logout")
        public String logoutUser(SessionStatus sessionStatus) {
            sessionStatus.setComplete();
            return "redirect:/login";
        }


    }
