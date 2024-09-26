const { spawn } = require('child_process');
const path = require('path');

// 절대 경로를 사용하여 Python 스크립트 실행
const scriptPath = path.join(__dirname, 'test.py'); // 현재 디렉토리의 test.py 파일

const result = spawn('py', [scriptPath]);

// 표준 출력 처리
result.stdout.on('data', (data) => {
    console.log(`출력: ${data}`);
});

// 표준 오류 처리
result.stderr.on('data', (data) => {
    console.error(`오류: ${data}`);
});

// 프로세스 종료 처리
result.on('close', (code) => {
    console.log(`프로세스 종료, 코드: ${code}`);
});



// var spawn = require('child_process').spawn;

// // 2. spawn을 통해 "python 파이썬파일.py" 명령어 실행
// const result = spawn('py', ['.\test.py']);

// console.log("실행되었습니다");

// // 표준 출력 처리
// result.stdout.on('data', (data) => {
//         console.log(`출력: ${data}`);
// });

// // 표준 오류 처리
// result.stderr.on('data', (data) => {
// console.error(`오류: ${data}`);
// });

// // 프로세스 종료 처리
// result.on('close', (code) => {
// console.log(`프로세스 종료, 코드: ${code}`);
// });