export function bindComplianceInput(input) {
    input.addEventListener('input', () => {
        let value = input.value.replace(/\D/g, '');
        value = value.slice(0, 3);
        const numeric = Math.min(Math.max(parseInt(value || 0), 0), 100);
        input.value = isNaN(numeric) ? '' : numeric;
    });
}

// Opcional: función para aplicar a todos los inputs existentes
export function bindAllComplianceInputs() {
    document.querySelectorAll('input[name="compliancePercentage"]').forEach(bindComplianceInput);
}