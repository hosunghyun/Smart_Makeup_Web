import cv2
import os


# input_directory = 'pre_ref_img/skin'


input_directory_lip = 'makeup_img\lip'
input_directory_skin = 'makeup_img\skin'
lip_tuple = {}
skin_tuple = {}

try:
    # 디렉토리 내의 모든 파일 검사
    for i, filename in enumerate(os.listdir(input_directory_lip)):
        if filename.lower().endswith('.jpg'):
            # AVIF 파일의 전체 경로
            lip_tuple[i] = filename

    # 디렉토리 내의 모든 파일 검사
    for i, filename in enumerate(os.listdir(input_directory_skin)):
        if filename.lower().endswith('.jpg'):
            # AVIF 파일의 전체 경로
            skin_tuple[i] = filename
            
    print(lip_tuple)   
    print(skin_tuple)
except Exception as e:
    print(f"디렉토리 처리 중 에러 발생: {str(e)}")

