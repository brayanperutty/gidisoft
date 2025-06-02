import { setupFileUpload } from '../utils/file_uploaderHandler.js';
import { bindComplianceInput } from '../utils/complianceHandler.js';

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
    
                <div class="row mb-3">
                    <div class="col-md-10">
                        <label class="form-label fw-bold">Actividades:</label>
                        <textarea class="form-control" name="activities" rows="1"></textarea>
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
    setupFileUpload(form, timestamp);
    const newInput = form.querySelector('input[name="compliancePercentage"]');
    if (newInput) bindComplianceInput(newInput);
}
window.addProject = addProject;
