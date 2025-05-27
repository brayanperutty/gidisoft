document.addEventListener('DOMContentLoaded', () => {
    const fileButtons = document.querySelectorAll("[id^='fileButton-']");
    const form = document.getElementById('formProject');

    fileButtons.forEach(button => {
        const projectId = button.id.split("-")[1];
        const input = document.getElementById(`fileInput-${projectId}`);
        const fileNamesContainer = document.getElementById(`fileNamesContainer-${projectId}`);
        const fileCount = document.getElementById(`fileCount-${projectId}`);

        fileNamesContainer.style.display = "flex";
        fileNamesContainer.style.flexWrap = "wrap";
        fileNamesContainer.style.gap = "10px";

        button.addEventListener("click", () =>
            input.click());

        let files = [];
        input.addEventListener("change", () => {
            files = input.files;
            renderFileNames();
        });

        function renderFileNames() {
            fileCount.textContent = files.length > 0
                ? `${files.length} archivo(s) seleccionado(s)`
                : 'Ningún archivo seleccionado';
            fileNamesContainer.innerHTML = "";
            Array.from(files).forEach((file, index) => {
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
                    // ✅ Reconstruir fileInput.files
                    const dataTransfer = new DataTransfer();
                    files.forEach(file => dataTransfer.items.add(file));
                    input.files = dataTransfer.files;
                });

                fileItem.appendChild(removeButton);
                fileNamesContainer.appendChild(fileItem);
            });
        }

        form.addEventListener("submit", function (e) {
            if (input && input.files.length === 0) {
                input.remove(); // Elimina el input para que no se envíe
            }
        });
    });
});