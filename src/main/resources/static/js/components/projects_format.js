function addProject() {
    const container = document.getElementById('projects-container');

    const newProject = document.createElement('div');
    newProject.classList.add('card', 'mb-3', 'border', 'border-secondary', 'p-3');

    newProject.innerHTML = `

            <div class="mb-3">
                <label class="form-label fw-bold">Proyecto:</label>
                <textarea class="form-control" name="proyecto[]" rows="1" required></textarea>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Actividades:</label>
                <textarea class="form-control" name="actividadesProyecto[]" rows="2" required></textarea>
            </div>

            <div class="row mb-3">
                <div class="col-md-5">
                    <label class="form-label fw-bold">Fecha de Inicio:</label>
                    <input type="date" class="form-control" name="fechaInicio[]" required>
                </div>
                <div class="col-md-5">
                    <label class="form-label fw-bold">Fecha de Terminación:</label>
                    <input type="date" class="form-control" name="fechaFin[]" required>
                </div>
                <div class="col-md-2">
                    <label class="form-label fw-bold">% de Cumplimiento:</label>
                    <input type="number" class="form-control" name="cumplimiento[]" min="0" max="100" step="1" required>
                </div>
            </div>

            <div class="d-flex justify-content-end align-items-center">
                <button type="button" class="btn btn-sm btn-outline-danger" onclick="this.closest('.card').remove()">🗑 Eliminar</button>
            </div>
        `;
    container.appendChild(newProject);
}