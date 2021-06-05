var CAMPAIGN_TABLE_ID = '#campaignTable';
var CAMPAIGN_DETAIL_TABLE_ID = '#campaignDetailTable';
var HDN_MODAL_BUTTON_EDIT_ID = '#hdn_modal_button_edit';
var MODAL_BUTON_EDIT_ID = '#modal_button_edit';
var CAMPAIGN_MODAL_ID = '#campaignModal';
var BTN_DELETE_CAMPAIGN_DETAIL = '#btn-delete-campaign-detail';
$(document).ready(function (){
    $(HDN_MODAL_BUTTON_EDIT_ID).attr('hidden', true);
    get_main_grid_data();
});

$(document).on('click', MODAL_BUTON_EDIT_ID, function () {
    get_main_grid_modal_data();
});

$(CAMPAIGN_MODAL_ID).on('hidden.bs.modal', function () {
    clear_data_modal_table();
})

$(BTN_DELETE_CAMPAIGN_DETAIL).on('click', function (){
    delete_campaign_detail();
})
function get_main_grid_data(){
    $("#overlay").fadeIn();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/campaign/getCampaignMaketingForm",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            get_grid_campaign(data['campaignForm']);
            get_grid_campaign_detail(data['campaignDetailFormList']);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        },
        complete: function (){
            setTimeout(function(){
                $("#overlay").fadeOut(300);
            },500);
        }
    });
}
function get_grid_campaign_detail(data) {
    $(CAMPAIGN_DETAIL_TABLE_ID).DataTable( {
        scrollY: "500px",
        scrollX: true,
        scrollCollapse: true,
        paging: true,
        bInfo: false,
        columnDefs: [
            {
                "targets": 0,
                "width": "10%",
                "orderable": false,
                'checkboxes': {
                    'selectRow': true
                }
            },
        ],
        "select": {
            "style": 'multi',
            "selector": 'td:first-child'
        },
        "data": data,
        "columns": [
            { "data": "hdnCampaignCode"},
            { "data": "sourceName" },
            { "data": "startDate" },
            { "data": "endDate" },
            { "data": "content" },
            { "data": "status" },
            { "data": "assumptionResult"},
            { "data": "result" },
            { "data": "budget" },
            { "data": "actualExpense" },
            { "data": "averageExpense" },
        ]
    } );

}
function get_grid_campaign(data) {
    $(CAMPAIGN_TABLE_ID).DataTable( {
        scrollY: "500px",
        searching: false,
        bLengthChange: false,
        bInfo:false,
        bPaginate: false,
        scrollCollapse: true,
        "data": data,
        "columns": [
            { "data": "sourceName" },
            { "data": "totalResult" },
            { "data": "totalBudget" },
            { "data": "totalActualExpense" },
            { "data": "totalAverageExpense" },
        ]
    } );
}
function get_main_grid_modal_data(){
    var listSelectedId = [];
    var rows_selected = $(CAMPAIGN_DETAIL_TABLE_ID).DataTable().column(0).checkboxes.selected();
    $.each(rows_selected, function (index, rowId) {
        listSelectedId.push(rowId);
    });

    if (listSelectedId.length === 0) {
        alert("Vui lòng chọn chiến dịch cần chỉnh sửa!");
        return;
    }

    if (listSelectedId.length > 1) {
        alert("Vui lòng chọn 1 chiến địch để chỉnh sửa!");
        return;
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        data: listSelectedId[0],
        url: "/campaign/getListCampaignModal",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            render_data_modal_table(data);
            $(HDN_MODAL_BUTTON_EDIT_ID).click();
        },
        error: function (e) {
            console.log("ERROR : ", e);
        },
    });
}
function render_data_modal_table(data) {
    $('#txt_source_id').val(data.hdnSourceId);
    $('#txt_campaign_code').val(data.hdnCampaignCode);
    $('#txt_campaign').val(data.sourceName);
    $('#txt_start_date').val(data.startDate);
    $('#txt_end_date').val(data.endDate);
    $('#txt_content').val(data.content);
    $('#txt_status').val(data.status);
    $('#txt_assumption_result').val(data.assumptionResult);
    $('#txt_result').val(data.result);
    $('#txt_budget').val(data.budget);
    $('#txt_actutal_expense').val(data.actualExpense);
    $('#txt_ave_expense').val(data.averageExpense);
}
function clear_data_modal_table(){
    $('.modal-body input').each(function(index, el){
        $('#' + el.id).val('');
    });
}
function delete_campaign_detail(){
    var listSelectedId = [];
    var rows_selected = $(CAMPAIGN_DETAIL_TABLE_ID).DataTable().column(0).checkboxes.selected();
    $.each(rows_selected, function (index, rowId) {
        listSelectedId[index] = rowId;
    });
    if (listSelectedId.length === 0) {
        window().alert("Vui lòng chọn chiến dịch!")
        return;
    }

    if(!confirm("Bạn có chắc chắn xoá các chiến dịch đã chọn không ?")) return;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        data: listSelectedId.toString(),
        url: "/campaign/deteleCampaignDetail",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function () {
            window.location.href = "http://localhost:8081/campaign";
        },
        error: function (e) {
            console.log("ERROR : ", e);
        },
    });
}


