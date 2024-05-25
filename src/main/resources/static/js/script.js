// const postField = document.getElementById("sub-sec-10");
// // const postField = document.getElementById("sub-sec-11");
// const postFieldButt = document.getElementById("butt1");



// function displayPostField(){
//     // alert("Hello")
//     let visibilityInputField = postField.style.visibility;
//
//     if(visibilityInputField === "hidden"){
//         postField.style.visibility = "visible";
//     }else postField.style.visibility = "hidden";
// }
//
// postFieldButt.addEventListener("click", displayPostField);


// // Get all 'Comment' buttons and their associated comment fields
// const commentButtons = document.querySelectorAll('.comment-button');
// const commentForms = document.querySelectorAll('.comment-form');
//
// // Add event listeners to each 'Comment' button
// commentButtons.forEach((button, index) => {
//     button.addEventListener('click', () => {
//         // Toggle visibility of the associated comment field
//         commentForms[index].classList.toggle('hidden');
//     });
// });

const commentButtons = document.querySelectorAll('.comment-button');
const commentForms = document.querySelectorAll('.comment-form');

// Add event listeners to each 'Comment' button
commentButtons.forEach((button, index) => {
    button.addEventListener('click', () => {
        // Toggle visibility of the associated comment field
        commentForms[index].classList.toggle('hidden');
    });
});

// Add event listeners to each 'Hide' button for comment forms
const hideButtons = document.querySelectorAll('.hide-comment');
hideButtons.forEach((button) => {
    button.addEventListener('click', () => {
        button.parentElement.classList.add('hidden');
    });
});


// const featured = document.getElementById('featured').value;
//
// const likeButton = document.getElementById('likeButton');
// const unlikeButton = document.getElementById('unlikeButton');
//
// if (featured === false){
//        likeButton.style.display = 'none';
//        unlikeButton.style.display = 'block';
// }else{
//    likeButton.style.display = 'block';
//    unlikeButton.style.display = 'none';
// }
//


const likeBtn = document.getElementById('like-btn');
const postIdInput = document.getElementById('post-id');

likeBtn.addEventListener('click', () => {
  const postId = postIdInput.value;
  const liked = likeBtn.textContent === 'Unlike';

  // Send a request to your Spring Boot controller to update the like status
  fetch(`/like/${postId}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ liked }),
  });
});


