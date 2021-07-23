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


        let checkValidate = true;
        const nameValue = name.value.trim();
        const startDateValue = startDate.value.trim();
        const endDateValue = endDate.value.trim();
        if(nameValue ==''){
            setErrorFor(name,"Hãy nhập tên công việc");
            checkValidate = false;


        }else {
            setSuccessFor(name);
        }

        if(startDateValue ==''){
            checkValidate = false;
            setErrorFor(startDate,"Hãy nhập ngày bắt đầu");


        }else {
            setSuccessFor(startDate);
        }
        if(endDateValue == ''){
            setErrorFor(endDate,"Hãy nhập ngày kết thúc");
            checkValidate = false

        }else {
            setSuccessFor(endDate);
        }





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

    if(checkValidate == false){
       return;
    }

    $('#form').submit();


    //


})
$('#updateTask').on('click', function(){
    let checkValidate = true;
    const nameValue = name.value.trim();
    const startDateValue = startDate.value.trim();
    const endDateValue = endDate.value.trim();
    if(nameValue ==''){
        setErrorFor(name,"Hãy nhập tên công việc");
        checkValidate = false;


    }else {
        setSuccessFor(name);
    }

    if(startDateValue ==''){
        checkValidate = false;
        setErrorFor(startDate,"Hãy nhập ngày bắt đầu");


    }else {
        setSuccessFor(startDate);
    }
    if(endDateValue == ''){
        setErrorFor(endDate,"Hãy nhập ngày kết thúc");
        checkValidate = false

    }else {
        setSuccessFor(endDate);
    }





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

    if(checkValidate == false){
        return;
    }




    //


    $('#form').submit();
})

function setErrorFor(input,message) {
    const  inputBox=input.parentElement;

    const small = inputBox.querySelector('small');
    small.innerText = message;
    inputBox.className='col-md-6 form-validate error';

}
function setSuccessFor(input) {
    const  inputBox=input.parentElement;


    inputBox.className='col-md-6 form-validate success';

}
function render_data_employee(data) {
    $.each(data, function (index, emp){
        $('#lstBox1').append(`<option value="${emp.id}">${emp.name}</option>`);
    })
}