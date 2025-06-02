import { setupFileUpload } from '../utils/file_uploaderHandler.js';

function addDirection() {
    const container = document.getElementById('directions-container');
    const idFormat = document.getElementById('id-format');
    const timestamp = Date.now();

    const form = document.createElement('form');
    form.action = '/directions'; // tu ruta
    form.method = 'post';
    form.enctype = 'multipart/form-data';
    form.classList.add('card', 'border', 'border-secondary', 'p-3');

    let optionsDirector = '<option value="" disabled selected hidden>Seleccione al director</option>';
    window.users.forEach(user => {
        optionsDirector += `<option value="${user.id}">${user.name}</option>`;
    });

    // Construir las opciones para el select de codirector
    let optionsCodirector = '<option value="" disabled selected hidden>Seleccione al codirector</option>';
    window.users.forEach(user => {
        optionsCodirector += `<option value="${user.id}">${user.name}</option>`;
    });

    form.innerHTML = `
            
            <input type="hidden" class="form-control" name="formatId" value="${idFormat.value}">
            
            <div class="mb-3">
                <label class="form-label fw-bold">Participación en:</label>
                <textarea class="form-control" name="name" rows="1"></textarea>
            </div>
            <div class="row mb-3">
                <div class="col-md-5">
                    <label class="form-label fw-bold">Director:</label>
                    <select class="form-select text-center director-select" id="director" name="director">
                        ${optionsDirector}
                    </select>
                </div>
                <div class="col-md-5">
                    <label class="form-label fw-bold">¿Cuenta con codirector?</label>
                    <select class="form-select text-center codirector-select" id="codirector" name="codirector">
                        ${optionsCodirector}
                    </select>
                </div>
                <div class="col-md-2">
                    <label class="form-label fw-bold">% de Cumplimiento:</label>
                    <input
                            type="text"
                            inputmode="numeric"
                            pattern="[0-9]*"
                            maxlength="3"
                            class="form-control no-spinner"
                            name="compliancePercentage"
                            required
                    />
                </div>
            </div>
            
            <div class="mb-3">
                    <label class="form-label fw-bold d-block mb-3">Evidencias de la participación:</label>
                    
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

    const directorSelect = form.querySelector('.director-select');
    const codirectorSelect = form.querySelector('.codirector-select');

    directorSelect.addEventListener('change', function () {
        const selectedDirectorId = this.value;

        // Guardar la opción seleccionada actualmente del codirector
        const selectedCodirectorId = codirectorSelect.value;

        // Limpiar el codirector
        codirectorSelect.innerHTML = '<option value="" disabled selected hidden>Seleccione al codirector</option>';

        // Recargar opciones del codirector EXCLUYENDO al director seleccionado
        window.users.forEach(user => {
            if (user.id !== parseInt(selectedDirectorId)) {
                const option = document.createElement('option');
                option.value = user.id;
                option.textContent = user.name;
                codirectorSelect.appendChild(option);
            }
        });

        // Si el codirector seleccionado ya no existe, poner el placeholder
        if (!Array.from(codirectorSelect.options).some(option => option.value === selectedCodirectorId)) {
            codirectorSelect.value = "";
        } else {
            codirectorSelect.value = selectedCodirectorId;
        }
    });
    setupFileUpload(form, timestamp);
}
window.addDirection = addDirection;
