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