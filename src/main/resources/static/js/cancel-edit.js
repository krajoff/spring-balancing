function cancelEdit(button) {
    const editRow = button.closest('.edit-row');
    const row = editRow.previousElementSibling;

    const currentName = row.querySelector('td').textContent;
    editRow.querySelector('.edit-input').value = currentName;

    editRow.style.display = 'none';
}
