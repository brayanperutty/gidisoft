document.addEventListener('DOMContentLoaded', function () {
    // Selecciona todos los formularios de edición de direcciones
    document.querySelectorAll('form[action="/directions"]').forEach(form => {
        const directorSelect = form.querySelector('select[name="director"]');
        const codirectorSelect = form.querySelector('select[name="codirector"]');

        if (!directorSelect || !codirectorSelect) return;

        const updateCodirectorOptions = () => {
            const selectedDirectorId = directorSelect.value;

            // Guardamos temporalmente la opción seleccionada del codirector
            const selectedCodirectorId = codirectorSelect.value;

            // Guardamos todas las opciones originales
            const originalOptions = Array.from(directorSelect.querySelectorAll('option'))
                .filter(opt => opt.value && opt.value !== "");

            // Limpiamos las opciones del codirector
            codirectorSelect.innerHTML = '<option value="" disabled hidden>¿Cuenta con codirector?</option>';

            // Añadimos todas las opciones excepto el director seleccionado
            originalOptions.forEach(opt => {
                if (opt.value !== selectedDirectorId) {
                    const option = document.createElement('option');
                    option.value = opt.value;
                    option.textContent = opt.textContent;
                    codirectorSelect.appendChild(option);
                }
            });

            // Restauramos el valor anterior si sigue disponible
            codirectorSelect.value = selectedCodirectorId;
            if (!Array.from(codirectorSelect.options).some(opt => opt.value === selectedCodirectorId)) {
                codirectorSelect.value = "";
            }
        };

        // Ejecutamos al inicio por si el valor ya está seleccionado
        updateCodirectorOptions();

        // Cuando cambia el director, actualizamos las opciones
        directorSelect.addEventListener('change', updateCodirectorOptions);
    });
});
