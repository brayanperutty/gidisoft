const publishFormat = document.querySelectorAll('.publishFormat');
const publishFormatConfirm = document.querySelector('#publishFormat');

if(publishFormat){
    publishFormat.forEach(function(publishFormat){
        publishFormat.addEventListener('click', function(){
            const id = this.closest('tr').querySelector('.id');
            const idValue = id.textContent;
            localStorage.setItem('id', idValue);

        });
    });
}


if(publishFormatConfirm){
    publishFormatConfirm.addEventListener('click', function(){
        window.location.href = '/formats/' +localStorage.getItem('id') + '/publish';
    });
    localStorage.clear();
}