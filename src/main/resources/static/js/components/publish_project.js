const publishProjectButtons = document.querySelectorAll('.publish');
const publishConfirm = document.querySelector('#confirmarPublicacion');

if (publishProjectButtons) {
    publishProjectButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const form = this.closest('form'); // el contenedor más directo

            const projectIdInput = form.querySelector('.projectId');
            const formatIdInput = form.querySelector('.formatId');

            const projectId = projectIdInput?.value;
            const formatId = formatIdInput?.value;

            if (projectId && formatId) {
                localStorage.setItem('projectId', projectId);
                localStorage.setItem('formatId', formatId);
            }
        });
    });
}

if (publishConfirm) {
    publishConfirm.addEventListener('click', function () {
        const projectId = localStorage.getItem('projectId');
        const formatId = localStorage.getItem('formatId');

        if (projectId && formatId) {
            window.location.href = `/projects/${projectId}/publish?formatId=${formatId}`;

            // Limpiamos después de redirigir
            localStorage.removeItem('projectId');
            localStorage.removeItem('formatId');
        }
    });
}