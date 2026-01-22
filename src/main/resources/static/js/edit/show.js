function show(button) {
    document.querySelectorAll('.edit-row')
        .forEach(r => r.style.display = 'none');

    const tr = button.closest('tr');
    const editRow = tr.nextElementSibling;

    if (editRow && editRow.classList.contains('edit-row')) {
        editRow.style.display = 'table-row';
    }
}
