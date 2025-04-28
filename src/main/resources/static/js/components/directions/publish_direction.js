const publishDirection = document.querySelectorAll('.publishDirection');
const publishDirectionConfirm = document.querySelector('#publishDirectionConfirm');

if (publishDirection) {
    publishDirection.forEach(function (button) {
        button.addEventListener('click', function () {
            const form = this.closest('form'); // el contenedor más directo

            const directionInput = form.querySelector('.directionId');
            const formatIdInput = form.querySelector('.formatId');

            const directionId = directionInput?.value;
            const formatId = formatIdInput?.value;

            if (directionId && formatId) {
                localStorage.setItem('directionId', directionId);
                localStorage.setItem('formatId', formatId);
            }
        });
    });
}

if (publishDirectionConfirm) {
    publishDirectionConfirm.addEventListener('click', function () {
        const directionId = localStorage.getItem('directionId');
        const formatId = localStorage.getItem('formatId');

        if (directionId && formatId) {
            window.location.href = `/directions/${directionId}/publish?formatId=${formatId}`;

            // Limpiamos después de redirigir
            localStorage.removeItem('directionId');
            localStorage.removeItem('formatId');
        }
    });
}