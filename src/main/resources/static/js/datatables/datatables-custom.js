let urlNewStatistic = 'http://localhost:8085/statistic/potential/today';

if (document.getElementById("datatablesStatisticPotential") != null) {
    let dataTableStatisticPotential = new simpleDatatables.DataTable("#datatablesStatisticPotential", {
        fixedHeight: true,
        searchable: false,
        perPageSelect: false,
        perPage: 3,
    });
    fetch(urlNewStatistic).then(
        res=>{
            res.json().then(
                data=>{
                    console.log(data);
                    if (data.length > 0) {
                        var slag = data.map(doc => Object.values(doc));
                        dataTableStatisticPotential.rows().add(slag);
                    }
                }
            )
        }
    )
} else if (document.getElementById("datatablesTakeCareOfPotential") != null) {
    const dataTableTakeCare = new simpleDatatables.DataTable(
        "#datatablesTakeCareOfPotential",
        {
            fixedHeight: true,
            searchable: false,
            perPageSelect: false,
            perPage: 3,
        }
    );
} else if (document.getElementById("datatablesManageTakeCareOfPotential") != null) {
    const datatablesTakeCareOfPotential = new simpleDatatables.DataTable(
        "#datatablesManageTakeCareOfPotential",
        {
            searchable: false,
            fixedHeight: true,
            perPageSelect: false,
            labels: {
                info: "",
            }
        }
    );
} else if (document.getElementById("datatablesPotentials") != null) {
    const dataTablePotential = new simpleDatatables.DataTable("#datatablesPotentials", {
        fixedHeight: true,
        fixedColumns: true,
        labels: {
            placeholder: "Tìm kiếm đầu mối...",
            perPage: "Show {select} leads per page",
            noRows: "Không có đầu mối",
        },
    });
} else if (document.getElementById("datatablesDepartment") != null) {
    const dataTableDepartment = new simpleDatatables.DataTable("#datatablesDepartment", {
        fixedHeight: true,
        fixedColumns: true,
    });
} else if (document.getElementById("datatableEmployee") != null) {
    const datatableEmployee = new simpleDatatables.DataTable("#datatableEmployee", {
        fixedHeight: true,
        fixedColumns: true,
    });
} else if (document.getElementById("datatablesGroupEmp") != null) {
    const datatablesGroupEmp = new simpleDatatables.DataTable("#datatablesGroupEmp", {
        fixedHeight: true,
        labels: {
            placeholder: "Tìm kiếm nhóm nhân viên...",
            perPage: "Show {select} leads per page",
            noRows: "Không có nhóm nhân viên",
        },
    });
} else if (document.getElementById("datatablesPermission") != null) {
    const datatablesPermission = new simpleDatatables.DataTable("#datatablesPermission", {
        searchable: false,
        fixedHeight: true,
        perPageSelect: false,
        labels: {
            placeholder: "Tìm kiếm nhóm quyền...",
            perPage: "Show {select} per page",
            noRows: "Không có nhóm quyền",
        },
    });
} else if (document.getElementById("datatableDecentralize") != null) {
    const datatableDecentralize = new simpleDatatables.DataTable("#datatableDecentralize", {
        fixedHeight: true,
    });
} else if (document.getElementById("dataTablesJunk") != null) {
    const datatablesPermission = new simpleDatatables.DataTable("#dataTablesJunk", {
        fixedHeight: true,
        labels: {
            placeholder: "Tìm kiếm...",
            perPage: "Show {select} per page",
            noRows: "Không có dữ liệu",
        },
    });
} else if (document.getElementById("datatablesDeal") != null) {
    const datatablesDeal = new simpleDatatables.DataTable("#datatablesDeal", {
        fixedHeight: true,
        labels: {
            placeholder: "Tìm kiếm...",
            perPage: "Show {select} per page",
            noRows: "Không có dữ liệu",
        },
    });
} else if (document.getElementById("dataTableEmp") != null) {
    const datatablesDeal = new simpleDatatables.DataTable("#dataTableEmp", {
        fixedHeight: true,
        labels: {
            placeholder: "Tìm kiếm...",
            perPage: "Show {select} per page",
            noRows: "Không có dữ liệu",
        },
    });
} else if (document.getElementById("datatablesReportSource") != null) {
    const datatablesReportSource = new simpleDatatables.DataTable("#datatablesReportSource", {
        searchable: false,
        fixedHeight: true,
        perPageSelect: false,
        labels: {
            info: "",
        },
    });
} else if (document.getElementById("datatablesReportAgency") != null) {
    const datatablesReportAgency = new simpleDatatables.DataTable("#datatablesReportAgency", {
        searchable: false,
        fixedHeight: true,
        perPageSelect: false,
        labels: {
            info: "",
        },
    });
}else if (document.getElementById("datatablesTradingAccountBalance") != null) {
    const datatablesReportAgency = new simpleDatatables.DataTable("#datatablesTradingAccountBalance", {
        searchable: true,
        fixedHeight: true,
        perPageSelect: false,
        labels: {
            info: "",
        },
    });
}
