function showEditRow(button) {
  var rows = document.querySelectorAll('.edit-row');
  rows.forEach(row => row.style.display = 'none');
  var tr = button.parentNode.parentNode;
  var editRow = tr.nextElementSibling;
  if (editRow.classList.contains('edit-row')) {
      editRow.style.display = 'table-row'; // Показываем текущую строку редактирования
  }
}

function cancelEdit(button) {
  var editRow = button.parentNode.parentNode.parentNode;
  editRow.style.display = 'none';
}