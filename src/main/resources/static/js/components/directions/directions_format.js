function addDirection() {
    const container = document.getElementById('directions-container');
    const idFormat = document.getElementById('id-format');

    const form = document.createElement('form');
    form.action = '/directions'; // tu ruta
    form.method = 'post';
    form.classList.add('card', 'border', 'border-secondary', 'p-3');
    form.innerHTML = `
            
            <input type="hidden" class="form-control" name="formatId" value="${idFormat.value}">
            
            <div class="mb-3">
                <label class="form-label fw-bold">Título del proyecto:</label>
                <textarea class="form-control" name="name" rows="1"></textarea>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Tipo de proyecto (Trabajo de Grado - Tesis):</label>
                <textarea class="form-control" name="projectType" rows="1"></textarea>
            </div>

            <div class="row mb-3">
                <div class="col-md-5">
                    <label class="form-label fw-bold">Director:</label>
                    <textarea class="form-control" name="director" rows="1"></textarea>
                </div>
                <div class="col-md-5">
                    <label class="form-label fw-bold">Programa académico:</label>
                    <textarea class="form-control" name="academicProgram" rows="1"></textarea>
                </div>
                <div class="col-md-2">
                    <label class="form-label fw-bold">% de Cumplimiento:</label>
                    <input type="number" class="form-control" name="compliancePercentage" min="0" max="100" step="1" required>
                </div>
            </div>

            <div class="d-flex justify-content-end align-items-center">
                    <button type="submit" class="btn btn-sm btn-primary">Guardar</button>
                    <button type="button" class="btn btn-sm btn-outline-danger ms-2" onclick="this.closest('.card').remove()">🗑 Eliminar</button>
                </div>
        `;
    container.appendChild(form);
}
