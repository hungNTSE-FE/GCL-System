$("#treeview-container").treeview({
    debug: false,
    // data: ["3.2", "2.2.3"],
});
$("#show-values").on("click", function () {
    $("#values").text($("#treeview-container").treeview("selectedValues"));
});

function selectAllNone() {
    var tvNodes = document.getElementById("treeview-container");
    var chBoxes = tvNodes.getElementsByTagName("input");
    for (var i = 0; i < chBoxes.length; i++) {
        var chk = chBoxes[i];
        if (chk.type == "checkbox") {
            if (chk.checked == false) {
                chk.checked = true;
            }
        }
    }
    return false;
}

function ClearAll() {
    var tvNodes = document.getElementById("treeview-container");
    var chBoxes = tvNodes.getElementsByTagName("input");
    for (var i = 0; i < chBoxes.length; i++) {
        var chk = chBoxes[i];
        if (chk.type == "checkbox") {
            if (chk.checked == true) {
                chk.checked = false;
            }
        }
    }
    return false;
}