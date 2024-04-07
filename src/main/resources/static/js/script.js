const postField = document.getElementById("sub-sec-2");
const postFieldButt = document.getElementById("butt");

function displayPostField(){
    if(postField.style.display === "hidden"){
        postField.style.display = "visible";
    }else postField.style.display = "hidden";
}

postField.addEventListener("onclick", displayPostField);