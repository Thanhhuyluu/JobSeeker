
const jobInforSlider = document.querySelector('.job-infor-img-slider')
const jobInforSliderImgs = document.querySelectorAll('.job-infor-img-slider-wrap');
const jobInforSliderBtnLeft  = document.querySelector('.job-infor-slider-btn-left')
const jobInforSliderBtnRight  = document.querySelector('.job-infor-slider-btn-right')
const length2 = jobInforSliderImgs.length
let current2 = 0;
const handleChangeSlider2 =()=>{
    if(current2==length2-1) {
        current2= 0
        let width = jobInforSliderImgs[0].offsetWidth
        jobInforSlider.style.transform = `translateX(0px)`
    }else{

        current2++;
        let width = jobInforSliderImgs[0].offsetWidth
        jobInforSlider.style.transform = `translateX(${width  *-1* current2}px)`
    }
}
let handleEventChangeSlide2 = setInterval(handleChangeSlider2, 4000);

jobInforSliderBtnRight.addEventListener('click',()=>{
    clearInterval(handleEventChangeSlide2)
    handleChangeSlider2()
    handleEventChangeSlide2 = setInterval(handleChangeSlider2, 4000);
})

jobInforSliderBtnLeft.addEventListener('click',()=>{
    clearInterval(handleEventChangeSlide2)
    if(current2==0) {
        current2 = length2-1
        let width = jobInforSliderImgs[0].offsetWidth
        jobInforSlider.style.transform = `translateX(${width * -1 * current2}px)`
    }else{

        current2--
        let width = jobInforSliderImgs[0].offsetWidth
        jobInforSlider.style.transform = `translateX(${width  *-1* current2}px)`
    }
    handleEventChangeSlide2 = setInterval(handleChangeSlider2, 4000);
})



// handle like button

var jobLikeButton = document.querySelector('.job-infor-detail-like-button');

jobLikeButton.addEventListener('click', () => {
    jobLikeButton.classList.toggle('liked');
})
