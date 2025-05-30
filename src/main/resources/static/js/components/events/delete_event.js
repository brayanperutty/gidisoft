const deleteEvent = document.querySelectorAll('.deleteEvent');
const deleteEventConfirm = document.querySelector('#deleteEventConfirm');

if(deleteEvent){
    deleteEvent.forEach(function(button){
        button.addEventListener('click', function(){
            const form = this.closest('form'); // el contenedor más directo

            const eventIdInput = form.querySelector('.eventId');
            const formatIdInput = form.querySelector('.formatId');

            const eventId = eventIdInput?.value;
            const formatId = formatIdInput?.value;

            if (eventId && formatId) {
                localStorage.setItem('eventId', eventId);
                localStorage.setItem('formatId', formatId);
            }
        });
    });
}

if(deleteEventConfirm){
    deleteEventConfirm.addEventListener('click', function(){
        const eventId = localStorage.getItem('eventId');
        const formatId = localStorage.getItem('formatId');

        if (eventId && formatId) {
            window.location.href = `/events/${eventId}/delete?formatId=${formatId}`;

            // Limpiamos después de redirigir
            localStorage.removeItem('eventId');
            localStorage.removeItem('formatId');
        }
    });
}