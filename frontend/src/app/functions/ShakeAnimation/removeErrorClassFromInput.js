export function removeError(class_id) {
  const element = document.getElementsByTagName('input');

  if (typeof element === 'undefined') {
    element.classList.remove('error');
  }
}
