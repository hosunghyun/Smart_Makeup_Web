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
        
        
        self.skin_root = 'makeup_img\skin'
        

        
        self.lip_root = 'makeup_img\lip'
        

        
    def run(self, frame, skin_val = False, lip_val= False, skin_num= None, lip_num=None, s_mix = 100, l_mix=100):
        output = frame.copy()
        
        if skin_val or lip_val:
            final_mask = np.zeros_like(frame)
            lip_result = None
            skin_result = None
            if skin_val:
                if skin_num is None:
                    print("피부 파일이 없습니다.")
                else:
                    skin_path = os.path.join(self.skin_root, self.skin_tuple[skin_num]) 
                    skin_path = cv2.imread(skin_path)   
                    skin = makeup_face()
                    skin_result, skin_mask = skin.run(frame,skin_path, s_mix)
                    # return output
            if lip_val:
                if lip_num is None:
                    print("입술 파일이 없습니다.")        
                else:
                    lip_path = os.path.join(self.lip_root, self.lip_tuple[lip_num])
                    lip_path = cv2.imread(lip_path)
                    lip = makeup_lips()
                    lip_result, lip_mask = lip.run(frame,lip_path, l_mix) 
                    
                    # return output
            # final_mask = ~final_mask
            # final_mask = cv2.bitwise_or(frame, final_mask)
            if lip_result is not None and skin_result is not None:
                lip_result = cv2.bitwise_and(lip_result, lip_mask)
                lip_mask = ~lip_mask
                output = cv2.bitwise_and(output, lip_mask)
                output = cv2.bitwise_or(lip_result, output)
                skin_result = cv2.bitwise_and(skin_result, skin_mask)
                skin_mask = ~skin_mask
                output = cv2.bitwise_and(output, skin_mask)
                output = cv2.bitwise_or(skin_result,output)     #배경 제외
            elif lip_result is not None: 
                lip_result = cv2.bitwise_and(lip_result, lip_mask)
                lip_mask = ~lip_mask
                output = cv2.bitwise_and(output, lip_mask)
                output = cv2.bitwise_or(lip_result, output)
            elif skin_result is not None:
                skin_result = cv2.bitwise_and(skin_result, skin_mask)
                skin_mask = ~skin_mask
                output = cv2.bitwise_and(output, skin_mask)
                output = cv2.bitwise_or(skin_result,output)     #배경 제외
        
        return output