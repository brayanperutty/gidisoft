const deleteProject = document.querySelectorAll('.deleteProject');
const deleteProjectConfirm = document.querySelector('#deleteProjectConfirm');

if(deleteProject){
    deleteProject.forEach(function(button){
        button.addEventListener('click', function(){
            const form = this.closest('form'); // el contenedor más directo

            const projectIdInput = form.querySelector('.projectId');
            const formatIdInput = form.querySelector('.formatId');

            const projectId = projectIdInput?.value;
            const formatId = formatIdInput?.value;

            if (projectId && formatId) {
                localStorage.setItem('projectId', projectId);
                localStorage.setItem('formatId', formatId);
            }
        });
    });
}

if(deleteProjectConfirm){
    deleteProjectConfirm.addEventListener('click', function(){
        const projectId = localStorage.getItem('projectId');
        const formatId = localStorage.getItem('formatId');

        if (projectId && formatId) {
            window.location.href = `/projects/${projectId}/delete?formatId=${formatId}`;

            // Limpiamos después de redirigir
            localStorage.removeItem('projectId');
            localStorage.removeItem('formatId');
        }
    });
}