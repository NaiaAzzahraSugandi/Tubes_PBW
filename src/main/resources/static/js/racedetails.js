let userIDToDelete
let raceIDToDelete
function confirmDelete(raceID, userID) {
  userIDToDelete = userID;
  raceIDToDelete = raceID;
  document.getElementById('reasonModal').style.display = 'flex';
  document.getElementById('user_id').value = userID;
  document.body.style.overflow = "hidden";
  console.log(userIDToDelete);
  console.log(raceIDToDelete);
}
function closeModal(event) {
  document.getElementById('reasonModal').style.display = 'none';
  document.body.style.overflow = "auto";
  event.preventDefault();
}

document.querySelector("button[type=submit]").onclick = function () {
  event.preventDefault();
  message = document.getElementById('message').value;
  raceTitle = document.getElementsByTagName('h2')[0].textContent;
  raceTitle
  window.location.href = '/admin/races/detail/delete?raceID=' + raceIDToDelete + '&userID=' + userIDToDelete + '&message=' + message + '&raceTitle=' + raceTitle;
  return false;
}