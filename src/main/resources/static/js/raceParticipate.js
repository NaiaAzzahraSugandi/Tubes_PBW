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

const distanceInput = document.getElementById('distance');
const raceLengthInput = document.getElementById('raceLength');
const participateButton = document.getElementById('participate-button');
const raceLengthValid = document.getElementById('raceLengthValid');

function checkDistance() {
    const distanceValue = parseFloat(distanceInput.value);
    const raceLengthValue = parseFloat(raceLengthInput.value);

    if ( distanceValue < raceLengthValue) {
        participateButton.disabled = true;
        raceLengthValid.style.display = 'block';
    } else {
        participateButton.disabled = false;
        participateButton.textContent = "Participate"
        raceLengthValid.style.display = 'none';
    }
}

distanceInput.addEventListener('input', checkDistance);

checkDistance();