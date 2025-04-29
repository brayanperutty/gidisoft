const deleteDirection = document.querySelectorAll('.deleteDirection');
const deleteDirectionConfirm = document.querySelector('#deleteDirectionConfirm');

if(deleteDirection){
    deleteDirection.forEach(function(button){
        button.addEventListener('click', function(){
            const form = this.closest('form'); // el contenedor más directo

            const directionIdInput = form.querySelector('.directionId');
            const formatIdInput = form.querySelector('.formatId');

            const directionId = directionIdInput?.value;
            const formatId = formatIdInput?.value;

            if (directionId && formatId) {
                localStorage.setItem('directionId', directionId);
                localStorage.setItem('formatId', formatId);
            }
        });
    });
}

if(deleteDirectionConfirm){
    deleteDirectionConfirm.addEventListener('click', function(){
        const directionId = localStorage.getItem('directionId');
        const formatId = localStorage.getItem('formatId');

        if (directionId && formatId) {
            window.location.href = `/directions/${directionId}/delete?formatId=${formatId}`;

            // Limpiamos después de redirigir
            localStorage.removeItem('projectId');
            localStorage.removeItem('formatId');
        }
    });
}