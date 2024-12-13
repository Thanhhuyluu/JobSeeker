
$(document).ready(function () {

        $('.app-content-loaded').on('click', '.app-main-navigate-item-btn', function () {
            var pageUrl = $(this).val();
            console.log(pageUrl)

            $.ajax({
                type: 'GET',
                url: pageUrl,
                success: function (data) {
                    $('.app-content-loaded').html(data);
                    window.scrollTo({
                        top: 160,
                        behavior: 'smooth'
                    });
                    console.log(pageUrl)
                },
                error: function (xhr, status, error) {
                    console.error("Có lỗi xảy ra: ", error);
                }
            });
        });

        $('.search-bar--main-section-item').click(function () {
            var locationKey = $('#search-bar--main-section-item-input-sender--location').val();
            var industryKey = $('#search-bar--main-section-item-input-sender--industry').val();
            var searchKey = $('#search-bar--main-section-input').val();



            $.ajax({
                type: 'GET',
                url: '/admin/viec-lam'+'?industryId=' + industryKey + '&locationId=' +locationKey + '&search=' + searchKey + '&pageNo=1' + '&pageSize=10' + '&sortBy=jobTitle:asc' ,
                success: function (data) {
                    $('.app-content-loaded').html(data);
                    window.scrollTo({
                        top: 160,
                        behavior: 'smooth'
                    });
                    console.log('/viec-lam/page=1'+'?industryId=' + industryKey + '&locationId=' +locationKey + '&search=' + searchKey)


                },
                error: function (xhr, status, error) {
                    console.error("Có lỗi xảy ra: ", error); console.error("Có lỗi xảy ra: ", status); console.error("Có lỗi xảy ra: ", xhr);
                }
            })
        })
    }
)

$(document).ready(function() {
    // Use event delegation to attach the event listener
    $(document).on('click', '.add-new-record-button', function() {
        const mainForm = document.querySelector('.main-form');
        const mainFormParent = mainForm.parentElement;
        const mainTable = document.querySelector('.main-content-table-wrapper');

        if (mainFormParent.classList.contains('l-4')) {
            console.log('add new record');

            mainFormParent.classList.remove('l-4');
            mainFormParent.classList.add('hide');
            mainTable.classList.remove('l-8');
        } else {
            mainTable.classList.add('l-8');

            mainFormParent.classList.remove('hide');
            mainFormParent.classList.add('l-4');
        }
    });
});