function update(button) {
    const editRow = button.closest('.edit-row');
    if (!editRow) {
        console.error('Cannot find edit row');
        return;
    }

    const unitRow = editRow.previousElementSibling;
    if (!unitRow) return;

    const unitId = unitRow.dataset.id;
    if (!unitId) {
        alert('Unit ID not found');
        return;
    }

    const inputs = editRow.querySelectorAll('input, select');

    const payload = {
        id: unitId,
        unitNumber: inputs[0].value,
        unitType: inputs[1].value,
        weightPrecision: inputs[2].value,
        weightUnitMeasure: inputs[3].value,
        vibrationPrecision: inputs[4].value,
        vibrationUnitMeasure: inputs[5].value,
        description: inputs[6].value
    };

    fetch(`/api/station/${stationId}/unit`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
        .then(res => {
            if (!res.ok) {
                return res.text().then(t => {
                    throw new Error(t || 'Update failed');
                });
            }
            return res.json();
        })
        .then(updated => {
            const tds = unitRow.querySelectorAll('div');
            tds[0].textContent = updated.unitNumber;
            tds[1].textContent = updated.unitType;
            tds[2].textContent = updated.weightPrecision;
            tds[3].textContent = updated.weightUnitMeasure;
            tds[4].textContent = updated.vibrationPrecision;
            tds[5].textContent = updated.vibrationUnitMeasure;
            tds[6].textContent = updated.description;

            editRow.style.display = 'none';
        })
        .catch(err => {
            console.error(err);
            alert(err.message);
        });
}
