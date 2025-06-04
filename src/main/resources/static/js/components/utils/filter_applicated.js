document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('filtroForm').addEventListener('submit', async function (e) {
        e.preventDefault();

        const filtros = {
            teacher: document.getElementById('teacher').value.trim(),
            group: document.getElementById('group').value.trim(),
            academicPeriod: document.getElementById('academicPeriod').value.trim(),
            status: document.getElementById('status').value.trim()
        };

        // Solo agregar parámetros que tengan valor
        const params = new URLSearchParams();
        for (const [key, value] of Object.entries(filtros)) {
            if (value) {
                params.append(key, value);
            }
        }
        try {
            window.location.href = `/formats/list?${params.toString()}`;
        } catch (error) {
            console.error(error);
            alert("Ocurrió un error al aplicar los filtros.");
        }
    });
});

document.getElementById('limpiarFiltros').addEventListener('click', async function () {
    // Limpiar los inputs
    document.getElementById('teacher').value = '';
    document.getElementById('group').value = '';
    document.getElementById('academicPeriod').value = '';
    document.getElementById('status').value = '';

    // Volver a cargar la tabla sin filtros
    try {
        window.location.href = `/formats/list`;
    } catch (error) {
        console.error(error);
        alert("Ocurrió un error al limpiar los filtros.");
    }
});