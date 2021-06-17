var CAMPAIGN_TABLE_ID = '#campaignTable';
var CAMPAIGN_DETAIL_TABLE_ID = '#campaignDetailTable';
var HDN_MODAL_BUTTON_EDIT_ID = '#hdn_modal_button_edit';
var MODAL_BUTON_EDIT_ID = '#modal_button_edit';
var CAMPAIGN_MODAL_ID = '#campaignModal';
var BTN_DELETE_CAMPAIGN_DETAIL = '#btn-delete-campaign-detail';
var CHK_BOX_BUTTON = '.campaignCheckbox';
var CHK_BOX_ALL_BUTTON = '#chk_all_campaign';

$(document).ready(function (){
    $(HDN_MODAL_BUTTON_EDIT_ID).attr('hidden', true);
    $(CAMPAIGN_MODAL_ID).on('hidden.bs.modal', function () {
        clear_data_modal_table();
    })

    $(BTN_DELETE_CAMPAIGN_DETAIL).on('click', function (){
        delete_campaign_detail();
    })

    $(CHK_BOX_ALL_BUTTON).on('click', function(){
        var flag_chk_all = this.checked;
        $.each($(CHK_BOX_BUTTON), function(index, chk) {
            chk.checked = flag_chk_all;
        });
    })
    get_main_grid_data();
});

$(document).on('click', MODAL_BUTON_EDIT_ID, function () {
    get_main_grid_modal_data();
});
function getSelectedId() {
    var listSelectedId = [];
    var rows_selected = $('.campaignCheckbox:checkbox:checked');
    $.each(rows_selected, function (index, el) {
        listSelectedId.push(el.value);
    });
    return listSelectedId;
}
function campaignClick(){
    if ($(CHK_BOX_BUTTON).length === $('.campaignCheckbox:checkbox:checked').length) {
        $(CHK_BOX_ALL_BUTTON).prop("checked", true);
    } else {
        $(CHK_BOX_ALL_BUTTON).prop("checked", false);
    }
}
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
                'targets': 0,
                'searchable':false,
                'orderable':false,
            },
        ],
        "data": data,
        "columns": [
            { "data": "hdnCampaignCode"
                , 'render': function (data, type, full, meta){
                                return '<input type="checkbox" ' +
                                    'class="campaignCheckbox"' +
                                    'value="' + data +'" ' +
                                    'onclick="campaignClick()"' +
                                    'id="chk_' + data +'">'
                                    ;
                            }
            },
            { "data": "sourceName" },
            { "data": "startDate" },
            { "data": "endDate" },
            { "data": "content" },
            { "data": "status" },
            { "data": "assumptionResult"},
            { "data": "result" },
            { "data": "budget",
                render: $.fn.dataTable.render.number( ',', null, 0, null )
            },
            { "data": "actualExpense",
                render: $.fn.dataTable.render.number( ',', null, 0, null )
            },
            { "data": "averageExpense",
                render: $.fn.dataTable.render.number( ',', '.', 2, null )
            },
        ],
        order: [[1, "asc"]]
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
    var listSelectedId = getSelectedId();

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
    var listSelectedId = getSelectedId();

    if (listSelectedId.length === 0) {
        alert("Vui lòng chọn chiến dịch!");
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


