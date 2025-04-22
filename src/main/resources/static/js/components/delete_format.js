const deleteFormat = document.querySelectorAll('.delete');
const formatConfirm = document.querySelector('#formatConfirm');

if(deleteFormat){
    deleteFormat.forEach(function(deleteFormat){
        deleteFormat.addEventListener('click', function(){
            var id = this.closest('tr').querySelector('.id');
            var idValue = id.textContent;
            localStorage.setItem('id', idValue);

        });
    });
}


if(formatConfirm){
    formatConfirm.addEventListener('click', function(){
        window.location.href = '/formats/delete/'+localStorage.getItem('id');
    });
    localStorage.clear();
}