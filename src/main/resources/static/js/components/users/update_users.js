document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("addTeacherModal");
    const form = document.getElementById("addTeacherForm");

    document.querySelectorAll(".edit-teacher").forEach(button => {
        button.addEventListener("click", () => {
            modal.querySelector("#addTeacherModalLabel").textContent = "Editar Docente";

            form.action = "/users/save"; // Cambia si tu ruta es distinta
            form.method = "post"; // o "put" si lo manejas con PUT

            form.querySelector("#teacherId").value = button.dataset.id;
            form.querySelector("#usercode").value = button.dataset.usercode;
            form.querySelector("#name").value = button.dataset.name;
            form.querySelector("#email").value = button.dataset.email;
            form.querySelector("#phoneNumber").value = button.dataset.phone;
            form.querySelector("#role").value = button.dataset.roleid;
        });
    });

    // Limpia el formulario si se abre para crear
    document.querySelector("[data-bs-target='#addTeacherModal']").addEventListener("click", () => {
        modal.querySelector("#addTeacherModalLabel").textContent = "Agregar Nuevo Docente";

        form.action = "/users/save";
        form.method = "post";

        form.reset();
        form.querySelector("#teacherId").value = "";
    });
});