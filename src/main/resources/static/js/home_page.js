// Handle the search input location and industry when user typeing

document.querySelectorAll('label.search-bar--main-section').forEach(section => {

    var searchBarMainList = section.querySelector('.search-bar--main-section-list');
    var searchBarMainInput = section.querySelector('.search-bar--main-section-item-input');
    var searchBarMainItems = section.querySelectorAll('.search-bar--main-section-item');
    var sectionText = section.querySelector('.search-bar--main-section-text');
    var inputSender = section.querySelector('.search-bar--main-section-item-input-sender');
    searchBarMainInput.addEventListener('input', () => {
        const searchValue = searchBarMainInput.value.toLowerCase();
        searchBarMainItems.forEach(item => {
            const text = item.querySelector('.search-bar--main-section-item-text');
            if (text) {
                if (text.textContent.toLowerCase().includes(searchValue)) {
                    item.style.display = '';
                } else {
                    item.style.display = 'none';
                }
            }
        });
    });
    searchBarMainItems.forEach(item => {
        item.addEventListener('click', () => {
            searchBarMainItems.forEach(i => i.classList.remove('active'));

            item.classList.add('active');

            const activeText = item.querySelector('.search-bar--main-section-item-text').textContent;
            sectionText.textContent = activeText;

            searchBarMainList.classList.remove('active');
            inputSender.value = item.querySelector('.search-bar--main-section-item-key').value;

        });
    });


})


// handle the filter salary dropdown


const selectBox = document.querySelector('.app-main-filter-section-select');
const optionsContainer = document.querySelector('.app-main-filter-section-options-container');
const options = document.querySelectorAll('.app-main-filter-section-option');
const selectBoxDownBtn = document.querySelector('.app-main-filter-section-select-icon--down');
const selectBoxUpBtn = document.querySelector('.app-main-filter-section-select-icon--up');
const salaryFilterInput = document.querySelector('#app-main-filter-section-salary-input');
selectBox.addEventListener('click', () => {
    optionsContainer.classList.toggle('hide');
    selectBoxUpBtn.classList.toggle('hide');
    selectBoxDownBtn.classList.toggle('hide');
});

options.forEach(option => {
    option.addEventListener('click', () => {
        selectBox.querySelector('p').textContent = option.textContent;
        selectBox.setAttribute('data-value', option.getAttribute('data-value'));
        optionsContainer.classList.add('hide');
        salaryFilterInput.value = option.getAttribute('data-value').trim();
    });
});

document.addEventListener('click', function (event) {
    if (!selectBox.contains(event.target)) {
        optionsContainer.classList.add('hide')
        selectBoxUpBtn.classList.add('hide');
        selectBoxDownBtn.classList.remove('hide');

    }
});


const jobContainer = document.querySelector('.app-content-loaded');  // Phần tử cha chứa các nút like

jobContainer.addEventListener('click', function (event) {
    if (event.target.classList.contains('app-main-job-item-action-icon')) {
        const parent = event.target.parentElement;  // Lấy phần tử cha của event.target
        parent.classList.toggle('liked');           // Toggle class 'liked' cho phần tử cha
        console.log(parent);
    }
});




    // Lắng nghe sự kiện click vào phần tử filter-heading
    document.querySelector('#filter-heading').addEventListener('click', function() {
    // Tự động submit form
    document.querySelector('#app-main-filter').submit();
});
