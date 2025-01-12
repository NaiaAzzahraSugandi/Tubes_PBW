let notificationIDToDelete
function confirmDelete(notificationID) {
    notificationIDToDelete = notificationID;
    document.getElementById('confirmationModal').style.display = 'flex';
    console.log(notificationIDToDelete);
}

function closeModal() {
    document.getElementById('confirmationModal').style.display = 'none';
}

document.getElementById('confirmDeleteButton').onclick = function () {
    event.preventDefault();
    window.location.href = '/user/notifications/delete?id=' + notificationIDToDelete;
    return false;
}

function confirmDeleteAll() {
    document.getElementById('notificationModal').style.display = 'flex';
}

function cancelDeleteAll() {
    document.getElementById('notificationModal').style.display = 'none';
}

document.getElementById('notificationDeleteButton').onclick = function () {
    event.preventDefault();
    window.location.href = '/user/notifications/deleteAll';
    return false;
}