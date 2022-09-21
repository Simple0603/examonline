var student = {}

student.toStudentQuery = function () {

}

student.toClearStudentQuery = function () {

}

student.changeFile = function (fileInfo) {
    var fileType = fileInfo.split('.')[1];
    if (fileType != 'xls' && fileType != 'xlsx') {
        alert('请选择excel文件');
    }else {
        $('#file-msg').text(fileInfo);
    }
}

student.toImport = function () {
    $.post("student/importTemplate", {}, function (view) {
        var uploading = false ;
        var config = {
            title : '导入学生信息',
            content : view,
            submit : function () {
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
                    url: 'student/importStudents',
                    type: 'POST',
                    cache: false,
                    data: new FormData($('#student-import-form')[0]),
                    processData: false,
                    contentType: false,
                    beforeSend: function () {
                        uploading = true;
                    },
                    success: function (msg) {
                        uploading = false;
                        msg = msg.replace(/\|/g,"\r\n");
                        alert(msg) ;
                        main.closeDialog();
                        main.removeSubmit();
                        student.toClearStudentQuery();
                    }
                });
            }
        };
        main.showDialog(config);
    });
}