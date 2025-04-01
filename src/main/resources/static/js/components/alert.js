document.addEventListener("DOMContentLoaded", function () {
    let errorToast = document.getElementById("errorToast");
    if (errorToast.querySelector("span").textContent.trim() !== "") {
        let toast = new bootstrap.Toast(errorToast);
        toast.show();
    }
});

document.addEventListener("DOMContentLoaded", function () {
    let successToast = document.getElementById("successToast");
    if (successToast.querySelector("span").textContent.trim() !== "") {
        let toast = new bootstrap.Toast(successToast);
        toast.show();
    }
});