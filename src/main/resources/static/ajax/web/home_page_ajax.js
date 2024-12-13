
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
                // url: '/viec-lam/page=1'+'?industryId=' + industryKey + '&locationId=' +locationKey + '&searchKey=' + searchKey,
                url: '/viec-lam/tim-viec-lam'+'?industryId=' + industryKey + '&locationId=' +locationKey + '&search=' + searchKey + '&pageNo=1' + '&pageSize=10' + '&sortBy=jobTitle:asc' ,
                success: function (data) {
                    $('.app-content-loaded').html(data);
                    window.scrollTo({
                        top: 160,
                        behavior: 'smooth'
                    });
                    console.log('/viec-lam/page=1'+'?industryId=' + industryKey + '&locationId=' +locationKey + '&search=' + searchKey)
                    $('#search-bar--main-section-input').val($('#search-key-sendback').val().trim());
                    $('#searchKey-for-filter').val($('#search-key-sendback').val().trim());
                    $('#industryId-for-filter').val(industryKey);
                    $('#locationId-for-filter').val(locationKey);

                },
                error: function (xhr, status, error) {
                    console.error("Có lỗi xảy ra: ", error); console.error("Có lỗi xảy ra: ", status); console.error("Có lỗi xảy ra: ", xhr);
                }
            })
        })
    }
)