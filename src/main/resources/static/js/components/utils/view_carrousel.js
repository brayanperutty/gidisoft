document.addEventListener('DOMContentLoaded', function () {
    const inner = document.getElementById('carouselInner');
    const currentIndex = document.getElementById('currentImageIndex');
    const totalImages = document.getElementById('totalImages');
    const modalTitle = document.getElementById('evidenceModalTitle');
    const carouselElement = document.getElementById('carouselEvidences');

    let currentId = null;
    let currentFormatId = null;
    let currentEntity = null;

    document.querySelectorAll('.view-evidences').forEach(btn => {
        btn.addEventListener('click', () => {
            const files = btn.dataset.files?.split(',') || [];
            const container = btn.closest('div');

            // Capturar la entidad (projects, directions, etc.)
            currentEntity = btn.dataset.entity || 'projects';

            // Buscar ID y formatId (input o span)
            const idElement = container.querySelector('input[name="id"], .projectId, .directionId');
            const formatIdElement = container.querySelector('input[name="formatId"]');
            const nameElement = container.querySelector('textarea[name="name"], input[name="name"], span[name="name"]');

            currentId = idElement?.value || idElement?.textContent?.trim();
            currentFormatId = formatIdElement?.value || formatIdElement?.textContent?.trim();
            const name = nameElement?.value || nameElement?.textContent?.trim() || 'Sin nombre';

            // Renderizar imágenes
            inner.innerHTML = '';
            files.forEach((url, index) => {
                const item = document.createElement('div');
                item.className = 'carousel-item' + (index === 0 ? ' active' : '');
                item.innerHTML = `<div class="d-flex justify-content-center align-items-center w-100 h-100">
                                            <img src="${url}" data-url="${url}" class="img-fluid" style="max-width: 90%; max-height:90%; object-fit: contain;" alt="Evidencia">
                                          </div>`;
                inner.appendChild(item);
            });

            // Actualizar título e índice
            modalTitle.textContent = `Evidencias de ${name}`;
            currentIndex.textContent = 1;
            totalImages.textContent = files.length;

            // Reiniciar carrusel
            bootstrap.Carousel.getOrCreateInstance(carouselElement).to(0);
        });
    });

    carouselElement.addEventListener('slid.bs.carousel', function (e) {
        currentIndex.textContent = e.to + 1;
    });

    const deleteButton = document.getElementById('deleteImageBtn');

    deleteButton.addEventListener('click', async () => {
        const activeItem = inner.querySelector('.carousel-item.active');
        if (!activeItem) return;

        const img = activeItem.querySelector('img');
        const imageUrl = img?.dataset.url;

        if (!imageUrl) return;

        const confirmed = confirm('¿Estás seguro de que deseas eliminar esta imagen?');
        if (!confirmed) return;

        window.location.href = `/${currentEntity}/${currentId}/delete-evidence?url=${imageUrl}&formatId=${currentFormatId}`;
    });
});