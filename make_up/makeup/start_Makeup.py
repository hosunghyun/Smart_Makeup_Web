import cv2
from makeup_face import makeup_face
from makeup_lips import makeup_lips
import numpy as np
import os


class start_Makeup():
    def __init__(self):
        self.lip_tuple = {0: '101.jpg', 1: '106.jpg', 2: '110.jpg', 3: '112.jpg', 4: '120.jpg', 5: '201.jpg', 6: '212.jpg', 7: '302.jpg', 8: '303.jpg', 
                          9: 'extra.jpg', 10: 'fall.jpg', 11: 'spring.jpg', 12: 'summer.jpg', 13: 'winter.jpg'}
        self.skin_tuple = {0: '1C0_shell.jpg', 1: '1C1_cool_bone.jpg', 2: '1C2_petal.jpg', 3: '1N0_porcelain.jpg', 4: '1N1_ivory_nude.jpg', 5: '1N2_ecru.jpg', 
                           6: '1W0_warm_porcelain.jpg', 7: '1W1_bone.jpg', 8: '1W2_sand.jpg', 9: '2C0_cool_vanilla.jpg', 10: '2C1_pure_beige.jpg', 11: '2N2_buff.jpg', 
                           12: '2W0_warm_vaniila.jpg', 13: '2W1.5_natural_suede.jpg', 14: '2W1_dawn.jpg', 15: '2W2_rattan.jpg', 16: '3C0_cool_creme.jpg', 17: '3W0_warm_creme.jpg', 
                           18: '3W1_tawny.jpg', 19: 'extra.jpg', 20: 'fall.jpg', 21: 'spring.jpg', 22: 'summer.jpg', 23: 'winter.jpg'}
        
        self.skin_root = 'makeup_img\\skin'
        self.lip_root = 'makeup_img\\lip'


    ## skin_val, lip_val -> 화장여부 boolean / skin_num, lip_num -> 화장 적용 파일 번호 / s_mix, l_mix -> 화장 투명도
    def run(self, frame, skin_val = False, lip_val= False, skin_num= None, lip_num=None, s_mix = 100, l_mix=100):
        output = frame.copy()
        
        # 피부, 립 적용 이미지 load 여부
        ## 적용할 화장부위가 있다면 / skin_val, lip_val 중 True가 있다면 실행
        if skin_val or lip_val:
            # final_mask = np.zeros_like(frame)
            lip_result = None
            skin_result = None

            ### boolean 피부 화장여부
            if skin_val:
                if skin_num is None:
                    print("피부 파일이 없습니다.")
                ## boolean True 피부 화장 적용
                else:
                    skin_path = os.path.join(self.skin_root, self.skin_tuple[skin_num]) 
                    skin_path = cv2.imread(skin_path)   
                    skin = makeup_face()
                    skin_result, skin_mask = skin.run(frame,skin_path, s_mix)
                    # return output
            ### boolean 입술 화장여부
            if lip_val:
                if lip_num is None:
                    print("입술 파일이 없습니다.")   
                ## boolean True 입술 화장적용
                else:
                    lip_path = os.path.join(self.lip_root, self.lip_tuple[lip_num])
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
#     output = makeup.run(frame, skin_val=True, lip_val=True, skin_num=1, lip_num=2, s_mix=80, l_mix=80)

#     # 결과 이미지 출력
#     cv2.imshow('Makeup Result', output)
#     cv2.waitKey(0)
#     cv2.destroyAllWindows()

# if __name__ == "__main__":
#     main()