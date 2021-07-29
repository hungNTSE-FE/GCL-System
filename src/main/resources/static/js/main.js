$(document).ready(function() {
    $('a.edit').click(function() {
        var dad = $(this).parent().parent();
        dad.find('#label-name').hide();
        dad.find('#selectLevel').show().focus();
    });

    $('#selectLevel').focusout(function() {
        var dad = $(this).parent();
        $(this).hide();
        dad.find('#label-name').show().focus();
    });
});