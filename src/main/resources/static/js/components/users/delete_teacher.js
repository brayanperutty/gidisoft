const deleteTeacher = document.querySelectorAll('.delete');
const teacherConfirm = document.querySelector('#teacherConfirm');

if(deleteTeacher){
    deleteTeacher.forEach(function(deleteTeacher){
        deleteTeacher.addEventListener('click', function(){
            var usercode = this.closest('tr').querySelector('.usercode');
            var usercodeValue = usercode.textContent;
            localStorage.setItem('usercode', usercodeValue);

        });
    });
}


if(teacherConfirm){
    teacherConfirm.addEventListener('click', function(){
        window.location.href = '/users/delete/'+localStorage.getItem('usercode');
    });
    localStorage.clear();
}