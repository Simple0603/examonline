var teacher = {}
teacher.toTeacherQuery = function (curr) {
    // curr存在的话就无变化,curr不存在的话赋值为1
    curr = curr ? curr : 1;
    var tname = $('#search-tname').val();
    var param = {
        curr : curr,
        tname : tname
    };
    $.post('teacher/tableTemplate.html', param, function (view) {
        $('#tableTemplate').replaceWith(view);
    });
}

teacher.toClearTeacherQuery = function () {
    $('#search-tname').val('');
    teacher.toTeacherQuery();
}

teacher.toPageTeacherQuery = function (curr) {
    teacher.toTeacherQuery(curr);
}

teacher.toAddTeacher = function () {
    $.post("teacher/formTemplate.html", {}, function (view) {
        $('#teacher-modal-title').html('新建教师');
        $('#teacher-modal-body').html(view);
        $('#teacher-modal-submit').click(function () {
            var tname = $('#form-tname').val();
            var param = {
                tname : tname
            }
            $.post("teacher/save", param, function (f) {
                if (f == true){
                    alert('添加成功');
                    $('#teacher-modal').modal('hide');
                    $('#search-tname').val('');
                    teacher.toTeacherQuery();
                }else {
                    alert('助记码重复，添加失败');
                }
            });
            $('#teacher-modal-submit').unbind();
        });
        $('#teacher-modal').modal('show');
    });
}

teacher.toEditTeacher = function (id) {
    $.post("teacher/formTemplate.html", {id : id}, function (view) {
        $('#teacher-modal-title').html('编辑教师信息');
        $('#teacher-modal-body').html(view);
        $('#teacher-modal-submit').click(function () {
            var id = $('#id-for-edit').val();
            var tname = $('#form-tname').val();
            var param = {
                id : id,
                tname : tname
            }
            $.post("teacher/edit", param, function (f) {
                if (f == true){
                    alert('编辑成功');
                    $('#teacher-modal').modal('hide');
                    teacher.toTeacherQuery();
                }else {
                    alert('助记码重复，编辑失败');
                }
            });
            $('#teacher-modal-submit').unbind();
        });
        $('#teacher-modal').modal('show');
    });
}