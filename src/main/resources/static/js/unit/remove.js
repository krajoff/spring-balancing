function remove(button) {
    const editRow = button.closest('tr.edit-row');
    if (!editRow) return;

    const unitRow = editRow.previousElementSibling;
    if (!unitRow) return;

    const unitId = unitRow.dataset.id;
    if (!unitId) {
        alert('Unit ID not found');
        return;
    }

    if (!confirm('Delete this unit?')) return;

    fetch(`/api/station/${stationId}/unit`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: unitId
        })
    })
        .then(res => {
            if (!res.ok) {
                return res.text().then(t => {
                    throw new Error(t || 'Delete failed');
                });
            }
            unitRow.remove();
            editRow.remove();
        })
        .catch(err => {
            console.error(err);
            alert(err.message);
        });
}
