function addEvent() {
    const container = document.getElementById('events-container');

    const newEvent = document.createElement('div');
    newEvent.classList.add('card', 'mb-3', 'border', 'border-secondary', 'p-3');

    newEvent.innerHTML = `
            
            <div class="mb-3">
                <label class="form-label fw-bold">Nombre del evento:</label>
                <textarea class="form-control" name="eventTitle[]" rows="1" required></textarea>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label fw-bold">Carácter de evento (local, regional, nacional, internacional):</label>
                    <textarea class="form-control" name="eventType[]" rows="1" required></textarea>
                </div>
                <div class="col-md-4">
                    <label class="form-label fw-bold">Fecha de realización:</label>
                    <input type="date" class="form-control" name="realizeDate[]" required>
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
    container.appendChild(newEvent);
}
