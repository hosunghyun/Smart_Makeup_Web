(()=>{
    const video = document.getElementById('webcam');
        const startButton = document.getElementById('startButton');
        let currentStream = null;

        // 웹캠 스트림 요청 함수
        function requestWebcamStream() {
            navigator.mediaDevices.getUserMedia({ video: true })
                .then(stream => {
                    // 기존 스트림이 있을 경우 종료
                    if (currentStream) {
                        currentStream.getTracks().forEach(track => track.stop());
                    }
                    currentStream = stream; // 현재 스트림 저장
                    video.srcObject = stream; // 스트리밍 시작

                    // 스트림 종료 이벤트 리스너 추가
                    stream.getTracks().forEach(track => {
                        track.addEventListener('ended', () => {
                            alert("웹캠 연결이 해제되었습니다.");
                            // 비디오 화면은 그대로 유지
                            // 이 부분에서 비디오 요소의 srcObject는 그대로 둡니다.
                        });
                    });
                })
                .catch(error => {
                    // 웹캠이 연결되지 않은 경우 알림
                    alert("웹캠이 컴퓨터에 연결되지 않았거나 사용할 수 없습니다.");
                    console.warn("웹캠을 사용할 수 없습니다.", error);
                });
        }

        // 버튼 클릭 시 웹캠 연결 시도
        startButton.addEventListener('click', () => {
            requestWebcamStream();
        });
})();