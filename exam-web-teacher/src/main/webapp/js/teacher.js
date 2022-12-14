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

teacher.toCheckAll = function () {
    var check = $('#teacherGrid thead :checkbox').prop('checked');
    $('#teacherGrid tbody :checkbox').prop('checked', check);
}

teacher.toDeleteAll = function () {
    var len = $('#teacherGrid tbody :checked').length;
    if (len == 0){
        alert("请至少选中一条要删除的记录");
        return ;
    }
    if (!confirm('是否确认删除选中的【' + len + '】条记录？')){
        return;
    }

    var ids = '';
    $('#teacherGrid tbody :checked').each(function (i, element) {
        ids += element.value + ',';
    });
    $.post("teacher/deleteAll", {ids : ids}, function () {
        alert("删除成功");
        teacher.toPageTeacherQuery($('.pagination .active').text());
    });
}

teacher.toImport = function () {
    var uploading = false ;
    $.post("teacher/importTemplate", {}, function (view) {
        $('#teacher-modal-title').html('导入教师信息');
        $('#teacher-modal-body').html(view);
        $('#teacher-modal-submit').click(function () {
            var fileInfo = $('#import-excel').val();
            if(!fileInfo){
                alert('请选择要上传的excel文件');
                return ;
            }
            if(uploading){
                alert("文件正在上传中，请稍候");
                return false;
            }
            $.ajax({
                url: 'teacher/importTeachers',
                type: 'POST',
                cache: false,
                data: new FormData($('#teacher-import-form')[0]),
                processData: false,
                contentType: false,
                beforeSend: function () {
                    uploading = true;
                },
                success: function (msg) {
                    uploading = false;
                    msg = msg.replace(/\|/g,"\r\n");
                    alert(msg) ;
                    $('#teacher-modal').modal('hide');
                    teacher.toClearTeacherQuery();
                }
            });
            $('#teacher-modal-submit').unbind();
        });
        $('#teacher-modal').modal('show');
    });
}

teacher.changeFile = function (fileInfo) {
    var fileType = fileInfo.split('.')[1];
    if (fileType != 'xls' && fileType != 'xlsx') {
        alert('请选择excel文件');
    }else {
        $('#file-msg').text(fileInfo);
    }
}