import cv2
from skimage.exposure import match_histograms
import pandas as pd
from makeup_face import makeup_face
from makeup_lips import makeup_lips
import numpy as np
import os
from config import Config

os.environ['OMP_NUM_THREADS'] = '1'
os.environ['OPENBLAS_NUM_THREADS'] = '1'
os.environ['MKL_NUM_THREADS'] = '1'
os.environ['VECLIB_MAXIMUM_THREADS'] = '1'
os.environ['NUMEXPR_NUM_THREADS'] = '1'
os.environ['KMP_DUPLICATE_LIB_OK']='TRUE'


class start_Makeup():
    def __init__(self, config):
        self.skin_val = config.skin_val
        self.skin_root = config.skin_root
        self.skin_name = config.skin_name

        self.lip_val = config.lip_val
        self.lip_root = config.lip_root
        self.lip_name = config.lip_name

    def run(self, frame):
        output = frame.copy()
        if self.skin_val or self.lip_val:
            final_mask = np.zeros_like(frame)
            lip_result = None
            skin_result = None
            if self.skin_val:
                if self.skin_name:
                    skin_path = os.path.join(self.skin_root, self.skin_name) 
                    skin_path = cv2.imread(skin_path)
                    skin = makeup_face()
                    skin_result, skin_mask = skin.run(frame,skin_path)
                else:
                    print("피부 파일이 없습니다.")
                    # return output
            if self.lip_val:
                if self.lip_name:
                    lip_path = os.path.join(self.lip_root, self.lip_name)
                    lip_path = cv2.imread(lip_path)
                    lip = makeup_lips()
                    lip_result, lip_mask = lip.run(frame,lip_path)         
                else:
                    print("입술 파일이 없습니다.")
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
            
            # final_mask = cv2.bitwise_and(lip_mask, skin_mask)
            # cv2.imshow('final_mask', final_mask)
            # output = cv2.bitwise_or(output, final_mask)
            # cv2.imshow('output3', output)
            
        
        return output



if __name__=="__main__":
    # os.environ['KMP_DUPLICATE_LIB_OK']='True'
    
    cam = cv2.VideoCapture(0)
    while True:
        _, frame = cam.read()
        conf = Config().initialize()
        Make = start_Makeup(conf)
        output = Make.run(frame)
        cv2.imshow('output', output)
        key = cv2.waitKey(1)
        if key & 0xFF == 27 : # enter ESC       
            break
        


        
        



