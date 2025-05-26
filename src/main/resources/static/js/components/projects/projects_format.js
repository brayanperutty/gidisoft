function addProject() {
    const container = document.getElementById('projects-container');
    const idFormat = document.getElementById('id-format');
    const timestamp = Date.now();

    const form = document.createElement('form');
    form.action = '/projects'; // tu ruta
    form.method = 'post';
    form.enctype = 'multipart/form-data';
    form.classList.add('card', 'mt-3', 'border', 'border-secondary', 'p-3');
    form.innerHTML = `
                <input type="hidden" class="form-control" name="formatId" value="${idFormat.value}">

                <div class="mb-3">
                    <label class="form-label fw-bold">Proyecto:</label>
                    <textarea class="form-control" name="name" rows="1"></textarea>
                </div>
    
                <div class="mb-3">
                    <label class="form-label fw-bold">Actividades:</label>
                    <textarea class="form-control" name="activities" rows="2"></textarea>
                </div>
    
                <div class="row mb-3">
                    <div class="col-md-5">
                        <label class="form-label fw-bold">Fecha de Inicio:</label>
                        <input type="date" class="form-control" name="startDate">
                    </div>
                    <div class="col-md-5">
                        <label class="form-label fw-bold">Fecha de Terminación:</label>
                        <input type="date" class="form-control" name="endDate">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label fw-bold">% de Cumplimiento:</label>
                        <input type="number" class="form-control" name="compliancePercentage" min="0" max="100" step="1">
                    </div>
                </div>
                
                <div class="mb-3">
                    <label class="form-label fw-bold d-block mb-3">Evidencias del Proyecto:</label>
                    
                    <!-- Input oculto -->
                    <input type="file" name="files" multiple id="fileInput-${timestamp}" style="display: none;">
                    
                    <!-- Botón para seleccionar archivos -->
                    <button type="button" class="btn btn-sm btn-outline-secondary" id="fileButton-${timestamp}">
                        Seleccionar archivos
                    </button>
                    
                    <!-- Contador personalizado -->
                    <small id="fileCount-${timestamp}" class="form-text text-muted ms-2">Ningún archivo seleccionado</small>
                
                    <!-- Contenedor para los nombres de los archivos -->
                    <div id="fileNamesContainer-${timestamp}" class="mt-2"></div>
                </div>
    
                <div class="d-flex justify-content-end align-items-center">
                    <button type="submit" class="btn btn-sm btn-primary">Guardar</button>
                    <button type="button" class="btn btn-sm btn-outline-danger ms-2" onclick="this.closest('.card').remove()">🗑 Eliminar</button>
                </div>
        `;

    container.appendChild(form);

    // Agregar el evento de carga de archivos al nuevo input creado
    const fileInput = form.querySelector(`#fileInput-${timestamp}`);
    const fileNamesContainer = form.querySelector(`#fileNamesContainer-${timestamp}`);
    const fileButton = form.querySelector(`#fileButton-${timestamp}`);
    const fileCount = form.querySelector(`#fileCount-${timestamp}`);

    // Aplicar estilo al contenedor para que los archivos se muestren horizontalmente
    fileNamesContainer.style.display = "flex";  // Mostrar en fila
    fileNamesContainer.style.flexWrap = "wrap";  // Permitir que los elementos se ajusten si son muchos
    fileNamesContainer.style.gap = "10px";  // Espacio entre los elementos

    fileButton.addEventListener('click', () => fileInput.click());

    let selectedFiles = [];

    if (fileInput != null) {
        fileInput.addEventListener("change", function (event) {
            selectedFiles = Array.from(event.target.files);
            renderFileNames();
        });
    }

    function renderFileNames() {
        fileNamesContainer.innerHTML = "";

        fileCount.textContent = selectedFiles.length > 0
            ? `${selectedFiles.length} archivo(s) seleccionado(s)`
            : 'Ningún archivo seleccionado';

        selectedFiles.forEach((file, index) => {
            const fileDiv = document.createElement("div");
            fileDiv.textContent = file.name;

            Object.assign(fileDiv.style, {
                backgroundColor: "#f0f0f0",
                borderRadius: "15px",
                padding: "5px 10px",
                color: "#333",
                fontSize: "0.875rem",
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between",
                width: "max-content"
            });

            const removeButton = document.createElement("span");
            removeButton.innerHTML = "&times;";
            removeButton.style.cursor = "pointer";
            removeButton.style.marginLeft = "10px";

            removeButton.addEventListener("click", () => {
                selectedFiles.splice(index, 1);
                renderFileNames();
                const dataTransfer = new DataTransfer();
                selectedFiles.forEach(file => dataTransfer.items.add(file));
                fileInput.files = dataTransfer.files;
            });

            fileDiv.appendChild(removeButton);
            fileNamesContainer.appendChild(fileDiv);
        });
    }

    form.addEventListener("submit", function (e) {
        if (fileInput && fileInput.files.length === 0) {
            fileInput.remove(); // Elimina el input para que no se envíe
        }
    });
}
