const activateTeacher = document.querySelectorAll('.activate');
const teacherActivate = document.querySelector('#teacherActivate');

if(activateTeacher){
    activateTeacher.forEach(function(activateTeacher){
        activateTeacher.addEventListener('click', function(){
            var usercode = this.closest('tr').querySelector('.usercode');
            var usercodeValue = usercode.textContent;
            localStorage.setItem('usercode', usercodeValue);

        });
    });
}


if(teacherActivate){
    teacherActivate.addEventListener('click', function(){
        window.location.href = '/users/activate/'+localStorage.getItem('usercode');
    });
    localStorage.clear();
}