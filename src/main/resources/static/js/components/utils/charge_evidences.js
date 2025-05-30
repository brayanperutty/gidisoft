document.addEventListener('DOMContentLoaded', () => {
    // Selecciona todos los contenedores que tengan carga de archivos (proyectos, participaciones, etc)
    const blocks = document.querySelectorAll('.project-block, .participation-block'); // ajusta según clases

    blocks.forEach(block => {
        const input = block.querySelector('.fileInput');
        const button = block.querySelector('.fileButton');
        const count = block.querySelector('.fileCount');
        const namesContainer = block.querySelector('.fileNamesContainer');

        if (!input || !button || !count || !namesContainer) return;

        namesContainer.style.display = "flex";
        namesContainer.style.flexWrap = "wrap";
        namesContainer.style.gap = "10px";

        button.addEventListener('click', () => input.click());

        let files = [];

        input.addEventListener('change', () => {
            files = Array.from(input.files);
            renderFileNames();
        });

        function renderFileNames() {
            count.textContent = files.length > 0
                ? `${files.length} archivo(s) seleccionado(s)`
                : 'Ningún archivo seleccionado';

            namesContainer.innerHTML = "";

            files.forEach((file, index) => {
                const fileItem = document.createElement("div");
                fileItem.textContent = file.name;

                Object.assign(fileItem.style, {
                    backgroundColor: "#f0f0f0",
                    borderRadius: "15px",
                    padding: "5px 10px",
                    color: "#333",
                    fontSize: "0.875rem",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "space-between",
                    width: "max-content"
                });

                const removeButton = document.createElement("span");
                removeButton.innerHTML = "&times;";
                removeButton.style.cursor = "pointer";
                removeButton.style.marginLeft = "10px";

                removeButton.addEventListener("click", () => {
                    files.splice(index, 1);
                    renderFileNames();

                    const dataTransfer = new DataTransfer();
                    files.forEach(f => dataTransfer.items.add(f));
                    input.files = dataTransfer.files;
                });

                fileItem.appendChild(removeButton);
                namesContainer.appendChild(fileItem);
            });
        }

        // Opcional: evitar enviar input vacío en form si fuera necesario
        const form = block.closest('form');
        if (form) {
            form.addEventListener('submit', () => {
                if (input.files.length === 0) {
                    input.remove();
                }
            });
        }
    });
});
