function show(button) {
    document.querySelectorAll('.edit-row')
        .forEach(r => r.style.display = 'none');

    const row = button.closest('.unit-row, .station-row');
    const editRow = row.nextElementSibling;

    if (editRow && editRow.classList.contains('edit-row')) {
        editRow.style.display = 'grid';
    }
}
