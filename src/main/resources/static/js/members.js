let userIDToDelete
function confirmDelete(userID){
  userIDToDelete = userID;
  document.getElementById('confirmationModal').style.display = 'flex';
  document.body.style.overflow = "hidden";
  console.log(userIDToDelete);
}
function closeModal(event){
  document.getElementById('confirmationModal').style.display = 'none';
  document.body.style.overflow = "auto";
  event.preventDefault();
}

document.getElementById('confirmDeleteButton').onclick = function() {
  event.preventDefault();
  window.location.href = '/admin/delete?id=' + userIDToDelete;
  return false;
}