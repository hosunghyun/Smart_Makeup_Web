// 버튼을 클릭 시 중복 클릭을 막기위해 클릭한 것을 나타내기 위한 함수
function check_numberbtn(numbutton) {
    // 일단 모든 버튼을 사용할 수 있게 변경
    $save_1.disabled = false;
    $save_2.disabled = false;
    $save_3.disabled = false;
    $save_4.disabled = false;
    $save_5.disabled = false;

    $save_1.style.opacity = 1;
    $save_2.style.opacity = 1;
    $save_3.style.opacity = 1;
    $save_4.style.opacity = 1;
    $save_5.style.opacity = 1;

    // 그러고 나서 입력 받은 버튼을 사용할 수 없게 변경
    numbutton.disabled = true;
    numbutton.style.opacity = 0.2;
}

$savenumber.forEach(button => {
    button.addEventListener('click', ()=>{
        const btnid = button.id;
        const btnnum = btnid.split('_')[1];
        isNumberClick = true;               // 사용자가 저장하기 전에 숫자 버튼을 클릭했는지 확인하기 위한 boolean
        whatNumber = btnnum;
        check_numberbtn(button);   // 버튼을 사용할 수 없게 변경
    });
});

function sendDataMakeup(slider, category, whatColor) {
    fetch(`/savemakeup`, {
        method : 'POST',
        headers : { "Content-Type" : "application/json" },
        body : JSON.stringify({"number" : whatNumber, "opacity" : slider, "color_code" : whatColor, "category" : category})
    })
    .catch((Error) => {
        console.error('Error:', Error); // 에러 처리
    });
}

$savebtn.addEventListener('click', ()=>{
    if(isNumberClick == true) {
        sendDataMakeup(Fdslider, "foundation", FdwhatColor);
        sendDataMakeup(Lipslider, "lipstick", LipwhatColor);
        // sendDataMakeup(EyeLineslider, "eyeline", EyeLinewhatColor);
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

// 파운데이션 바르기 버튼을 클릭했을 경우
$showFdMakeupSelectBox.addEventListener('click', ()=>{
    $SelectMakeupButton.style.display = 'none';    // 버튼들 안보이게 하기
    $MakeupSelectBox.style.backgroundColor = FdBackGroundColor;
    $sliderValueText.style.backgroundColor = FdBackGroundColor;
    $MakeupSelectBox.style.display = "block"; // 셀렉트 박스 보이게 하기 height: 480px
    $MakeupSelectBox.style.height = '390px';
    $foundation.style.display = 'block';
    $sliderValue.value = Fdslider;
    // $colorBox1.style.backgroundColor = FdwhatColor;
    whatBtn = 'Fd';
});

// 립 바르기 버튼을 클릭했을 경우
$showLipMakeupSelectBox.addEventListener('click', ()=>{
    $SelectMakeupButton.style.display = 'none';    // 버튼들 안보이게 하기
    $MakeupSelectBox.style.backgroundColor = LipBackGroundColor;
    $sliderValueText.style.backgroundColor = LipBackGroundColor;
    $MakeupSelectBox.style.height = '350px';
    $MakeupSelectBox.style.display = "block"; // 셀렉트 박스 보이게 하기
    $lip.style.display = 'block';
    $sliderValue.value = Lipslider;
    // $colorBox2.style.backgroundColor = LipwhatColor;
    whatBtn = 'Lip';
});

// // 아이라인 바르기 버튼을 클릭했을 경우
// $showEyeLineMakeupSelectBox.addEventListener('click', ()=>{
//     $SelectMakeupButton.style.display = 'none';    // 버튼들 안보이게 하기
//     $MakeupSelectBox.style.backgroundColor = '#362349';
//     $MakeupSelectBox.style.display = "block"; // 셀렉트 박스 보이게 하기
//     $sliderValue.value = EyeLineslider;
//     whatBtn = 'EyeLine';
// });

// 셀렉터 박스에서 닫기 버튼을 클릭했을 경우
$closeBtn.addEventListener('click', ()=>{
    $MakeupSelectBox.style.display = "none";    // 셀렉트 박스 안보이게 하기
    $foundation.style.display = 'none';
    $lip.style.display = 'none';
    $SelectMakeupButton.style.display = 'block';   // 버튼들 보이게 하기   
});

// 색상 코드를 post로 전송하는 함수
function colorevent(color) {
    /////////////////// 버튼 값 POST로 전송 ///////////////////
    fetch(`/Color?whatBtn=${whatBtn}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ 'color_code' : color })
    });
}

// 버튼이 선택되면, css로 선택된 버튼을 표시
function changeSelectColor(button) {
    // 이전에 선택한 버튼이 있다면
    if(whatBtn == 'Fd') {
        if(previousFdButton != null) {
            // 이전버튼을 원상태로
            previousFdButton.style.border="";
        }
        // 클릭한 버튼 선택
        button.style.border = '1.5px solid black';
        previousFdButton = button;
    }
    else if(whatBtn == 'Lip') {
        if(previousLipButton != null) {
            // 이전버튼을 원상태로
            previousLipButton.style.border="";
        }
        // 클릭한 버튼 선택
        button.style.border = '1.5px solid black';
        previousLipButton = button;
    }
}

// 색을 찾아서 CSS를 수정하는 함수
function detectionColorButton(detectColor) {
    console.log(whatBtn + " detectColor: " + detectColor);
    let boolean = true;

    // 색상 버튼들을 순회
    for (let i = 0; i < $color_button.length; i++) {
        const button = $color_button[i];

        // 버튼의 실제 배경색을 가져옴 (getComputedStyle을 사용)
        const buttonColor = getComputedStyle(button).backgroundColor;

        // colorMap에서 해당 RGB 값을 찾음
        const colorHex = colorMap.get(buttonColor);

        // 색상이 일치하면 작업을 수행
        if (boolean && (colorHex === detectColor)) {
            console.log("일치하는 색상을 찾음: " + detectColor);

            // 버튼의 css를 수정
            changeSelectColor(button);

            // boolean을 false로 설정 (한 번만 동작하도록)
            boolean = false;
        }
    }
    // 버튼에 없는 색상인 경우,(색상 미선택으로)
    if (boolean) {
        console.log("없는 색상임");
        if(whatBtn == 'Fd') {
            if(previousFdButton != null) {
                // 이전버튼을 색상 미선택으로
                previousFdButton.style.border="";
            }
            previousFdButton = null;
        }
        else if(whatBtn == 'Lip') {
            if(previousLipButton != null) {
                // 이전버튼을 색상 미선택으로
                previousLipButton.style.border="";
            }
            previousLipButton = null;
        }
    }
    console.log("previousFdButton : " + previousFdButton + " previousLipButton : " + previousLipButton);
}

// 셀렉터의 색상 중 하나를 클릭했을 경우 실행되는 동작
$color_button.forEach(button => {
    button.addEventListener('click', () => {
        // 파운데이션 버튼 구역일 때
        if(whatBtn == "Fd") {
            // 이전버튼과 동일한버튼 클릭시 선택안됨(아무것도 선택안한상태)으로 설정 
            if ((previousFdButton != null || previousFdButton != "") && previousFdButton == button) {
                const color = "0";  // "0"을 전송, 버튼 표시는 선택안됨으로
                FdwhatColor = color;

                previousFdButton.style.border=""; // 버튼의 css는 선택안됨으로
                button.style.border="";
                previousFdButton = null;
                colorevent(color); // 색상 post로 서버 전송
            }
            // 색상을 선택했을 때(클릭한 버튼 적용)
            else {
                const color = colorMap.get(button.style.backgroundColor); // 색상 추출
                FdwhatColor = color;

                changeSelectColor(button);  // 선택된 버튼 표시 (css 적용)
                colorevent(color); // 색상 post로 서버 전송
            }
        }
        // 립 버튼 구역일 때
        else if(whatBtn == "Lip") {
            if ((previousLipButton != null || previousLipButton != "") && previousLipButton == button) {
                const color = "0";  // "0"을 전송, 버튼 표시는 선택안됨으로
                LipwhatColor = color;

                previousLipButton.style.border=""; // 버튼의 css는 선택안됨으로
                button.style.border="";
                previousLipButton = null;
                colorevent(color); // 색상 post로 서버 전송
            }
            else {
                const color = colorMap.get(button.style.backgroundColor); // 색상 추출
                LipwhatColor = color;
                changeSelectColor(button);  // 선택된 버튼 표시 (css 적용)
                colorevent(color); // 색상 post로 서버 전송
            }
            
        }
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
    if(whatBtn == "Fd") {
        Fdslider = value;
    }
    else if(whatBtn == "Lip") {
        Lipslider = value;
    }
    // else if(whatBtn == "EyeLine") {
    //     EyeLineslider = value;
    // }
};


// cunnectWebsocket();
// cunnectWebsocket();
// sendConnectPython();
$CamBtn.addEventListener('click', ()=>{
    if(connectCam === false) {   // 캠 미연결이므로 연결 동작 실행
        cunnectWebsocket();
         
        $video.style.backgroundImage = 'url(http://127.0.0.1:8080/video_feed)'; // 캠 스트림 URL 설정
        $video.style.display = 'block';   // 비디오 부분 보이게 설정
        $CamBtn.textContent = '카메라 해제';    // 연결되어 있으므로 연결을 해제 위해 카메라 해제로 명칭 변경
        $SelectMakeupButton.style.display = 'block';
        connectCam = true;
    }
    else {  // 캠이 연결되어 있으니 해제 동작 실행
        // sendDestroyPython();
        $CamBtn.textContent = '카메라 연결';    // 연결 해제되므로 다시 연결을 위해 카메라 연결로 명칭 변경
        $SelectMakeupButton.style.display = 'none';
        streamActive = false; // 상태 업데이트
        $video.style.display = 'none';
        connectCam = false;
    }
});