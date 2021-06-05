var CAMPAIGN_MODAL_ID = '#campaignModal';
$(document).ready(function (){
});

$("#modalForm").submit(function(event){
    event.preventDefault(); //prevent default action
    var post_url = $(this).attr("action"); //get form action url
    var request_method = $(this).attr("method"); //get form GET/POST method
    var form_data = {
        'hdnCampaignCode': $('#txt_campaign_code').val(),
        'hdnSourceId': $('#txt_source_id').val(),
        'sourceName': $('#txt_campaign').val(),
        'startDate': $('#txt_start_date').val(),
        'endDate': $('#txt_end_date').val(),
        'content': $('#txt_content').val(),
        'status': $('#txt_status').val(),
        'assumptionResult': $('#txt_assumption_result').val(),
        'result': $('#txt_result').val(),
        'budget': $('#txt_budget').val(),
        'actualExpense': $('#txt_actutal_expense').val(),
        'averageExpense': $('#txt_ave_expense').val()
    }
    $.ajax({
        url : post_url,
        type: request_method,
        data : form_data
    }).success(function(response){
        window.location.href = "http://localhost:8081/campaign";
    }).error(function (res){
        console.log("ERROR...");
    });
});