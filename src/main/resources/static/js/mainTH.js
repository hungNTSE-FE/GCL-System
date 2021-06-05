/*!
    * Start Bootstrap - SB Admin Pro v2.0.0 (https://shop.startbootstrap.com/product/sb-admin-pro)
    * Copyright 2013-2021 Start Bootstrap
    * Licensed under SEE_LICENSE (https://github.com/StartBootstrap/sb-admin-pro/blob/master/LICENSE)
    */
window.addEventListener('DOMContentLoaded', event => {
    // Activate feather
    feather.replace();

    // Enable tooltips globally
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Enable popovers globally
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });

    // Activate Bootstrap scrollspy for the sticky nav component
    const stickyNav = document.body.querySelector('#stickyNav');
    if (stickyNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#stickyNav',
            offset: 82,
        });
    }

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sidenav-toggled'));
        });
    }

    // Close side navigation when width < LG
    const sidenavContent = document.body.querySelector('#layoutSidenav_content');
    if (sidenavContent) {
        sidenavContent.addEventListener('click', event => {
            const BOOTSTRAP_LG_WIDTH = 992;
            if (window.innerWidth >= 992) {
                return;
            }
            if (document.body.classList.contains("sidenav-toggled")) {
                document.body.classList.toggle("sidenav-toggled");
            }
        });
    }

    // Add active state to sidbar nav links
    let activatedPath = window.location.pathname.match(/([\w-]+\.html)/, '$1');

    if (activatedPath) {
        activatedPath = activatedPath[0];
    } else {
        activatedPath = 'index.html';
    }

    const targetAnchors = document.body.querySelectorAll('[href="' + activatedPath + '"].nav-link');

    targetAnchors.forEach(targetAnchor => {
        let parentNode = targetAnchor.parentNode;
        while (parentNode !== null && parentNode !== document.documentElement) {
            if (parentNode.classList.contains('collapse')) {
                parentNode.classList.add('show');
                const parentNavLink = document.body.querySelector(
                    '[data-bs-target="#' + parentNode.id + '"]'
                );
                parentNavLink.classList.remove('collapsed');
                parentNavLink.classList.add('active');
            }
            parentNode = parentNode.parentNode;
        }
        targetAnchor.classList.add('active');
    });
});

function selectAllNone() {
    var tvNodes = document.getElementById("treeview");
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
    var tvNodes = document.getElementById("treeview");
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

$(".clickshowinfo").click(function () {
    var id = $(this).data("id");
    $(".user-info")
        .animate({ width: "25%" }, 150)
        .find(".user-info-in")
        .animate({ width: "100%" }, 150);
});

$(".user-info").click(function () {
    $(".cc").animate({ width: "0%" }, 150);
    $(".user-info").animate({ width: "0%" }, 150);
});

$(".card-body").click(function (e) {
    e.stopPropagation();
});

document.getElementById("insert1").onclick = function () {
    Swal.fire({
        position: "top-end",
        icon: "success",
        title: "Lưu thành công",
        showConfirmButton: false,
        timer: 1500,
    });
};






