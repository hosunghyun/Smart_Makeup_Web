////////////////// 슬라이더 값 POST로 전송 ///////////////////
function sendSliderValue(value, endpoint) {
    fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ opacity: value })
    });
}
/////////////////// 버튼 값 POST로 전송 ///////////////////
function sendButtonValue(value, endpoint) {
    fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ hex: value })
    });
}

//////////////////////////////////// 버튼 보이기 ////////////////////////////////////
function showHideSelect(showId, hideId1, hideId2) {
    document.querySelector(`#${showId}`).style.display = "block"; // 보이게
    document.querySelector(`#${hideId1}`).style.display = "none"; // 숨기기
    document.querySelector(`#${hideId2}`).style.display = "none"; // 숨기기
}
////////////////// 색 바꾸기 //////////////////
function changeColor(color, htmlId) {
    const colorBox = document.getElementById(htmlId); // id로 요소 찾기
    if (colorBox) {
        colorBox.style.backgroundColor = color;
        colorBox.style.backgroundImage = 'none'; // 배경 패턴 없애기
        console.log(`선택된 색상: ${color}`); // 선택된 색상 HEX 코드 콘솔 로그 출력
    } else {
        console.error(`요소 '${htmlClass}'를 찾을 수 없습니다.`);
    }
}

//////////////////// 동작과 동시에 비디오 연결 //////////////////////
const videoElement = document.getElementById('video');
const websocket = new WebSocket('ws://127.0.0.1:8080/video-feed');

websocket.onmessage = function(event) {
    const blob = new Blob([event.data], { type: 'image/jpeg' });
    const url = URL.createObjectURL(blob);
    videoElement.src = url;
};

websocket.onclose = function(event) {
    console.log('페이지 초기 : WebSocket closed:', event);
};

//////////////////// 비디오 연결 함수 //////////////////////
function cunnectWebsocket() {
    const videoElement = document.getElementById('video');
    const websocket = new WebSocket('ws://127.0.0.1:8080/video-feed');
    websocket.onmessage = function(event) {
        const blob = new Blob([event.data], { type: 'image/jpeg' });
        const url = URL.createObjectURL(blob);
        videoElement.src = url;
    };
}

///////////////// 비디오 버튼 /////////////////
const video = document.getElementById('video');
const connectButton = document.getElementById('connectButton');
const disconnectButton = document.getElementById('disconnectButton');

let streamActive = false; // 스트림 상태 변수

// 캠 연결 버튼 클릭 시 스트림 URL 설정
connectButton.addEventListener('click', () => {
    cunnectWebsocket();
    video.style.backgroundImage = 'url(http://localhost:8080/video_feed)'; // 캠 스트림 URL 설정
    video.classList.remove('hidden'); // 박스 보이기
    connectButton.classList.add('hidden'); // 연결 버튼 숨김
    disconnectButton.classList.remove('hidden'); // 해제 버튼 표시
    streamActive = true; // 스트림 상태 업데이트
    showHideSelect('SelectMakeupButton', 'LipMakeupSelectBox', 'FdMakeupSelectBox');
    sendConnectPython(); 
    cunnectWebsocket();
});
// 캠 해제 버튼 클릭 시
disconnectButton.addEventListener('click', () => {
    document.querySelector('#SelectMakeupButton').style.display = "none"; // 숨기기
    document.querySelector('#LipMakeupSelectBox').style.display = "none"; // 숨기기
    document.querySelector('#FdMakeupSelectBox').style.display = "none"; // 숨기기
    // video.style.backgroundImage = ''; // 스트림 해제
    video.classList.add('hidden'); // 박스 숨기기
    connectButton.classList.remove('hidden'); // 연결 버튼 표시
    disconnectButton.classList.add('hidden'); // 해제 버튼 숨김
    streamActive = false; // 상태 업데이트
    alert('캠이 해제되었습니다.'); // 경고 창 표시
});


///////////// python 프로세스 연결 //////////////
function sendConnectPython() {
    const practiceButton = document.getElementById('practiceButton');
    const DestroyButton = document.getElementById('DestroyButton');
    fetch('/practice', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(response => {
        if (response.ok) {
            practiceButton.style.display = 'none'; // 버튼 숨기기
            DestroyButton.style.display = 'block'; // 버튼 보이기
            alert('/practice: 보냄이 완료되었습니다.');
            sendDestroyPython();
        } else {
            alert('/practice: 보내는 데 오류가 발생했습니다.');
        }
    })
    .catch(error => {
        alert('/practice: 네트워크 오류가 발생했습니다: ' + error);
    });
}
///////////// python 프로세스 종료 //////////////
function sendDestroyPython() {
    const practiceButton = document.getElementById('practiceButton');
    const DestroyButton = document.getElementById('DestroyButton');
    fetch('/shutdown', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(response => {
        if (response.ok) {
            DestroyButton.style.display = 'none'; // 버튼 숨기기
            practiceButton.style.display = 'block'; // 버튼 보이기
            alert('/shutdown: 보냄이 완료되었습니다.');
        } else {
            alert('/shutdown: 보내는 데 오류가 발생했습니다.');
        }
    })
    .catch(error => {
        alert('/shutdown: 네트워크 오류가 발생했습니다: ' + error);
    });
}