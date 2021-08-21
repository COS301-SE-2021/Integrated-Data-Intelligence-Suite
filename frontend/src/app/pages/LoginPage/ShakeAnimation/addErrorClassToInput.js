export function addError() {
    console.log("add error is running bro");

    //Get all Input elements
    var array_input_elements = document.getElementsByClassName("input_item_div");
    console.log("Array input elements:");
    console.log(array_input_elements)

    //Check if they are empty
    if (typeof array_input_elements !== 'undefined') {
        for (var i = 0, len = array_input_elements.length; i < len; i++) {
            array_input_elements[i].classList.add('error');
        }
    }
}