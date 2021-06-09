$('#insert1').on('click',function(e){
    e.preventDefault();
    var form = $(this).parents('form');
    swal.fire({
        position: "top-end",
        icon: "success",
        title: "Lưu thành công",
        showConfirmButton: false,
        timer: 3000,
    }, function(isConfirm){
        if (isConfirm) form.submit();
    });
});

$('#update1').on('click',function(e){
    e.preventDefault();
    var form = $(this).parents('form');
    swal.fire({
        position: "top-end",
        icon: "success",
        title: "Cập nhật thành công",
        showConfirmButton: false,
        timer: 3000,
    }, function(isConfirm){
        if (isConfirm) form.submit();
    });
});

$('#delete1').on('click',function(e){
    e.preventDefault();
    var form = $(this).parents('form');
    swal.fire({
        position: "top-end",
        icon: "success",
        title: "Xoá thành công",
        showConfirmButton: false,
        timer: 3000,
    }, function(isConfirm){
        if (isConfirm) form.submit();
    });
});

$('#cancelWork').on('click',function(e){
    e.preventDefault();
    var form = $(this).parents('form');
    swal.fire({
        position: "top-end",
        icon: "success",
        title: "Ngừng hoạt động thành công",
        showConfirmButton: false,
        timer: 3000,
    }, function(isConfirm){
        if (isConfirm) form.submit();
    });
});