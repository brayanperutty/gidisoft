const deleteActivity = document.querySelectorAll('.deleteOther');
const deleteActivityConfirm = document.querySelector('#deleteOtherConfirm');

if(deleteActivity){
    deleteActivity.forEach(function(button){
        button.addEventListener('click', function(){
            const form = this.closest('form'); // el contenedor más directo

            const otherIdInput = form.querySelector('.otherId');
            const formatIdInput = form.querySelector('.formatId');

            const otherId = otherIdInput?.value;
            const formatId = formatIdInput?.value;

            if (otherId && formatId) {
                localStorage.setItem('otherId', otherId);
                localStorage.setItem('formatId', formatId);
            }
        });
    });
}

if(deleteActivityConfirm){
    deleteActivityConfirm.addEventListener('click', function(){
        const otherId = localStorage.getItem('otherId');
        const formatId = localStorage.getItem('formatId');

        if (otherId && formatId) {
            window.location.href = `/others/${otherId}/delete?formatId=${formatId}`;

            // Limpiamos después de redirigir
            localStorage.removeItem('otherId');
            localStorage.removeItem('formatId');
        }
    });
}