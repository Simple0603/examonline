var main = {}
main.exit = function () {
    if (! confirm('是否确认退出系统？')){
        return ;
    }
    location.href='common/exit';
}
main.toUpdatePwd = function () {
    $.post('common/updatePwdTemplate.html', {}, function (view) {
       $('#modal-body').html(view);
       $('#myModal').modal('show');
    });
}
main.updatePwd = function () {
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

/**
 * eg.
 * showDialog({
 *      title:'新建教师信息',
 *      content:ajax-view,
 *      submit:function(){
 *
 *      }
 * });
 * @param config {title , content , submit}
 */
main.showDialog = function(config){
    $('#common-modal-title').html(config.title);
    $('#common-modal-body').html(config.content);
    $('#common-modal-submit').click(function () {
        config.submit();
    });
    $('#common-modal').modal('show');
}

main.closeDialog = function () {
    $('#common-modal').modal('hide');
}

main.removeSubmit = function () {
    $('#common-modal-submit').unbind();
}

