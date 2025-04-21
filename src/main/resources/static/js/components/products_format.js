function addProducts() {
    const container = document.getElementById('products-container');

    const newProduct = document.createElement('div');
    newProduct.classList.add('card', 'mb-3', 'border', 'border-secondary', 'p-3');

    newProduct.innerHTML = `
            
            <div class="mb-3">
                <label class="form-label fw-bold">Producto:</label>
                <textarea class="form-control" name="productName[]" rows="1" required></textarea>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Descripción:</label>
                <textarea class="form-control" name="productDescription[]" rows="2" required></textarea>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-8">
                    <label class="form-label fw-bold">Responsable:</label>
                    <textarea class="form-control" name="productManager[]" rows="1" required></textarea>
                </div>
                <div class="col-md-4">
                    <label class="form-label fw-bold">Fecha de realización:</label>
                    <input type="date" class="form-control" name="realizeDate[]" required>
                </div>
            </div>

            <div class="d-flex justify-content-end align-items-center">
                <button type="button" class="btn btn-sm btn-outline-danger" onclick="this.closest('.card').remove()">🗑 Eliminar</button>
            </div>
        `;
    container.appendChild(newProduct);
}
