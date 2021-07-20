$('#selectDepartment').on('change', function(){
    $('#lstBox1').empty();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/task/getEmployeeByDepartmentId",
        dataType: 'json',
        data: $(this).val(),
        cache: false,
        timeout: 600000,
        success: function (data) {
            render_data_employee(data);
        },
        error: function (e) {
            // window.location.href = "http://localhost:8081/error/error-400.html";
        },
    });
})
$('#createTask').on('click', function(){
    var optsListBox2 = $('#lstBox2 option:not([disabled])');
    if (optsListBox2.length === 0) {
        alert("Vui lòng chọn nhân viên để chia");
        return;
    }
    var listSelectedEmployeId = [];

    $.each(optsListBox2, function(){
        listSelectedEmployeId.push(Number($(this).val()));
    })
    $('#empIdList').val(listSelectedEmployeId.join());
    $('#form').submit();
})
$('#updateTask').on('click', function(){
    var optsListBox2 = $('#lstBox2 option:not([disabled])');
    if (optsListBox2.length === 0) {
        alert("Vui lòng chọn nhân viên để chia");
        return;
    }
    var listSelectedEmployeId = [];

    $.each(optsListBox2, function(){
        listSelectedEmployeId.push(Number($(this).val()));
    })
    $('#empIdList').val(listSelectedEmployeId.join());
    $('#taskForm').submit();
})
function render_data_employee(data) {
    $.each(data, function (index, emp){
        $('#lstBox1').append(`<option value="${emp.id}">${emp.name}</option>`);
    })
}