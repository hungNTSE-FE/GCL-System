$(document).ready(function () {
    var table = $("#example").DataTable({
        scrollY: "700px",
        scrollX: true,
        scrollCollapse: true,
        paging: true,
        fixedColumns: true,
        fixedHeader: {
            leftColumn: 2,
        },
        columnDefs: [
            {
                targets: 0,
            },
        ],
        order: [[1, "asc"]],
    });
});