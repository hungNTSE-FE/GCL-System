var dataTableShareLeads;
$('#sharingLeads').on('click', function(){
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
    $('#customerDistributionForm').submit();
})

$('#modalPotentailSharing').on('click', function () {
    var listSelectedPotentailId = [];
    $('input[name="potential-id"]:checked').each(function() {
        listSelectedPotentailId.push(Number(this.value));
    });

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/potential/getPotentialToShare",
        dataType: 'json',
        data: JSON.stringify(listSelectedPotentailId),
        cache: false,
        timeout: 600000,
        success: function (data) {
            render_data_potential_sharing(data);
            $('#hdnModalPotentailSharing').click();
        },
        error: function (e) {
            console.log("ERROR : ", e);
        },
    });


})

function render_data_potential_sharing(data) {
    listSelectedPotentailId = [];
    $(data.potentialSearchFormList).each(function(index, potential){
        markup = '<tr><td>' + potential.name + '</td>'
            + '<td>' + potential.phone + '</td>'
            + '<td>' + potential.email + '</td>'
            + '<td>' + potential.source + '</td>'
            + '<td>' + potential.time + '</td></tr>'
        $('#datatablesShareLeads > tbody:last-child').append(markup);
        listSelectedPotentailId.push(Number(potential.potentialID));
    });

    $('#potentialIdList').val(listSelectedPotentailId.join());
    dataTableShareLeads = new simpleDatatables.DataTable("#datatablesShareLeads", {
        perPage: 5,
        searchable: true,
        perPageSelect: false,
        labels: {
            placeholder: "Tìm kiếm đầu mối...",
        },
    });
}

$('#modalShareLead').on('hide.bs.modal', function () {
    dataTableShareLeads.destroy();
    $('#datatablesShareLeads tbody tr').each(function(){
        $(this).remove();
    });

})