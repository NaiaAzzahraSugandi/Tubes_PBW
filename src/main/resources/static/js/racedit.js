let raceIDToDelete
function confirmDelete(raceID){
  raceIDToDelete = raceID;
  document.getElementById('confirmationModal').style.display = 'flex';
  document.body.style.overflow = "hidden";
  console.log(raceIDToDelete);
}
function closeModal(event){
  document.getElementById('confirmationModal').style.display = 'none';
  document.body.style.overflow = "auto";
  event.preventDefault();
}

document.getElementById('confirmDeleteButton').onclick = function() {
  window.location.href = '/admin/races/delete?id=' + raceIDToDelete;
  return false;
}