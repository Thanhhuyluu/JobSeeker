$(document).ready(function () {
    // Event delegation for pagination buttons
    $(document).on('click', '.app-main-navigate-item-btn', function () {
        var pageUrl = $(this).val();
        console.log(pageUrl);

        $.ajax({
            type: 'GET',
            url: pageUrl,
            success: function (data) {
                $('.app-content-loaded').html(data);
                window.scrollTo({
                    top: 160,
                    behavior: 'smooth'
                });
                console.log(data);
            },
            error: function (xhr, status, error) {
                console.error("Có lỗi xảy ra: ", error);
            }
        });
    });

    // Event delegation for search bar items
    $(document).on('click', '.search-bar--main-section-item', function () {
        var locationKey = $('#search-bar--main-section-item-input-sender--location').val();
        var industryKey = $('#search-bar--main-section-item-input-sender--industry').val();
        var searchKey = $('#search-bar--main-section-input').val();

        $.ajax({
            type: 'GET',
            url: '/admin/viec-lam' + '?industryId=' + industryKey + '&locationId=' + locationKey + '&search=' + searchKey + '&pageNo=1' + '&pageSize=10' + '&sortBy=jobTitle:asc',
            success: function (data) {
                $('.app-content-loaded').html(data);
                window.scrollTo({
                    top: 160,
                    behavior: 'smooth'
                });
                console.log('/viec-lam/page=1' + '?industryId=' + industryKey + '&locationId=' + locationKey + '&search=' + searchKey);
            },
            error: function (xhr, status, error) {
                console.error("Có lỗi xảy ra: ", error);
                console.error("Có lỗi xảy ra: ", status);
                console.error("Có lỗi xảy ra: ", xhr);
            }
        });
    });

    // Event delegation for showing job details
    $(document).on('click', '.table-action-button--show', function () {
        var jobId = $(this).data('job-id');
        console.log(jobId);

        $.ajax({
            url: '/admin/xem-thong-tin?jobId=' + jobId,
            type: 'GET',
            data: { jobId: jobId },
            success: function (response) {
                // Handle the response from the server
                toggleForm();
                document.querySelector('.main-form').innerHTML = response;
            },
            error: function (xhr, status, error) {
                // Handle any errors
                console.error('Error:', error);
            }
        });
    });
});

