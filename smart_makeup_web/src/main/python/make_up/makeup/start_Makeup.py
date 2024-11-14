import cv2
from makeup_face import makeup_face
from makeup_lips import makeup_lips
import numpy as np
import os


class start_Makeup():
    def __init__(self):
        self.skin_tuple = {
            (149, 180, 213): '1C0_shell.jpg',        # BGR: (149, 180, 213)
            (159, 192, 223): '1C1_cool_bone.jpg',    # BGR: (159, 192, 223)
            (114, 152, 209): '1C2_petal.jpg',        # BGR: (114, 152, 209)
            (152, 187, 230): '1N0_porcelain.jpg',    # BGR: (152, 187, 230)
            (128, 177, 212): '1N1_ivory_nude.jpg',   # BGR: (128, 177, 212)
            (111, 176, 226): '1W1_bone.jpg',         # BGR: (111, 176, 226)
            (167, 199, 250): '1N2_ecru.jpg',         # BGR: (167, 199, 250)
            (77, 146, 201): '1W2_sand.jpg',          # BGR: (77, 146, 201)
            (103, 165, 222): '2C0_cool_vanilla.jpg', # BGR: (103, 165, 222)
            (107, 164, 214): '2W0_warm_vaniila.jpg', # BGR: (107, 164, 214)
            (109, 164, 216): '2C1_pure_beige.jpg',   # BGR: (109, 164, 216)
            (99, 156, 197): '2W1_dawn.jpg',          # BGR: (99, 156, 197)
            (101, 166, 216): '2W1.5_natural_suede.jpg', # BGR: (101, 166, 216)
            (110, 162, 208): '2N2_buff.jpg',         # BGR: (110, 162, 208)
            (101, 162, 199): '2W2_rattan.jpg',       # BGR: (101, 162, 199)
            (111, 157, 202): '3C0_cool_creme.jpg',   # BGR: (111, 157, 202)
            (79, 141, 200): '3W0_warm_creme.jpg',    # BGR: (79, 141, 200)
            (103, 162, 201): '3W1_tawny.jpg',         # BGR: (103, 162, 201)
            None:None
        }

        self.lip_tuple = {
            (89, 99, 176): '101.jpg',  # BGR: (89, 99, 176)
            (72, 77, 152): '106.jpg',  # BGR: (72, 77, 152)
            (72, 67, 148): '110.jpg',  # BGR: (72, 67, 148)
            (107, 93, 184): '112.jpg', # BGR: (107, 93, 184)
            (25, 42, 148): '120.jpg',  # BGR: (25, 42, 148)
            (81, 91, 177): '201.jpg',  # BGR: (81, 91, 177)
            (78, 70, 190): '212.jpg',  # BGR: (78, 70, 190)
            (81, 21, 175): '302.jpg',  # BGR: (81, 21, 175)
            (19, 15, 169): '303.jpg',   # BGR: (19, 15, 169)
            None:None
        }

        ## 경로는 사용자 환경에 맞게 수정할 것, HairSegmentation.py 9번째 줄도 경로 수정할 것,
        self.skin_root = 'smart_makeup_web\\src\\main\\python\\make_up\\makeup\\makeup_img\\skin'
        self.lip_root = 'smart_makeup_web\\src\\main\\python\\make_up\\makeup\\makeup_img\\lip'


    ## skin_val, lip_val -> 화장여부 boolean / skin_bgr, lip_bgr -> 화장 적용 파일 번호 / s_mix, l_mix -> 화장 투명도
    def run(self, frame, skin_val = False, lip_val= False, skin_bgr= None, lip_bgr=None, s_mix = 100, l_mix=100):
        output = frame.copy()
        
        # 피부, 립 적용 이미지 load 여부
        ## 적용할 화장부위가 있다면 / skin_val, lip_val 중 True가 있다면 실행
        if skin_val or lip_val:
            # final_mask = np.zeros_like(frame)
            lip_result = None
            skin_result = None

            ### boolean 피부 화장여부
            if skin_val:
                if skin_bgr is None:
                    print("피부 파일이 없습니다.")
                ## boolean True 피부 화장 적용
                else:
                    skin_path = os.path.join(self.skin_root, self.skin_tuple[skin_bgr]) 
                    skin_path = cv2.imread(skin_path)   
                    skin = makeup_face()
                    skin_result, skin_mask = skin.run(frame,skin_path, s_mix)
                    # return output
            ### boolean 입술 화장여부
            if lip_val:
                if lip_bgr is None:
                    print("입술 파일이 없습니다.")   
                ## boolean True 입술 화장적용
                else:
                    lip_path = os.path.join(self.lip_root, self.lip_tuple[lip_bgr])
                    lip_path = cv2.imread(lip_path)
                    lip = makeup_lips()
                    lip_result, lip_mask = lip.run(frame,lip_path, l_mix) 
                    
                    # return output
            # final_mask = ~final_mask
            # final_mask = cv2.bitwise_or(frame, final_mask)

            # 화장 적용 여부 (마스킹 직접적용)
            ## lip + skin 둘다 적용하는 경우
            if lip_result is not None and skin_result is not None:
                lip_result = cv2.bitwise_and(lip_result, lip_mask)
                lip_mask = ~lip_mask
                output = cv2.bitwise_and(output, lip_mask)
                output = cv2.bitwise_or(lip_result, output)
                skin_result = cv2.bitwise_and(skin_result, skin_mask)
                skin_mask = ~skin_mask
                output = cv2.bitwise_and(output, skin_mask)
                output = cv2.bitwise_or(skin_result,output)     #배경 제외
            ## lip만 적용하는 경우
            elif lip_result is not None: 
                lip_result = cv2.bitwise_and(lip_result, lip_mask)
                lip_mask = ~lip_mask
                output = cv2.bitwise_and(output, lip_mask)
                output = cv2.bitwise_or(lip_result, output)
            ## skin만 적용하는 경우
            elif skin_result is not None:
                skin_result = cv2.bitwise_and(skin_result, skin_mask)
                skin_mask = ~skin_mask
                output = cv2.bitwise_and(output, skin_mask)
                output = cv2.bitwise_or(skin_result,output)     #배경 제외
        
        return output

# def main():
#     # 이미지 파일 경로
#     image_path = 'path_to_your_image.jpg'  # 여기에 테스트할 이미지 경로를 입력하세요
#     frame = cv2.imread(image_path)

#     # start_Makeup 객체 생성
#     makeup = start_Makeup()

#     # 예시: 피부화장(1번), 입술화장(2번), 투명도 80%로 적용
#     output = makeup.run(frame, skin_val=True, lip_val=True, skin_bgr=(149, 180, 213), lip_bgr=(89, 99, 176), s_mix=80, l_mix=80)

#     # 결과 이미지 출력
#     cv2.imshow('Makeup Result', output)
#     cv2.waitKey(0)
#     cv2.destroyAllWindows()

# if __name__ == "__main__":
#     main()