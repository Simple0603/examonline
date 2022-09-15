var login = {};
login.toLogin = function () {
    var param = {
        tname : $('#tname').val(),
        pass : $('#pass').val()
    }
    $.post('common/login.html', param, function (f) {
        if (f == true){
            alert('登录成功');
            location.href = 'common/main.html';
        }else{
            $('#msg').html('用户名或密码错误');
            $('#pass').val('');
        }

    })
}