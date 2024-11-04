//////////// 셀렉트 박스 부분 //////////////
const $SelectMakeupButton = document.getElementById('SelectMakeupButton');                      // 셀렉트 박스 버튼 전체 부분
const $showFdMakeupSelectBox = document.getElementById('showFdMakeupSelectBox');                // 파운데이션 바르기 버튼
const $showLipMakeupSelectBox = document.getElementById('showLipMakeupSelectBox');              // 립 바르기 버튼
const $showEyeLineMakeupSelectBox = document.getElementById('showEyeLineMakeupSelectBox');      // 아이라인 버튼
const $MakeupSelectBox = document.getElementById('MakeupSelectBox');                            // 셀렉트 박스
const $colorBox = document.getElementById('colorBox');                                          // 셀렉트 박스에 있는 색상 박스
const $color_button = document.querySelectorAll('.color-button');                                // 셀렉터에 존재하는 색상들을 전부 불러오기
const $closeBtn = document.getElementById('closeBtn');                                          // 셀렉터 닫기 버튼
const $sliderValue = document.getElementById('sliderValue');                                    // 투명도를 위한 슬라이더 값
                                                                                   // 파운데이션인지 립인지 아이라인인지 구분하기 위한 변수
const $foundation = document.getElementById('foundation');
const $lip = document.getElementById('lip');

const $save_1 = document.getElementById('save_1');
const $save_2 = document.getElementById('save_2');
const $save_3 = document.getElementById('save_3');
const $save_4 = document.getElementById('save_4');
const $save_5 = document.getElementById('save_5');
let isNumberClick = false;
let whatNumber = 1;
const $savebtn = document.getElementById('savebtn');

let whatBtn; 
let Fdslider = 0;
let Lipslider = 0;
let whatColor;

$save_1.addEventListener('click', ()=>{
    console.log("1");
    isNumberClick = true;
    whatNumber = 1;
});

$save_2.addEventListener('click', ()=>{
    isNumberClick = true;
    whatNumber = 2;
});

$save_3.addEventListener('click', ()=>{
    isNumberClick = true;
    whatNumber = 3;
});

$save_4.addEventListener('click', ()=>{
    isNumberClick = true;
    whatNumber = 4;
});

$save_5.addEventListener('click', ()=>{
    isNumberClick = true;
    whatNumber = 5;
});

function sendDataMakeup(slider) {
    fetch(`/savemakeup`, {
        method : 'POST',
        headers : { "Content-Type" : "application/json" },
        body : JSON.stringify({"number" : whatNumber, "opacity" : slider, color_code : whatColor})
    })
    .then((message) => { return message.text()})
    .then((message) => {
        if(message == "fails") {
            alert(`${$emailvalue}로 아이디를 찾을 수 없습니다.`);
        }
        else {
            $findid_form.style.display = 'none';
            $printid_form.style.display = "block";
            $member_id.value = message;
        }
    })
    .catch((Error) => {
        console.error('Error:', error); // 에러 처리
    });
}

$savebtn.addEventListener('click', ()=>{
    console.log("2");
    if(isNumberClick == true) {
        if(whatBtn == "Fd") {
            console.log("1");
            sendDataMakeup(Fdslider);
        }
        else if(whatBtn == "Lip") {
            sendDataMakeup(Lipslider);
        }
        
    }
    else {
        alert('저장할 버튼을 클릭해주세요');
    }
});



//////////// 카메라 부분 //////////////
let connectCam = false;     // 현재 캠이 연결되었는지 확인 false : 미연결 true : 연결
const $CamBtn = document.getElementById('CamBtn');      // 카메라 연결과 해제를 위한 버튼
const $video = document.getElementById('video');         // 카메라를 보이게 하는 영역

//////////// 웹 소켓 함수 부분 //////////////
function cunnectWebsocket() {
    const websocket = new WebSocket('ws://127.0.0.1:8080/video-feed');
    websocket.onmessage = function(event) {
        const blob = new Blob([event.data], { type: 'image/jpeg' });
        const url = URL.createObjectURL(blob);
        $video.src = url;
    };
}

///////////// python 프로세스 연결 //////////////
function sendConnectPython() {
    fetch('/practice', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then((message)=>{ return message.text() })
    .then((message)=>{
        if(message == "already") {    // 컨트롤러에서 중복인지 아닌지 판단하면서 text를 전송하는데 exist면 중복
            alert(`이미 실행 중 입니다.`);
        }
        else {
            alert('프로그램이 실행합니다.');
        }
    })
    .catch(error => {
        alert('/practice: 네트워크 오류가 발생했습니다: ' + error);
    });
}
///////////// python 프로세스 종료 //////////////
function sendDestroyPython() {
    fetch('/shutdown', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then((message)=>{ return message.text() })
    .then((message)=>{
        if(message == "already") {    // 컨트롤러에서 중복인지 아닌지 판단하면서 text를 전송하는데 exist면 중복
            alert(`이미 종료되었습니다. 입니다.`);
        }
        else {
            alert('프로그램이 종료됩니다.');
        }
    })
    .catch(error => {
        alert('/shutdown: 네트워크 오류가 발생했습니다: ' + error);
    });
}

// 색상 이름과 HEX 값을 매핑하는 Map 객체 생성
const colorMap = new Map([
    ['red', '#ff0000'],
    ['green', '#00ff00'],
    ['blue', '#0000ff'],
    ['yellow', '#ffff00'],

    ['orange', '#ff7f00'],
    ['purple', '#800080'],
    ['pink', '#ffc0cb'],
    ['cyan', '#00ffff'],
    
    ['brown', '#a52a2a'],
    ['gray', '#808080'],
    ['lightcoral', '#f08080'],
    ['lightgreen', '#90ee90'],

    ['lightblue', '#add8e6'],
    ['lavender', '#e6e6fa'],
    ['gold', '#ffd700'],
    ['salmon', '#fa8072'],

    ['teal', '#008080'],
    ['navy', '#000080'],
    ['maroon', '#800000'],
    ['olive', '#808000'],

    ['coral', '#ff7f50'],
    ['plum', '#dda0dd'],
    ['khaki', '#f0e68c'],
    ['chocolate', '#d2691e'],

    ['sienna', '#a0522d'],
    ['slateblue', '#6a5acd'],
    ['mediumseagreen', '#3cb371'],
    ['lightsalmon', '#ffa07a'],
    
    ['lightgray', '#d3d3d3'],
    ['darkorange', '#ff8c00'],
    ['springgreen', '#00ff7f'],
    ['violet', '#ee82ee'],
]);

// 파운데이션 바르기 버튼을 클릭했을 경우
$showFdMakeupSelectBox.addEventListener('click', ()=>{
    $SelectMakeupButton.style.display = 'none';    // 버튼들 안보이게 하기
    $MakeupSelectBox.style.backgroundColor = '#339adf';
    $MakeupSelectBox.style.display = "block"; // 셀렉트 박스 보이게 하기 height: 480px
    $MakeupSelectBox.style.height = '480px';
    $foundation.style.display = 'block';
    $sliderValue.value = Fdslider;
    whatBtn = 'Fd';
});

// 립 바르기 버튼을 클릭했을 경우
$showLipMakeupSelectBox.addEventListener('click', ()=>{
    $SelectMakeupButton.style.display = 'none';    // 버튼들 안보이게 하기
    $MakeupSelectBox .style.backgroundColor = '#ec7b7b';
    $MakeupSelectBox.style.height = '350px';
    $MakeupSelectBox.style.display = "block"; // 셀렉트 박스 보이게 하기
    $lip.style.display = 'block';
    $sliderValue.value = Lipslider;
    whatBtn = 'Lip';
});

// 아이라인 바르기 버튼을 클릭했을 경우
$showEyeLineMakeupSelectBox.addEventListener('click', ()=>{
    $SelectMakeupButton.style.display = 'none';    // 버튼들 안보이게 하기
    $MakeupSelectBox.style.backgroundColor = '#362349';
    $MakeupSelectBox.style.display = "block"; // 셀렉트 박스 보이게 하기
    whatBtn = 'EyeLine';
});

// 셀렉터 박스에서 닫기 버튼을 클릭했을 경우
$closeBtn.addEventListener('click', ()=>{
    $MakeupSelectBox.style.display = "none";    // 셀렉트 박스 안보이게 하기
    $foundation.style.display = 'none';
    $lip.style.display = 'none';
    $SelectMakeupButton.style.display = 'block';   // 버튼들 보이게 하기   
});

// 셀렉터의 색상 중 하나를 클릭했을 경우 실행되는 동작
$color_button.forEach(button => {
    button.addEventListener('click', () => {
        const color = colorMap.get(button.style.backgroundColor); // 색상 추출
        $colorBox.style.backgroundColor = color;
        whatColor = color;
        /////////////////// 버튼 값 POST로 전송 ///////////////////
        fetch(`/Color?whatBtn=${whatBtn}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 'color_code' : color })
        });
    });
});

// 투명도 슬라이더를 드래그할 경우 발생하는 동작
function sliderevent (value) {
    ////////////////// 슬라이더 값 POST로 전송 ///////////////////
    fetch(`/Slider?whatBtn=${whatBtn}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ 'opacity' : value })
    });
    if(whatBtn == 'Fd') {
        Fdslider = value;
    }
    else if(whatBtn == 'Lip') {
        Lipslider = value;
    }
};

cunnectWebsocket();
$CamBtn.addEventListener('click', ()=>{
    if(connectCam === false) {   // 캠 미연결이므로 연결 동작 실행
        cunnectWebsocket();
        sendConnectPython(); 
        $video.style.backgroundImage = 'url(http://localhost:8080/video_feed)'; // 캠 스트림 URL 설정
        $video.style.display = 'block';   // 비디오 부분 보이게 설정
        $CamBtn.textContent = '카메라 해제';    // 연결되어 있으므로 연결을 해제 위해 카메라 해제로 명칭 변경
        $SelectMakeupButton.style.display = 'block';
        connectCam = true;
    }
    else {  // 캠이 연결되어 있으니 해제 동작 실행
        sendDestroyPython();
        $CamBtn.textContent = '카메라 연결';    // 연결 해제되므로 다시 연결을 위해 카메라 연결로 명칭 변경
        $SelectMakeupButton.style.display = 'none';
        streamActive = false; // 상태 업데이트
        $video.style.display = 'none';
        connectCam = false;
    }
});