var common = {}
common.exit = function () {
    if (! confirm('是否确认退出系统？')){
        return ;
    }
    location.href='common/exit';
}
common.toUpdatePwd = function () {
    $.post('common/updatePwdTemplate.html', {}, function (view) {
       $('#modal-body').html(view);
       $('#myModal').modal('show');
    });
}
common.updatePwd = function () {
    var oldpass = $('#old-pass').val();
    var newpass = $('#new-pass').val();
    var repass = $('#re-pass').val();
    if (newpass != repass){
        alert('两次密码输入不一致');
        return ;
    }
    var param = {
        oldpass : oldpass,
        newpass : newpass,
    };
    $.post('common/updatePwd', param, function (f) {
        if (f == false){
            alert('原密码错误');
        }else {
            alert('密码修改成功');
            $('#myModal').modal('hide');
        }
    });
}
