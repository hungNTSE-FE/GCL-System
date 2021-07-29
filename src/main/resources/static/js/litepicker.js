
window.addEventListener('DOMContentLoaded', event => {

    const litepickerSingleDate = document.getElementById('litepickerSingleDate');
    if (litepickerSingleDate) {
        new Litepicker({
            element: litepickerSingleDate,
            format: 'MMM DD, YYYY'
        });
    }

    const litepickerDateRange = document.getElementById('litepickerDateRange');
    if (litepickerDateRange) {
        new Litepicker({
            element: litepickerDateRange,
            singleMode: false,
            format: 'MMM DD, YYYY'
        });
    }

    const litepickerDateRange2Months = document.getElementById('litepickerDateRange2Months');
    if (litepickerDateRange2Months) {
        new Litepicker({
            element: litepickerDateRange2Months,
            singleMode: false,
            numberOfMonths: 2,
            numberOfColumns: 2,
            format: 'MMM DD, YYYY'
        });
    }

    const litepickerRangePlugin = document.getElementById('litepickerRangePlugin');
    if (litepickerRangePlugin) {
        new Litepicker({
            element: litepickerRangePlugin,
            singleMode: false,
            numberOfMonths: 2,
            numberOfColumns: 2,
            format: 'DD/MM/YYYY',
            plugins: ['ranges']
        });
    }

    const litepickerBirthdate = document.getElementById('litepickerBirthdate');
    if (litepickerBirthdate) {
        new Litepicker({
            element: document.getElementById("litepickerBirthdate"),
        });
    }

    const litepickerDateEnteringCompany = document.getElementById('litepickerDateEnteringCompany');
    if (litepickerDateEnteringCompany) {
        new Litepicker({
            element: document.getElementById("litepickerDateEnteringCompany"),
        });
    }

    const litepickerOfficeEntryDate = document.getElementById('litepickerOfficeEntryDate');
    if (litepickerOfficeEntryDate) {
        new Litepicker({
            element: document.getElementById("litepickerOfficeEntryDate"),
        });
    }

});
