var SOURCE_DROPDOWN = "#selectSource";
var LEVEL_DROPDOWN = "#selectLevel";
var DEPARTMENT_DROPDOWN = "#selectDepartment";
var RATING_DROPDOWN = "#selectRanking";
var TRANSACTION_DROPDOWN = "#selectTransaction";


var MULTI_SELECT = "#multi-select";

$(SOURCE_DROPDOWN).dropdown();
$(LEVEL_DROPDOWN).dropdown();
$(DEPARTMENT_DROPDOWN).dropdown();
$(RATING_DROPDOWN).dropdown();
$(MULTI_SELECT).dropdown();
$(TRANSACTION_DROPDOWN).dropdown();

//MULTI SELECT
$(MULTI_SELECT).change(function () {
    var meals = $(this).val();
    var selectedmeals = meals.join(", ");
    console.log(selectedmeals);
    $("input[name=listSelected]").val(selectedmeals);
});

//VALIDATION
$(".ui.form").form({
    fields: {
        name: {
            identifier: "name",
            rules: [
                {
                    type: "empty",
                    prompt: "Không bỏ trống tên đầu mối.",
                },
            ],
        },
        phoneNumber: {
            identifier: "phoneNumber",
            rules: [
                {
                    type   : 'integer',
                    prompt : 'Vui lòng nhập số điện thoại.'
                },
                {
                    type   : 'empty',
                    prompt : 'Không bỏ trống số điện thoại.'
                },
                {
                    type   : 'minLength[10]',
                    prompt : 'Vui lòng nhập số điện thoại ít nhất 10 ký tự.'
                },
            ]
        },
        sourceName: {
            identifier: "sourceName",
            rules: [
                {
                    type   : 'empty',
                    prompt : 'Vui lòng lựa chọn nguồn phù hợp.'
                }
            ]
        },
        email: {
            identifier  : 'email',
            rules: [
                {
                    type   : 'email',
                    prompt : 'Vui lòng nhập email hợp lệ.'
                }
            ]
        },
    },
});
