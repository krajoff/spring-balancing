function create() {
    const name = document.getElementById('stationName').value;

    fetch('/api/station', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
        },
        body: JSON.stringify({
            name: name
        })
    })
        .then(res => {
            if (!res.ok) throw new Error('Create failed');
            return res.json();
        })
        .then(data => {
            console.log('Created:', data);
            location.reload(); // или дорисовать строку вручную
        })
        .catch(err => alert(err.message));
}
