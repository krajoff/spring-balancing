function cancel(button) {
    const editRow = button.closest('.edit-row');
    const row = editRow.previousElementSibling;

    const currentName = row.querySelector('div').textContent.trim();
    editRow.querySelector('input').value = currentName;

    editRow.style.display = 'none';
}
