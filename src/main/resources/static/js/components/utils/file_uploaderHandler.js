// fileUploadHandler.js
export function setupFileUpload(form, timestamp) {
    const fileInput = form.querySelector(`#fileInput-${timestamp}`);
    const fileNamesContainer = form.querySelector(`#fileNamesContainer-${timestamp}`);
    const fileButton = form.querySelector(`#fileButton-${timestamp}`);
    const fileCount = form.querySelector(`#fileCount-${timestamp}`);

    fileNamesContainer.style.display = "flex";
    fileNamesContainer.style.flexWrap = "wrap";
    fileNamesContainer.style.gap = "10px";

    fileButton.addEventListener('click', () => fileInput.click());

    let selectedFiles = [];

    if (fileInput != null) {
        fileInput.addEventListener("change", function (event) {
            selectedFiles = Array.from(event.target.files);
            renderFileNames();
        });
    }

    function renderFileNames() {
        fileNamesContainer.innerHTML = "";

        fileCount.textContent = selectedFiles.length > 0
            ? `${selectedFiles.length} archivo(s) seleccionado(s)`
            : 'Ningún archivo seleccionado';

        selectedFiles.forEach((file, index) => {
            const fileDiv = document.createElement("div");
            fileDiv.textContent = file.name;

            Object.assign(fileDiv.style, {
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
                selectedFiles.splice(index, 1);
                renderFileNames();
                const dataTransfer = new DataTransfer();
                selectedFiles.forEach(file => dataTransfer.items.add(file));
                fileInput.files = dataTransfer.files;
            });

            fileDiv.appendChild(removeButton);
            fileNamesContainer.appendChild(fileDiv);
        });
    }

    form.addEventListener("submit", function (e) {
        if (fileInput && fileInput.files.length === 0) {
            fileInput.remove();
        }
    });
}
