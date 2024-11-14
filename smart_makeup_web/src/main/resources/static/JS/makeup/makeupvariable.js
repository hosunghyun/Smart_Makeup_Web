//////////// 셀렉트 박스 부분 //////////////
const $SelectMakeupButton = document.getElementById('SelectMakeupButton');                      // 셀렉트 박스 버튼 전체 부분
const $showFdMakeupSelectBox = document.getElementById('showFdMakeupSelectBox');                // 파운데이션 바르기 버튼
const $showLipMakeupSelectBox = document.getElementById('showLipMakeupSelectBox');              // 립 바르기 버튼
// const $showEyeLineMakeupSelectBox = document.getElementById('showEyeLineMakeupSelectBox');      // 아이라인 버튼
const $MakeupSelectBox = document.getElementById('MakeupSelectBox');                            // 셀렉트 박스

// const $colorBox1 = document.getElementById('colorBox1');                                          // 셀렉트 박스에 있는 색상 박스
// const $colorBox2 = document.getElementById('colorBox2');                                          // 셀렉트 박스에 있는 색상 박스

const $color_button = document.querySelectorAll('.color-button');                                // 셀렉터에 존재하는 색상들을 전부 불러오기

const $sliderValue = document.getElementById('sliderValue');                                    // 투명도를 위한 슬라이더 값

const $closeBtn = document.getElementById('closeBtn');                                          // 셀렉터 닫기 버튼
                                                                                  
const $foundation = document.getElementById('foundation');                                       // 파운데이션인지 립인지 아이라인인지 구분하기 위한 변수
const $lip = document.getElementById('lip');

const $loadbtn = document.getElementById('loadbtn');
const $savenumber = document.querySelectorAll('[id^="save_"]');
const $save_1 = document.getElementById('save_1');
const $save_2 = document.getElementById('save_2');
const $save_3 = document.getElementById('save_3');
const $save_4 = document.getElementById('save_4');
const $save_5 = document.getElementById('save_5');
const $savebtn = document.getElementById('savebtn');

// 사용자가 어디에 화장 정보를 저장할 건지에 대한 버튼
let isNumberClick = false;
let whatNumber = 1;

// 어느 부위를 선택한건지를 나타내는 버튼
let whatBtn; 

// 부위별 투명도를 조절하기 위한 슬라이더 값
let Fdslider = 0;
let Lipslider = 0;
// let EyeLineslider = 0;

// 화장품 종류별 색상
let FdwhatColor = "0";
let LipwhatColor = "0";
// let EyeLinewhatColor = "#00000000";


//// 추가한것

// 색상 이름과 HEX 값을 매핑하는 Map 객체 생성
const colorMap = new Map([
    ['rgb(176, 99, 89)', '#b06359'],
    ['rgb(152, 77, 72)', '#984d48'],
    ['rgb(148, 67, 72)', '#944348'],
    ['rgb(184, 93, 107)', '#b85d6b'],
    ['rgb(148, 42, 25)', '#942a19'],
    ['rgb(177, 91, 81)', '#b15b51'],
    ['rgb(190, 70, 78)', '#be464e'],
    ['rgb(175, 21, 81)', '#af1551'],
    ['rgb(169, 15, 19)', '#a90f13'],
    
    ['rgb(213, 180, 149)', '#d5b495'],
    ['rgb(230, 187, 152)', '#e6bb98'],
    ['rgb(239, 201, 154)', '#efc99a'],
    ['rgb(223, 192, 159)', '#dfc09f'],
    ['rgb(212, 177, 128)', '#d4b180'],
    ['rgb(226, 176, 111)', '#e2b06f'],
    ['rgb(209, 152, 114)', '#d19872'],
    ['rgb(250, 199, 167)', '#fac7a7'],
    ['rgb(201, 146, 77)', '#c9924d'],
    ['rgb(222, 165, 103)', '#dea567'],
    ['rgb(214, 164, 107)', '#d6a46b'],
    ['rgb(216, 164, 109)', '#d8a46d'],
    ['rgb(197, 156, 99)', '#c59c63'],
    ['rgb(216, 166, 101)', '#d8a665'],
    ['rgb(208, 162, 110)', '#d0a26e'],
    ['rgb(199, 162, 101)', '#c7a265'],
    ['rgb(202, 157, 111)', '#ca9d6f'],
    ['rgb(200, 141, 79)', '#c88d4f'],
    ['rgb(201, 162, 103)', '#c9a267']
]);

// 각 샐랙트박스 배경색
const FdBackGroundColor = "#339adf";
const LipBackGroundColor = "#ec7b7b";

// 슬라이더 박스 배경 (디자인이 샐랙트박스 배경색과 똑같이 해야함)
const $sliderValueText = document.getElementById('sliderValueText');

// 색상 중, 이전에 선택한 색상을 지정
let previousFdButton = null;
let previousLipButton = null;