const clockTarget_year = document.getElementById("today_year");
const clockTarget_date = document.getElementById("today_date");
const clockTarget_time = document.getElementById("today_time");


function clock() {
    let date = new Date();

    // 년도
    let clockYear = date.getFullYear();
    // 월 (0~11이기에 +1을 해줌)
    let clockMonth = date.getMonth()+1;
    // 일
    let clockDate = date.getDate();
    // 요일 (0~6까지의 숫자로 가져옴)
    let clockDay = date.getDay();
    // 요일 배열
    let week = ['일', '월', '화', '수', '목', '금', '토'];
    // 시
    let clockHours = date.getHours();
    // 분
    let clockMinutes = date.getMinutes();
    //초
    let clockSecond = date.getSeconds();
    // 오전 오후
    let clockAmPm = `${clockHours < 12 ? "오전" : "오후"}`
    // AM PM 방식으로 시간 변환
    clockHours = clockHours % 12;

    clockTarget_year.innerText = `${clockYear}년`;
    clockTarget_date.innerText = `${clockMonth}월 ${clockDate}일 ${week[clockDay]}요일`;
    clockTarget_time.innerText = `${clockAmPm} ${clockHours < 10 ? `0${clockHours}` : clockHours}시 ${clockMinutes < 10 ? `0${clockMinutes}` : clockMinutes}분 ${clockSecond < 10 ? `0${clockSecond}` : clockSecond}초`;
}

function init() {
    clock();
    setInterval(clock, 1000);
}

window.onload = () => {
    init();
}

