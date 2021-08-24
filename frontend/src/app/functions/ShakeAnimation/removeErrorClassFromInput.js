export function removeError(class_id) {
    var element = document.getElementsByTagName("input");

    if (typeof element === 'undefined') {
        element.classList.remove('error');
    }
}

