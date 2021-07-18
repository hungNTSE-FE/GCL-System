

var URL_SAVE_CUSTOMER = '/customer/saveCustomer';
var URL_REGISTER_CUSTOMER = '/customer/registerCustomer';
var URL_UPDATE_CUSTOMER = '/customer/updateCustomer';

var CUSTOMER_FORM_ID = '#customerForm';
var BTN_REGISTER_CUSTOMER_ID = '#registerCustomer';
var BTN_UPDATE_CUSTOMER_ID = '#updateCustomer';
var BTN_SAVE_CUSTOMER_ID = '#saveCustomer';

var CUSTOMER_FORM = {
    BROKER_CMB_ID : '#brokerCmb',
    SOURCE_CMB_ID : '#sourceCmb',
    GENDER_CMB_ID : '#genderCmb',
    CUSTOMER_STS_ID : '#customerStatusCmb',
    CREATE_DATE_PICKER_ID : '#createDatePicker',
    DATE_OF_ISSUE_PICKER_ID : '#dateOfIssuePicker',
    HDN_SOURCE_ID : '#hdnSourceId',
    HDN_EMPLOYEE_ID : '#hdnEmployeeId',
    HDN_CUSTOMER_STATUS_ID: '#customerStatus',
    GENDER_ID : '#gender',
    DATE_OF_BIRTH_ID : '#dateOfBirth',
    DATE_OF_ISSUE_ID : '#dateOfIssue',
}

$(document).ready(function () {
    init();
});

function init(){
    var hdnSourceId = $(CUSTOMER_FORM.HDN_SOURCE_ID).val();
    var hdnCusSts = $(CUSTOMER_FORM.HDN_CUSTOMER_STATUS_ID).val();

    if (checkNulankUndefined(hdnSourceId)) {
        $(CUSTOMER_FORM.SOURCE_CMB_ID).val(hdnSourceId);
    }

    if (checkNulankUndefined(hdnCusSts)) {
        $(CUSTOMER_FORM.CUSTOMER_STS_ID).val(hdnCusSts);
    }
}

$(BTN_REGISTER_CUSTOMER_ID).on('click', function (){
    registerCustomer();
});

$(BTN_UPDATE_CUSTOMER_ID).on('click', function (){
    registerCustomer();
});

$(BTN_SAVE_CUSTOMER_ID).on('click', function(){
    saveCustomer();
})

function registerCustomer(){
    // Remove error before submit form
    removeError();

    var validateRules = [
        {
            'customerName' : 'Tên không thể trống'
        },
        {
            'phoneNumber' : 'Số điện thoại không thể trống'
        },
        {
            'email' : 'Email không thể trống'
        },
        {
            'identifyNumber' : 'Căn cước công dân không thể trống'
        },
        {
            'idImageBefore' : 'Vui lòng chọn mặt trước CMND'
        },
        {
            'idImageAfter' : 'Vui lòng chọn mặt sau CMND'
        },
        {
            'dateOfBirth' : 'Ngày sinh không thể trống'
        },
        {
            'dateOfIssue' : 'Ngày cấp không thể trống'
        },
        {
            'bankName' : 'Tên ngân hàng không thể trống'
        },
        {
            'ownerBanking' : 'Tên chủ khoản không thể trống'
        },
        {
            'bankNumber' : 'Số tài khoản không thể trống'
        },
    ];

    if(!validate(validateRules) || !validateDateTime()) {
        return;
    }

    setValueForm();
    $(CUSTOMER_FORM_ID).attr('action', URL_REGISTER_CUSTOMER);
    $(CUSTOMER_FORM_ID).submit();
}

function setValueForm() {
    var hdnSelectedEmployeeId = $(CUSTOMER_FORM.BROKER_CMB_ID).val();
    var hdnSelectedSourceId = $(CUSTOMER_FORM.SOURCE_CMB_ID).val();
    var hdnSelectedGender = $(CUSTOMER_FORM.GENDER_CMB_ID).val();
    var hdnSelectedCustomerSts = $(CUSTOMER_FORM.CUSTOMER_STS_ID).val();

    $(CUSTOMER_FORM.HDN_EMPLOYEE_ID).val(hdnSelectedEmployeeId);
    $(CUSTOMER_FORM.HDN_SOURCE_ID).val(hdnSelectedSourceId);
    $(CUSTOMER_FORM.GENDER_ID).val(hdnSelectedGender);
    $(CUSTOMER_FORM.HDN_CUSTOMER_STATUS_ID).val(hdnSelectedCustomerSts);
}

function checkNulankUndefined(obj) {
    return obj !== undefined && obj !== null && obj.length > 0;
}

function validate(validateRule){
    if (validateRule === undefined) validateRule = [];
    var check = true;
    $.each(validateRule, function (){
        $.each(this, function (id, message){
            var selectedEl = $('#' + id);
            var parentSelector = selectedEl.closest('.form-validate');
            if (!checkNulankUndefined(selectedEl.val())) {
                parentSelector.addClass('error');
                parentSelector.append('<small class="showErr">' + message + '</small>');
                check = false;
            } else {
                parentSelector.addClass('success');
            }
        });
    });
    return check;
}

function removeError() {
    $('small[class="showErr"]').remove();
    $('.form-validate').removeClass('error success');
}

function validateDateTime() {
    var birthDate = $(CUSTOMER_FORM.DATE_OF_BIRTH_ID).val();
    var issueDate = $(CUSTOMER_FORM.DATE_OF_ISSUE_ID).val();
    var currentDate = new Date();
    var parentSelector;
    var errMessage;
    // if ( Date.parse(birthDate) >= issueDate || Date.parse(birthDate) >= Date.parse(currentDate)) {
    //     errMessage = 'Ngày sinh phải nhỏ hơn ngày cấp và ngày hiện tại';
    //     parentSelector = $(CUSTOMER_FORM.DATE_OF_BIRTH_ID).closest('.form-validate');
    //     if (parentSelector.hasClass('error')) {
    //         parentSelector.append('<small class="showErr">' + errMessage + '</small>');
    //     } else {
    //         parentSelector.addClass('error');
    //         parentSelector.append('<small class="showErr">' + errMessage + '</small>');
    //     }
    //     return false;
    // }

    // if ( Date.parse(issueDate) > Date.parse(currentDate)) {
    //     errMessage = 'Ngày ngày cấp phải nhỏ hơn ngày hiện tại';
    //     parentSelector = $(CUSTOMER_FORM.DATE_OF_BIRTH_ID).closest('.form-validate');
    //     if (parentSelector.hasClass('error')) {
    //         parentSelector.append('<small class="showErr">' + errMessage + '</small>');
    //     } else {
    //         parentSelector.addClass('error');
    //         parentSelector.append('<small class="showErr">' + errMessage + '</small>');
    //     }
    //     return false;
    // }

    return true;
}

function saveCustomer(){
    // Remove error before submit form
    removeError();

    if (!checkNulankUndefined($('#customerName').val()) && !checkNulankUndefined($('#phoneNumber').val())
            && !checkNulankUndefined($('#email').val())) {
        alert("Vui lòng nhập tên, số điện thoại hoặc email!");
        return;
    }
    setValueForm();
    $(CUSTOMER_FORM_ID).attr('action', URL_SAVE_CUSTOMER);
    $(CUSTOMER_FORM_ID).submit();
}