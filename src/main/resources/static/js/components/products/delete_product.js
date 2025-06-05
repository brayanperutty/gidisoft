const deleteProduct = document.querySelectorAll('.deleteProduct');
const deleteProductConfirm = document.querySelector('#deleteProductConfirm');

if(deleteProduct){
    deleteProduct.forEach(function(button){
        button.addEventListener('click', function(){
            const form = this.closest('form'); // el contenedor más directo

            const productIdInput = form.querySelector('.productId');
            const formatIdInput = form.querySelector('.formatId');

            const productId = productIdInput?.value;
            const formatId = formatIdInput?.value;

            if (productId && formatId) {
                localStorage.setItem('productId', productId);
                localStorage.setItem('formatId', formatId);
            }
        });
    });
}

if(deleteProductConfirm){
    deleteProductConfirm.addEventListener('click', function(){
        const productId = localStorage.getItem('productId');
        const formatId = localStorage.getItem('formatId');

        if (productId && formatId) {
            window.location.href = `/products/${productId}/delete?formatId=${formatId}`;

            // Limpiamos después de redirigir
            localStorage.removeItem('productId');
            localStorage.removeItem('formatId');
        }
    });
}