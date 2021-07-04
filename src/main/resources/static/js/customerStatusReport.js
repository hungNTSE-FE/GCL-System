var CUSTOMER_STS_RPT_TABLE_ID = '#customerStatusReportTable';
var CUSTOMER_STS_EST_TABLE_ID = '#customerStatusEvaluationTable';
var SEARCH_DATE = '#searchDate';
var FROM_DATE = '#fromDate';
var TO_DATE = '#toDate';
var fromDate, toDate;
$(document).ready(function (){
    $(FROM_DATE).datepicker({
        pickTime: false,
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        maxDate: new Date(),
        onClose: function (){
            fromDate = $(FROM_DATE).val();
            if (fromDate != null && fromDate !=='')
                $(TO_DATE).datepicker('option', 'minDate', new Date(fromDate));
        }
    });

    $(TO_DATE).datepicker({
        pickTime: false,
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        onClose: function (){
            toDate = $(TO_DATE).val();
            if (toDate != null && toDate !=='')
                $(FROM_DATE).datepicker('option', 'maxDate', new Date(toDate));
        }
    })
})

$(SEARCH_DATE).on('click', function (){
    doLoadTable();
})

function doLoadTable(){
    var fromDate, toDate;
    fromDate = $(FROM_DATE).val();
    toDate = $(TO_DATE).val();

    var data = {
        'fromDate' : fromDate,
        'toDate' : toDate
    }

    $('#overlay').fadeIn();
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: '/marketing/getListCustomerStatusReport',
        dataType: 'json',
        data: data,
        cache: false,
        timeout: 600000,
        success: function (data) {
            renderDataTable(data);
        },
        error: function (e) {
            console.log('ERROR : ', e);
        },
        complete: function (){
            setTimeout(function(){
                $('#overlay').fadeOut(300);
            },500);
        }
    });
}

function renderDataTable(data){
    $('.customerStatusReportArea').show();
    $(CUSTOMER_STS_RPT_TABLE_ID).DataTable( {
        scrollY: '500px',
        scrollX: true,
        scrollCollapse: true,
        paging: false,
        bInfo: false,
        data: data['customerStatusFormList'],
        columns: [
            { "data": "stt" },
            { "data": "emp_name" },
            { "data": "level_1" },
            { "data": "level_2" },
            { "data": "level_3" },
            { "data": "level_4"},
            { "data": "level_5" },
            { "data": "level_6"},
            { "data": "level_7"}
        ],
        order: [[0, 'asc']]
    } );
    $(CUSTOMER_STS_EST_TABLE_ID).DataTable( {
        scrollY: '500px',
        scrollX: true,
        scrollCollapse: true,
        paging: false,
        bInfo: false,
        data: data['customerStatusEvaluationFormList'],
        columns: [
            { "data": "stt" },
            { "data": "emp_name" },
            { "data": "num_of_registered_account" },
            { "data": "num_of_top_up_account" },
            { "data": "num_of_lot" }
        ],
        order: [[0, 'asc']]
    } );
}