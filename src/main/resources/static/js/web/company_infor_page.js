showmoreActionButton = document.querySelectorAll('.show-more-action-button');
showmoreSection = document.querySelector('.company-infor-body-section');

showmoreActionButton.forEach(element => {
    element.addEventListener('click', ()=> {
        console.log('aloo')
        showmoreSection.classList.toggle('active');
    });
});