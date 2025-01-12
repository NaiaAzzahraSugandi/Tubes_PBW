const hoursInput = document.getElementById('hours');
const minutesInput = document.getElementById('minutes');
const secondsInput = document.getElementById('seconds');
const durationInput = document.getElementById('duration');

function updateDuration() {
    const hours = hoursInput.value.padStart(2, '0') || '00';
    const minutes = minutesInput.value.padStart(2, '0') || '00';
    const seconds = secondsInput.value.padStart(2, '0') || '00';
    durationInput.value = `${hours}:${minutes}:${seconds}`;
}

hoursInput.addEventListener('input', updateDuration);
minutesInput.addEventListener('input', updateDuration);
secondsInput.addEventListener('input', updateDuration);