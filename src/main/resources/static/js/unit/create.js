function create() {
    const unitNumber = document.getElementById('unitNumber').value;
    const unitType = document.getElementById('unitType').value;
    const weightPrecision = document.getElementById('weightPrecision').value;
    const weightUnitMeasure = document.getElementById('weightUnitMeasure').value;
    const vibrationPrecision = document.getElementById('vibrationPrecision').value;
    const vibrationUnitMeasure = document.getElementById('vibrationUnitMeasure').value;
    const description = document.getElementById('description').value;

    if (!unitNumber) {
        alert('Unit number is required');
        return;
    }

    fetch(`/api/station/${stationId}/unit`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            unitNumber: unitNumber,
            unitType: unitType,
            weightPrecision: weightPrecision,
            weightUnitMeasure: weightUnitMeasure,
            vibrationPrecision: vibrationPrecision,
            vibrationUnitMeasure: vibrationUnitMeasure,
            description: description
        })
    })
        .then(res => {
            if (!res.ok) {
                return res.text().then(t => {
                    throw new Error(t || 'Create failed');
                });
            }
            return res.json();
        })
        .then(() => {
            location.reload();
        })
        .catch(err => {
            console.error(err);
            alert(err.message);
        });
}
