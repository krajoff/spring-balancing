function create() {
    const name = document.getElementById('stationName').value;

    fetch('/api/station', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
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
