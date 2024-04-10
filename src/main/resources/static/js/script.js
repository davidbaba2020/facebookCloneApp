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

