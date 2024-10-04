# main.py

## pip install fastapi uvicorn
from fastapi import FastAPI
from pydantic import BaseModel
import uvicorn

app = FastAPI()

# 데이터 모델 정의
class Item(BaseModel):
    name: str
    price: float
    description: str = None
    tax: float = None

# GET 요청 처리
@app.get("/")
def read_root():
    return {"message": "Hello, World!"}

# POST 요청 처리
@app.post("/items/")
def create_item(item: Item):
    return item

## 서버 실행 명령어 uvicorn main:app --reload
if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8080, reload=False)