const input = document.querySelector('input[name="compliancePercentage"]');

input.addEventListener('input', () => {
    let value = input.value.replace(/\D/g, ''); // solo dígitos
    value = value.slice(0, 3); // máximo 3 caracteres
    const numeric = Math.min(Math.max(parseInt(value || 0), 0), 100);
    input.value = isNaN(numeric) ? '' : numeric;
})