var student = {}

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

student.toClearClass = function () {
    $('#search-grade').val('');
    $('#search-major').val('');
    $('#search-class').val('');
    $.post('student/emptyClassTemplate', {}, function (view) {
        $('#classTemplate').replaceWith(view);
    });
}

/**
 * 点击查询按钮，默认从第1页查，没有pageNo参数
 * 点击分页按钮，从指定页查，有pageNo
 *      分页查询本身也需要携带过滤条件
 * @param pageNo
 */
student.toClassQuery = function(pageNo){
    pageNo = pageNo?pageNo:1 ;

    var grade = $('#search-grade').val() ;
    var major = $('#search-major').val()
    var classNo = $('#search-class').val() ;

    if(!grade&!major&!classNo){
        alert('请输入查询条件');
        return ;
    }

    var param = {
        pageNo : pageNo,
        grade:grade,
        major:major,
        classNo:classNo
    }

    $.post("student/classTemplate.html",param,function(view){
        $('#classTemplate').replaceWith(view);
    });
}

student.toShowClassDetail = function (classInfo) {
    $('#search-classno').val(classInfo);
    this.toStudentQuery();
}

student.toClearStudent = function () {
    $('#search-sname').val('');
    $('#search-code').val('');
    $('#search-classno').val('');

    $.post('student/emptyStudentTemplate', {}, function (view) {
        $('#studentTemplate').replaceWith(view);
    });
}

student.toStudentQuery = function () {
    var classInfo = $('#search-classno').val() ;
    var code = $('#search-code').val() ;
    var sname = $('#search-sname').val() ;

    if(!classInfo&!code&!sname){
        alert('请输入学生信息查询条件') ;
        return ;
    }

    var param = {
        classInfo :classInfo,
        code:code,
        sname:sname
    }

    $.post('student/studentTemplate.html',param,function(view){
        $('#studentTemplate').replaceWith(view);
    });
}

student.toAdd = function(className){
    //className 可能有，可能没有
    $.post('student/formTemplate.html',{},function(view){
        main.showDialog({
            title:'新建学生信息',
            content:view,
            submit:function(){

            }
        });
    });
}