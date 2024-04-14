    package facebookCloneApp.demo.Controller;

    import facebookCloneApp.demo.DTO.RequestDTO.LoginDTOReq;
    import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
    import facebookCloneApp.demo.Models.Comment;
    import facebookCloneApp.demo.Models.Like;
    import facebookCloneApp.demo.Models.Post;
    import facebookCloneApp.demo.Models.User;
    import facebookCloneApp.demo.Services.*;
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

        private final UserService userService;
        private final PostService postService;
        private final CommentService commentService;
        private final LikeService likeService;

        public UserController(UserServiceImpl userService, PostServiceImpl postService, CommentServiceImpl commentService, LikeService likeService) {
            this.userService = userService;
            this.postService = postService;
            this.commentService = commentService;
            this.likeService = likeService;
        }

        @GetMapping("/")
        public String viewHomePage(Model model){
            model.addAttribute("listOfUsers", userService.getAllUser());
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
            if (userService.userAlreadyExist(user.getEmail())){
                return "redirect:/signup";
            }else {
                userService.saveUser(user);
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
            LoginDTORes loginDTORes = userService.getUserByEmailAndPassword(loginDTOReq);
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
                List<Post> postList = postService.getAllPostById(loginDTORes.getId());
                List<Comment> commentList = commentService.commentListByUserId(loginDTORes.getId());
                model.addAttribute("user", loginDTORes);
                model.addAttribute("post", new Post());
                model.addAttribute("comment", new Comment());
                model.addAttribute("posts", postList);
                model.addAttribute("comments", commentList);
                model.addAttribute("like", new Like());


                return "/dashboard";
            }

            return "redirect:/login";
        }

//        CRUD ON POST



        @PostMapping("/posts/create")
        public String createPost(@ModelAttribute("loggedInUser") LoginDTORes loginDTORes, @ModelAttribute Post post){

            User userLoggedIn = postService.createUserTemplate(loginDTORes);
            post.setUser(userLoggedIn);

        postService.createPost(post);
        return "redirect:/dashboard";
        }

        @GetMapping("/post-edit/{id}")
        public String editPost(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
            LoginDTORes loggedInUser = (LoginDTORes) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                // Handle the case where the user is not logged in
                return "redirect:/login"; // Redirect to the login page
            }

            Post post = postService.getPostById(id);
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

            Post existingPost = postService.getPostById(id);
            if (!Objects.equals(existingPost.getUser().getId(), loggedInUser.getId())) {
                // Handle unauthorized access (e.g., redirect to an login page)
                return "redirect:/login"; // Redirect to an error page
            }

            existingPost.setContent(updatedPost.getContent());
            this.postService.updatePost(existingPost);
            return "redirect:/dashboard";
        }



        @GetMapping("/post-delete/{id}")
        public String deletePost(@PathVariable (value = "id") Long id){
            this.postService.deletePost(id);
            return "redirect:/dashboard";
        }



//        CRUD ON COMMENT

        @PostMapping("/post/{postId}/comment")
        public String createComment(@PathVariable Long postId, @ModelAttribute("comment") Comment comment, @ModelAttribute("loggedInUser") LoginDTORes loginDTORes) {
            // Retrieve the logged-in user based on principal
            User user = postService.createUserTemplate(loginDTORes);

            // Retrieve the post based on postId
            Post post = postService.getPostById(postId);

            // Set the post and user for the comment
            comment.setPost(post);
            comment.setUser(user);
//            post.getComment().add(comment);
            // Save the comment
            commentService.createComment(comment);
            return "redirect:/dashboard";
        }

        @GetMapping("/comment-edit/{id}")
        public String editComment(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
            LoginDTORes loggedInUser = (LoginDTORes) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                // Handle the case where the user is not logged in
                return "redirect:/login"; // Redirect to the login page
            }

            Comment comment = commentService.getCommentById(id);
            model.addAttribute("comment", comment);
            model.addAttribute("loggedInUser", loggedInUser);
            return "update_comment";
        }

        @PostMapping("/comment-update/{id}")
        public String updateComment(@PathVariable(value = "id") Long id, @ModelAttribute Comment updatedComment, HttpSession session) {
            LoginDTORes loggedInUser = (LoginDTORes) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                // Handle the case where the user is not logged in
                return "redirect:/login"; // Redirect to the login page
            }

            Comment existingComment = commentService.getCommentById(id);
            if (!Objects.equals(existingComment.getUser().getId(), loggedInUser.getId())) {
                // Handle unauthorized access (e.g., redirect to an login page)
                return "redirect:/login"; // Redirect to an error page
            }

            existingComment.setContent(updatedComment.getContent());
            this.commentService.updateComment(existingComment);
            return "redirect:/dashboard";
        }


        @GetMapping("/comment-delete/{id}")
        public String deleteComment(@PathVariable (value = "id") Long id){
            this.commentService.deleteComment(id);
            return "redirect:/dashboard";
        }



//        LIKE / UNLIKE OPERATIONS

//        @GetMapping("/likePost/{id}/{userId}")
//        public String likePost(@PathVariable(value = "id") Long id, @ModelAttribute("loggedInUser") LoginDTORes loginDTORes) {
//            Post post = postServiceImpl.getPostById(id);
//            User user = postServiceImpl.createUserTemplate(loginDTORes);
//            if (user == null) {
//                // Handle the case where the user is not logged in
//                return "redirect:/login"; // Redirect to the login page
//            }
//
//            likeService.likePost(post, user);
//            return "redirect:/dashboard";
//        }

        @GetMapping("/likePost/{id}")
        public String likePost(@PathVariable(value = "id") Long postId) {
            likeService.likePost(postId);
            return "redirect:/dashboard";
        }



        @GetMapping("/unlikePost/{id}")
        public String unlikePost(@PathVariable(value = "id") Long id) {
            Post post = postService.getPostById(id);
            likeService.unlikePost(post.getId());
            return "redirect:/dashboard";
        }


        @GetMapping("/logout")
        public String logoutUser(SessionStatus sessionStatus) {
            sessionStatus.setComplete();
            return "redirect:/login";
        }


    }
