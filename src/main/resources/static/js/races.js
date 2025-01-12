function resetOthers(changed) {
    const distanceSelect = document.getElementById('distance');
    const participantSelect = document.getElementById('participants')

    if (changed === 'distance') {
        participantSelect.value = 'None';
    }
    else if (changed === 'participants') {
        distanceSelect.value = 'None';
    }
    // Submit the form to apply the filters
    document.forms[0].submit();
}