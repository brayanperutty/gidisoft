document.addEventListener('DOMContentLoaded', function () {
    const params = new URLSearchParams(window.location.search);
    const tab = params.get('tab'); // Espera valores como 'general', 'modules' o 'products'

    if (tab) {
        // Busca el botón que tenga data-bs-target="#tab-{tab}"
        const triggerEl = document.querySelector(`#formatTabs button[data-bs-target="#tab-${tab}"]`);
        if (triggerEl) {
            const tabInstance = new bootstrap.Tab(triggerEl);
            tabInstance.show();
        }
    }
});