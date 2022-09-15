var login = {};
login.toLogin = function () {
    var param = {
        tname : $('#tname').val(),
        pass : $('#pass').val()
    }
    $.post('common/login', param, function (f) {
        if (f == true){
            location.href = 'common/main.html';
        }else{
            $('#msg').html('用户名或密码错误');
            $('#pass').val('');
        }

    });
}