function update(button) {
    const editRow = button.closest('tr.edit-row');
    if (!editRow) {
        console.error('Cannot find edit row');
        return;
    }

    const input = editRow.querySelector('input.edit-input');
    if (!input) {
        console.error('Cannot find input inside edit row');
        return;
    }

    const name = input.value.trim();
    if (!name) {
        alert('Station name is required');
        return;
    }

    const stationRow = editRow.previousElementSibling;
    const stationId = stationRow.dataset.id;
    const payload = { id: stationId, name: name };

    fetch(`/api/station`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
        .then(r => r.ok ? r.json() : r.text().then(t => { throw new Error(t || 'Remove failed') }))
        .then(data => {
            stationRow.querySelector('td').textContent = data.name;
            editRow.style.display = 'none';
        })
        .catch(e => alert(e.message));
}
