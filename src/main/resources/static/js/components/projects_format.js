function addProject() {
    const container = document.getElementById('projects-container');
    const idFormat = document.getElementById('id-format');

    const form = document.createElement('form');
    form.action = '/projects'; // tu ruta
    form.method = 'post';
    form.classList.add('card', 'mb-3', 'border', 'border-secondary', 'p-3');
    form.innerHTML = `
                <input type="hidden" class="form-control" name="formatId" value="${idFormat.value}">

                <div class="mb-3">
                    <label class="form-label fw-bold">Proyecto:</label>
                    <textarea class="form-control" name="name" rows="1" required></textarea>
                </div>
    
                <div class="mb-3">
                    <label class="form-label fw-bold">Actividades:</label>
                    <textarea class="form-control" name="activities" rows="2" required></textarea>
                </div>
    
                <div class="row mb-3">
                    <div class="col-md-5">
                        <label class="form-label fw-bold">Fecha de Inicio:</label>
                        <input type="date" class="form-control" name="startDate" required>
                    </div>
                    <div class="col-md-5">
                        <label class="form-label fw-bold">Fecha de Terminación:</label>
                        <input type="date" class="form-control" name="endDate" required>
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