import uvicorn

from fastapi import FastAPI, WebSocket, WebSocketDisconnect, Request, Response
from fastapi.responses import HTMLResponse
from fastapi.templating import Jinja2Templates
from pydantic import BaseModel

import cv2
import asyncio  # 비동기처리
from fastapi.staticfiles import StaticFiles

from pydantic import BaseModel, field_validator

# cors 미들웨어
from fastapi.middleware.cors import CORSMiddleware

import numpy as np

import threading

app = FastAPI()

# CORS 미들웨어 추가
# origins = [
#     "http://localhost.tiangolo.com",
#     "https://localhost.tiangolo.com",
#     "http://localhost",
#     "http://localhost:8080",
#     "http://localhost:9090",
# ]

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 모든 도메인 허용
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

params = {}
params_lock = threading.Lock()
params_file = "src\\main\\java\\test\\cors\\connection\\connect\\params.txt"

# def read_params():
#     global params
#     with params_lock:
#         with open(params_file, "r") as f:
#             lines = f.readlines()
#             for line in lines:
#                 key, value = line.strip().split("=")
#                 params[key] = int(value)

def load_params():
    """파일에서 파라미터를 읽어와 params 딕셔너리를 초기화합니다."""
    global params
    try:
        with params_lock:
            with open(params_file, "r") as f:
                lines = f.readlines()
                for line in lines:
                    key, value = line.strip().split("=")
                    if value.startswith("(") and value.endswith(")"):  # 튜플 형식으로 저장된 경우
                        params[key] = tuple(map(int, value.strip("()").split(",")))
                    else:
                        params[key] = str(value)
    except FileNotFoundError:
        params = {}  # 파일이 없으면 빈 딕셔너리로 초기화

def set_TxtValue(key, value):

    # 모든 값을 문자열로 변환
    value = str(value)

    """키와 값을 설정하고 params.txt에 기록합니다. 주어진 키의 값은 갱신합니다."""
    # 파일에서 현재 값을 읽어옵니다.
    try:
        with open(params_file, "r") as f:
            for line in f:
                k, v = line.strip().split("=")
                params[k] = v
    except FileNotFoundError:
        pass  # 파일이 없으면 무시

    # 주어진 키가 이미 존재하는 경우 값을 변경
    if key in params:
        if isinstance(value, tuple):  # 튜플이면 괄호 추가
            params[key] = f"({','.join(map(str, value))})"
        else:
            params[key] = value  # 문자열로 저장
    else:
        # 새로운 키일 경우 기존 방식으로 추가
        if isinstance(value, tuple):  # 튜플이면 괄호 추가
            params[key] = f"({','.join(map(str, value))})"
        else:
            params[key] = value  # 문자열로 저장

    # 파일에 모든 키-값 쌍을 기록합니다.
    with params_lock:
        with open(params_file, "w") as f:
            for k, v in params.items():
                f.write(f"{k}={v}\n")  # 기록










# def set_TxtValue(key, value):
#     """키와 값을 설정하고 params.txt에 기록합니다. 중복 키가 있을 경우 갱신하고, 없으면 추가합니다."""
#     with params_lock:
#         # 기존 키가 있다면 갱신
#         if key in params:
#             params[key] = value
#         else:
#             # 새로운 키-값 쌍 추가
#             params[key] = value

#         # 파일에 모든 키-값 쌍을 한 줄씩 기록
#         with open(params_file, "w") as f:
#             for k, v in params.items():
#                 if isinstance(v, tuple):  # 튜플인 경우 괄호와 함께 기록
#                     v_str = f"({','.join(map(str, v))})"
#                     f.write(f"{k}={v_str}\n")
#                 else:
#                     f.write(f"{k}={v}\n")






app = FastAPI()
templates = Jinja2Templates(directory="src\\main\\resources\\templates")

class Foundation(BaseModel):
    opacity: int = 0
    hex: str = "0"
    # colorBGR: np.ndarray = None
    # class Config:
    #     arbitrary_types_allowed = True  # numpy.ndarray와 같은 임의 타입을 허용

def hex_to_bgr(hex_code):
    # 헥사 코드 형식 확인
    if not isinstance(hex_code, str) or not (len(hex_code) == 7 and hex_code.startswith('#')):
        raise ValueError("유효한 헥사 코드 형식이 아닙니다. 예: '#RRGGBB'")
    
    hex_code = hex_code.lstrip('#')
    # 헥사 코드가 6자리인지 확인
    if len(hex_code) != 6:
        raise ValueError("유효한 헥사 코드 형식이 아닙니다. 예: '#RRGGBB'")
    try:
        r = int(hex_code[0:2], 16)
        g = int(hex_code[2:4], 16)
        b = int(hex_code[4:6], 16)
    except ValueError:
        raise ValueError("헥사 코드에 잘못된 문자가 포함되어 있습니다.")
    # BGR 형식으로 반환
    return (b, g, r)

# def update_value(file_path, key, value):
#     with open(file_path, 'r') as file:
#         lines = file.readlines()

#     with open(file_path, 'w') as file:
#         for line in lines:
#             # 줄을 공백으로 나누기 전에 유효성 검사
#             if line.strip() == '':
#                 continue  # 빈 줄은 건너뛰기
#             if ':' not in line:
#                 continue  # ':'가 없는 줄은 건너뛰기
            
#             try:
#                 k, v = line.strip().split(':', 1)  # 최대 1회만 분할
#                 if k == key:
#                     file.write(f"{key}:{value}\n")
#                 else:
#                     file.write(line)  # 기존의 줄을 그대로 유지
#             except ValueError:
#                 continue  # 에러가 발생하면 해당 줄은 무시

#         # 마지막에 새로운 키-값 쌍을 추가
#         if not any(k == key for k, _ in (line.strip().split(':', 1) for line in lines if ':' in line)):
#             file.write(f"{key}:{value}\n")


# def update_value(file_path, key, value):
#     try:
#         with open(file_path, 'r') as file:
#             lines = file.readlines()

#         with open(file_path, 'w') as file:
#             found_key = False
#             for line in lines:
#                 line = line.strip()
#                 # 빈 줄 또는 잘못된 형식의 줄은 건너뛰기
#                 if line == '' or ':' not in line:
#                     continue
                
#                 k, v = line.split(':', 1)
#                 if k == key:
#                     file.write(f"{key}:{value}\n")
#                     found_key = True  # 키를 찾았음을 기록
#                 else:
#                     file.write(line + '\n')  # 기존 줄 그대로 유지

#             # 키가 존재하지 않는 경우 추가
#             if not found_key:
#                 file.write(f"{key}:{value}\n")
                
#     except Exception as e:
#         print(f"오류 발생: {e}")




# def set_TxtValue(txtFile, key, value):
#     # 파일에 기록
#     with open(txtFile, "w") as f:
#         f.write(f"{key}={value}\n")
#         print("된듯?")

# 전역 변수로 foundation 인스턴스 생성
foundation = Foundation()
current_websocket = None  # 현재 웹소켓 연결을 저장할 변수




@app.post("/slider")
async def slider_data(data: Foundation):
    set_TxtValue("opacity", data.opacity)
    print("Received:", data.opacity, type(data.opacity))
    return {"message": "Data received", "received": data.opacity}



@app.post("/btnColor")
async def slider_data(data: Foundation):
    print(params_file, data.hex, type(data.hex))
    bgr_color = hex_to_bgr(data.hex)
    set_TxtValue("hex", data.hex)
    set_TxtValue("bgr_color", bgr_color)
    print(f"bgr = {bgr_color}")
    return {"message": "Data received", "received": data.hex}





def putText_frames(frame_copy, text, color, yArea):
    if not isinstance(text, str):
        text = str(text)
    return cv2.putText(frame_copy, text, (10, yArea), 
                       cv2.FONT_HERSHEY_SIMPLEX, 1, 
                       color, 2)

async def video_feed(websocket: WebSocket):
    """ 웹소켓을 통해 웹캠 영상을 클라이언트에 스트리밍하는 함수 """
    await websocket.accept()
    
    # 웹캠 비디오 캡처 초기화
    cap = cv2.VideoCapture(0)

    try:
        while True:
            # 프레임 읽기
            ret, frame = cap.read()
            if not ret: # 프레임을 못읽었다면, break
                break

            load_params()  # 주기적으로 매개변수 읽기

            # frame = putText_frames(frame, yourDataObject.value, 10)
            # # 매개변수 적용 (예: 밝기와 대비 조정)
            # frame = cv2.convertScaleAbs(frame, alpha=params["contrast"], beta=params["brightness"])

            # 매개변수를 텍스트로 출력
            print(f"opacity: {params['opacity']}, hex: {params['hex']}, bgr_color: {params['bgr_color']}")
            print(f"opacity: {type(params['opacity'])}, hex: {type(params['hex'])}, bgr_color: {type(params['bgr_color'])}")

            text = f"opacity: {params['opacity']}, hex: {params['hex']}, bgr_color: {params['bgr_color']}"
            putText_frames(frame, text, params['bgr_color'], 30)

            
            # 프레임을 JPEG 형식으로 인코딩
            _, buffer = cv2.imencode('.jpg', frame)
            bytes_data = buffer.tobytes()
            
            # 웹소켓을 통해 클라이언트에 전송
            await websocket.send_bytes(bytes_data)

    # except WebSocketDisconnect:
    #     print("클라이언트 연결이 끊어졌습니다.")

    # finally:
    #     cap.release()  # 비디오 캡처 객체 해제
    #     await websocket.close()
    except WebSocketDisconnect:
        print("클라이언트가 페이지를 벗어났습니다.")
    except Exception as e:
        print(f"예외 발생: {e}")
    finally:
        cap.release()
        try:
            if websocket.client_state == "connected":  # 연결 상태 확인
                await websocket.close()
        except RuntimeError as e:
            print(f"WebSocket 종료 오류: {e}")


@app.websocket("/video-feed")
async def feed(websocket: WebSocket):
    """ 웹소켓 엔드포인트 """
    # await websocket.accept()

    # # 클라이언트로부터 초기 데이터 수신
    # data = await websocket.receive_text()  # 클라이언트가 전송한 데이터 수신
    # yourDataObject = YourDataObject(value=data)  # 수신한 데이터로 객체 생성

    await video_feed(websocket)









def is_camera_in_use(camera_index=0):
    # 카메라를 열어보려 시도
    cap = cv2.VideoCapture(camera_index)
    
    if not cap.isOpened():
        return True  # 카메라가 이미 사용 중임

    # 카메라를 정상적으로 열었으면 닫기
    cap.release()
    return False  # 카메라가 사용 중이지 않음

## 서버 실행 명령어 uvicorn main:app --reload
if __name__ == "__main__":
    if is_camera_in_use():
        print("웹캠이 이미 사용 중입니다.")
    else:
        print("웹캠을 사용할 수 있습니다.")
    uvicorn.run(app, host="127.0.0.1", port=8080, reload=False) # 디버깅아님
    uvicorn.run(app, host="127.0.0.1", port=8080, reload=True) # 디버깅모드임
    # uvicorn.run(app, port=8080)

## /docs 참고하면, 자동 대화형 API 문서 확인 가능
## tasklist | findstr python
## taskkill /F /PID 포트번호