// Esperamos a que todo el DOM esté cargado
document.addEventListener("DOMContentLoaded", function() {
    // Delegar el evento de eliminación en el contenedor de los nombres de archivo
    document.getElementById('projects-container').addEventListener('click', function(event) {
        if (event.target && event.target.matches('.remove-file')) {
            const fileDiv = event.target.closest('.file-name'); // Buscar el contenedor del archivo
            const fileInput = fileDiv.closest('form').querySelector('input[type="file"]'); // Obtener el input del formulario

            // Obtener la lista de archivos del input
            let files = fileInput.files;
            const fileName = fileDiv.textContent.trim(); // Obtener el nombre del archivo

            // Convertir a array para poder usar splice
            let fileList = Array.from(files);

            // Buscar el archivo en la lista y eliminarlo
            const index = fileList.findIndex(file => file.name === fileName);
            if (index !== -1) {
                fileList.splice(index, 1); // Eliminar el archivo
            }

            // Actualizar los archivos en el input
            fileInput.files = new FileList(...fileList);

            // Eliminar el nombre del archivo de la interfaz
            fileDiv.remove();
        }
    });
});
