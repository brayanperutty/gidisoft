const downloadBtn = document.querySelectorAll('.generate');
const generateConfirm = document.querySelector('#generateConfirm');

if (downloadBtn) {
    downloadBtn.forEach(function (button) {
        button.addEventListener('click', function () {
            const row = button.closest('tr');
            const idCell = row.querySelector('td.id');

            if (idCell) {
                localStorage.setItem('idCell', idCell.textContent.trim());
            }
        });
    });
}

if (generateConfirm) {
    generateConfirm.addEventListener('click', async function () {
        const format = document.getElementById('fileFormat').value;
        const id = localStorage.getItem('idCell');

        if (id) {
            const endpoint = `/formats/${id}/download?format=${format}`;
            const response = await fetch(endpoint);

            const blob = await response.blob();
            const url = URL.createObjectURL(blob);

            const a = document.createElement('a');
            a.href = url;
            a.download = `InformeGestion.${format}`;
            document.body.appendChild(a);
            a.click();
            a.remove();
            URL.revokeObjectURL(url);

            closeModal();
            localStorage.removeItem('idCell');
        }
    });
}

function closeModal() {
    document.getElementById('formatModal').style.display = 'none';
}

