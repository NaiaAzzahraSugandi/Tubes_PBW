function resetOthers(changed) {
    const timeSelect = document.getElementById('time');
    const durationSelect = document.getElementById('duration');
    const distanceSelect = document.getElementById('distance');

    if (changed === 'time') {
      durationSelect.value = 'None';
      distanceSelect.value = 'None';
    } else if (changed === 'duration') {
      timeSelect.value = 'None';
      distanceSelect.value = 'None';
    } else if (changed === 'distance') {
      timeSelect.value = 'None';
      durationSelect.value = 'None';
    }

    // Submit the form to apply the filters
    document.forms[0].submit();
  }

  let activityIDToDelete
  function confirmDelete(activityID){
    activityIDToDelete = activityID;
    document.getElementById('confirmationModal').style.display = 'flex';
    console.log(activityIDToDelete);
  }

  function closeModal(event){
    document.getElementById('confirmationModal').style.display = 'none';
    event.preventDefault();
  }

  document.getElementById('confirmDeleteButton').onclick = function() {
    event.preventDefault();
    window.location.href = '/user/delete?id=' + activityIDToDelete;
    return false;
  }

  function pageClick(page) {
    event.preventDefault();
    console.log("halaman: " + page);


    fetch('/user/activity/get-page', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ page: page })
    })
    .then(response => {
        if (response.ok) {
            console.log("Data berhasil dikirim ke controller");
            return response.text();
        } else {
            console.error("Gagal mengirim data ke controller");
        }
    })
    .then(data => {
        console.log("Respon dari server:", data);
    })
    .catch(error => {
        console.error("Terjadi kesalahan:", error);
    });
}