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
                console.log(pageUrl);
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

function toggleForm() {
    const mainForm = document.querySelector('.main-form');
    const mainFormParent = mainForm.parentElement;
    const mainTable = document.querySelector('.main-content-table-wrapper');
    if (mainFormParent.classList.contains('l-4')) {
        mainFormParent.classList.remove('l-4');
        mainFormParent.classList.add('hide');
        mainTable.classList.remove('l-8');
    } else {
        mainTable.classList.add('l-8');
        mainFormParent.classList.remove('hide');
        mainFormParent.classList.add('l-4');
    }
}

$(document).ready(function() {
    // Use event delegation to attach the event listener
    $(document).on('click', '.add-new-record-button', function() {

        document.querySelector('.main-form').innerHTML = '<div class="form-group">\n' +
            '                            <label for="jobTitle">Tên công việc</label>\n' +
            '                            <input type="text" id="jobTitle" name="jobTitle" class="form-control">\n' +
            '                        </div>\n' +
            '                        <div class="form-group">\n' +
            '                            <label for="jobDescription">Mô tả</label>\n' +
            '                            <textarea id="jobDescription" name="jobDescription" class="form-control" placeholder="Hãy viết tiêu đề IN HOA"></textarea>\n' +
            '                        </div>\n' +
            '\n' +
            '\n' +
            '                        <label for="search-bar--main-section-category-trigger" class="search-bar--main-section">\n' +
            '                            <input hidden type="checkbox" id="search-bar--main-section-category-trigger" name="search-bar--main-section-category-trigger">\n' +
            '                            <i class="search-bar--main-section-icon fa-solid fa-briefcase"></i>\n' +
            '                            <div class="search-bar--main-section-text" th:text="|Company|">\n' +
            '\n' +
            '                            </div>\n' +
            '                            <i class="search-bar--main-section-icon--down fa-solid fa-chevron-down"></i>\n' +
            '                            <i class="search-bar--main-section-icon--up fa-solid fa-chevron-up"></i>\n' +
            '\n' +
            '\n' +
            '                            <ul class="search-bar--main-section-list scrollable-element">\n' +
            '                                <li class="search-bar--main-section-item">\n' +
            '                                    <input type="text" class="search-bar--main-section-item-input" >\n' +
            '                                </li>\n' +
            '                                <li class="search-bar--main-section-item active" >\n' +
            '                                    <p class="search-bar--main-section-item-text" th:value="${companyName}">\n' +
            '                                        All categories\n' +
            '                                        <input class="search-bar--main-section-item-key" type="text" hidden th:value="-1">\n' +
            '                                    </p>\n' +
            '                                    <i class="search-bar--main-section-item-check fa-solid fa-check"></i>\n' +
            '\n' +
            '                                </li>\n' +
            '                                <li th:each="company : ${companies}" class="search-bar--main-section-item" >\n' +
            '                                    <p th:text="${company.companyName}" class="search-bar--main-section-item-text">\n' +
            '\n' +
            '                                    </p>\n' +
            '                                    <i class="search-bar--main-section-item-check fa-solid fa-check"></i>\n' +
            '                                    <input class="search-bar--main-section-item-key" type="text" hidden th:value="${company.companyId}">\n' +
            '                                </li>\n' +
            '                                <input name="companyId" hidden id="search-bar--main-section-item-input-sender--company" th:value="${companyId != null ? companyId : -1}" type="text" class="search-bar--main-section-item-input-sender">\n' +
            '\n' +
            '                            </ul>\n' +
            '\n' +
            '                        </label>\n' +
            '\n' +
            '                        <div class="form-group">\n' +
            '\n' +
            '                            <label for="jobType">Loại công việc</label>\n' +
            '                            <select id="jobType" name="jobType" class="form-control">\n' +
            '                                <option value="Toàn thời gian">Toàn thời gian</option>\n' +
            '                                <option value="Bán thời gian">Bán thời gian</option>\n' +
            '                            </select>\n' +
            '                        </div>\n' +
            '                        <div class="form-group">\n' +
            '                            <label for="experienceLevel">Năm kinh nghiệm</label>\n' +
            '                            <input type="number" id="experienceLevel" name="experienceLevel" class="form-control">\n' +
            '                        </div>\n' +
            '                        <div class="form-group">\n' +
            '                            <label for="careerLevel">Vị trí công việc</label>\n' +
            '                            <select id="careerLevel" name="careerLevel" class="form-control">\n' +
            '                                <option value="Nhân viên">Nhân viên</option>\n' +
            '                                <option value="Trưởng nhóm">Trưởng nhóm</option>\n' +
            '                                <option value="Trưởng/Phó phòng">Trưởng/Phó phòng</option>\n' +
            '                                <option value="Quản lý / Giám sát">Quản lý / Giám sát</option>\n' +
            '                                <option value="Trưởng chi nhánh">Trưởng chi nhánh</option>\n' +
            '                                <option value="Phó giám đốc">Phó giám đốc</option>\n' +
            '                                <option value="Giám đốc">Giám đốc</option>\n' +
            '                                <option value="Thực tập sinh">Thực tập sinh</option>\n' +
            '                            </select>\n' +
            '                        </div>\n' +
            '                        <div class="form-group">\n' +
            '                            <label for="postedDate">Ngày đăng</label>\n' +
            '                            <input type="date" id="postedDate" name="postedDate" class="form-control">\n' +
            '                        </div>\n' +
            '                        <div class="form-group">\n' +
            '                            <label for="expirationDate">Ngày hết hạn</label>\n' +
            '                            <input type="date" id="expirationDate" name="expirationDate" class="form-control">\n' +
            '                        </div>\n' +
            '                        <div class="form-group">\n' +
            '                            <label for="jobUrl">Nguồn</label>\n' +
            '                            <input type="url" id="jobUrl" name="jobUrl" class="form-control">\n' +
            '                        </div>\n' +
            '                        <div class="form-group">\n' +
            '                            <label for="salary">Mức Lương</label>\n' +
            '                            <input type="number" id="salary" name="salary" class="form-control">\n' +
            '                        </div>\n' +
            '                        <div class="form-group">\n' +
            '                            <label for="salaryCurrency">Đơn vị tiền tệ</label>\n' +
            '                            <input type="text" id="salaryCurrency" name="salaryCurrency" class="form-control">\n' +
            '                        </div>\n' +
            '                        <button type="submit" class="btn btn-primary">Thêm</button>';
       toggleForm();
    });
});
