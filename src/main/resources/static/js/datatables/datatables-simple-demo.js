window.addEventListener("DOMContentLoaded", (event) => {
    // Simple-DataTables
    // https://github.com/fiduswriter/Simple-DataTables/wiki

    const datatablesSimple = document.getElementById("datatablesSimple");
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }

    const dataTable = new simpleDatatables.DataTable("#datatablesSimple", {
        scrollY: "300px",
        scrollX: true,
        scrollCollapse: true,
        fixedColumns: {
            leftColumns: 1,
            rightColumns: 1,
        },
    });
});
