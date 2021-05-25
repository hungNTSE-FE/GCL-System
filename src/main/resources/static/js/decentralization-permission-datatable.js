$(document).ready(function () {
    var table = $("#example").DataTable({
        bLengthChange: false,
        searching: false,
        info: false,
        columnDefs: [
            {
                targets: 0,
                checkboxes: true,
            },
        ],
        order: [[1, "asc"]],
    });
});