$(document).ready(function () {
    var sd, ed ;
    $('#startDate').datepicker({
        pickTime: false,
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        defaultDate: new Date(),
        maxDate: new Date(),
        onClose: function (){
            sd = $('#startDate').val();
            if (sd != null && sd !=='')
                $('#endDate').datepicker('option', 'minDate', new Date(sd));
        }
    });

    $('#endDate').datepicker({
        pickTime: false,
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        defaultDate: new Date(),
        onClose: function (){
            ed = $('#endDate').val();
            if (ed != null && ed !=='')
            $('#startDate').datepicker('option', 'maxDate', new Date(ed));
        }
    })

})

$('#searchDate').on('click', function(){
    var sdSelector = $('#startDate');
    var edSelector = $('#endDate');
    var parentSelector;
    var isValid = true;
    removeError();
    if (sdSelector.val() === null || sdSelector.val() === '') {
        parentSelector = sdSelector.closest('.form-validate')
        parentSelector.addClass('error');
        parentSelector.append('<small class="showErr">' + message + '</small>');
        isValid = false;
    }

    if (edSelector.val() === null || edSelector.val() === '') {
        parentSelector = edSelector.closest('.form-validate')
        parentSelector.addClass('error');
        parentSelector.append('<small class="showErr">' + message + '</small>');
        isValid = false;
    }
    if (isValid) {

        $('#form').submit();
    }
})

function removeError() {
    $('small[class="showErr"]').remove();
    $('.form-validate').removeClass('error success');
}