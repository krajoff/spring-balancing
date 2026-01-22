function remove(button) {
    const editRow = button.closest('tr.edit-row');
    if (!editRow) return;

    const input = editRow.querySelector('input.edit-input');
    const name = input.value.trim();
    if (!name) {
        alert('Station name is required');
        return;
    }

    const stationRow = editRow.previousElementSibling;
    const stationId = stationRow.dataset.id;
    const payload = { id: stationId, name: name };

    fetch(`/api/station`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
        .then(r => {
            if (r.ok) {
                stationRow.remove();
                editRow.remove();
            } else {
                return r.text().then(t => { throw new Error(t || 'Delete failed') });
            }
        })
        .catch(e => alert(e.message));
}
