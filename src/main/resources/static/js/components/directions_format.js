function addDirection() {
    const container = document.getElementById('directions-container');

    const newDirection = document.createElement('div');
    newDirection.classList.add('card', 'mb-3', 'border', 'border-secondary', 'p-3');

    newDirection.innerHTML = `
            
            <div class="mb-3">
                <label class="form-label fw-bold">Título del proyecto:</label>
                <textarea class="form-control" name="projectTitle[]" rows="1" required></textarea>
            </div>

            <div class=" mb-3">
                <label class="form-label fw-bold">Tipo de proyecto (Trabajo de Grado - Tesis):</label>
                <textarea class="form-control" name="projectType[]" rows="2" required></textarea>
            </div>

            <div class="row mb-3">
                <div class="col-md-5">
                    <label class="form-label fw-bold">Director:</label>
                    <textarea class="form-control" name="director[]" rows="1" required></textarea>
                </div>
                <div class="col-md-5">
                    <label class="form-label fw-bold">Programa académico:</label>
                    <textarea class="form-control" name="academicProgram[]" rows="1" required></textarea>
                </div>
                <div class="col-md-2">
                    <label class="form-label fw-bold">% de Cumplimiento:</label>
                    <input type="number" class="form-control" name="compliance[]" min="0" max="100" step="1" required>
                </div>
            </div>

            <div class="d-flex justify-content-end align-items-center">
                <button type="button" class="btn btn-sm btn-outline-danger" onclick="this.closest('.card').remove()">🗑 Eliminar</button>
            </div>
        `;
    container.appendChild(newDirection);
}
