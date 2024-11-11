import cv2
from skimage.exposure import match_histograms
import numpy as np
import mediapipe as mp
import utils

#==========머리 마스킹 ========
from HairSegmentation import HairSegmentation
from PIL import Image
# from keras.model import load_model
import tensorflow as tf

class makeup_face():
    def __init__(self):
        self.FACE_OVAL=[ 10, 338, 297, 332, 284, 251, 389, 356, 454, 
                    323, 361, 288, 397, 365, 379, 378, 400, 377, 152, 
                    148, 176, 149, 150, 136, 172, 58, 132, 93, 234, 
                    127, 162, 21, 54, 103,67, 109]

        self.LIPS=[ 61, 146, 91, 181, 84, 17, 314, 405, 321, 375, 291, 
            308, 324, 318, 402, 317, 14, 87, 178, 88, 95,
            185, 40, 39, 37,0 ,267 ,269 ,270 ,409, 
            415, 310, 311, 312, 13, 82, 81, 42, 183, 78 ] # 아랫입술+윗입술
        self.LOWER_LIPS =[61, 146, 91, 181, 84, 17, 314, 405, 321, 375, 291, 308, 324, 318, 402, 317, 14, 87, 178, 88, 95]   # 윗입술
        self.UPPER_LIPS=[ 185, 40, 39, 37,0 ,267 ,269 ,270 ,409, 415, 310, 311, 312, 13, 82, 81, 42, 183, 78]    # 아래입술

        self.CLOSE_LIP=[61, 146, 91, 181, 84, 17, 314, 405, 321, 375, 291, 409, 270, 269, 267, 0, 37, 39, 40, 185] # 닫힌입술 (입술+입술안 포함)

        self.LEFT_EYE =[ 362, 382, 381, 380, 374, 373, 390, 249, 263,   466, 388, 387, 386, 385,384, 398 ]
        self.LEFT_EYEBROW =[ 336, 296, 334, 293, 300, 276, 283, 282, 295, 285 ]
        self.LEFT_CLOWN = [357,350,349,348,347,346,340,372, 345,352,411,425,266,371,355]

        self.RIGHT_EYE=[ 33, 7, 163, 144, 145, 153, 154, 155, 133,    173, 157, 158, 159, 160, 161 , 246 ]  
        self.RIGHT_EYEBROW=[ 70, 63, 105, 66, 107, 55, 65, 52, 53, 46 ]
        self.RIGHT_CLOWN=[143,111,117,118,119,120,121,128, 126,142,36,205,187,123,116]

        self.LIPS1_up=[ 61, 96, 89, 179, 86, 316, 403, 319, 325, 291, 
                308, 324, 318, 402, 317, 14, 87, 178, 88, 95,
                185, 40, 39, 37,0 ,267 ,269 ,270 ,409, 
                415, 310, 311, 312, 13, 82, 81, 42, 183, 78 ]
        self.LIPS1_down=[61, 146, 91, 181, 84, 17, 314, 405, 321, 375, 291,
                    325, 319, 403, 316, 86, 179, 89, 96]

        self.RIGHT_EYE_up1 = [247,30,29,27,28,56,190,  133, 173, 157, 158, 159, 160, 161 , 246, 33]
        self.RIGHT_EYE_up2 = [225,224,223,222,221,   133, 173, 157, 158, 159, 160, 161 , 246, 33]
        self.LEFT_EYE_up1 = [414,286,258,257,259,260,467,    263, 466, 388, 387, 386, 385,384, 398, 362 ]
        self.LEFT_EYE_up2 = [441,442,443,444,445,    263, 466, 388, 387, 386, 385,384, 398, 362 ]

        self.DARKCIRCLE_RIGHT= [33, 7, 163, 144, 145, 153, 154, 155, 133,  112,232,231,230,110]
        self.DARKCIRCLE_LEFT= [362, 382, 381, 380, 374, 373, 390, 249, 263, 339,450,451,452,341]

        self.map_face_mesh = mp.solutions.face_mesh
        self.face_mesh = self.map_face_mesh.FaceMesh(min_detection_confidence =0.5, min_tracking_confidence=0.5)    

    def fillPolyTrans(self, img, points, color, opacity):
            """
            @param img: (mat) input image, where shape is drawn.
            @param points: list [tuples(int, int) these are the points custom shape,FillPoly
            @param color: (tuples (int, int, int)
            @param opacity:  it is transparency of image.
            @return: img(mat) image with rectangle draw.

            """
            list_to_np_array = np.array(points, dtype=np.int32)
            overlay = img.copy()  # coping the image
            cv2.polylines(img, [list_to_np_array], True, color,1, cv2.LINE_AA)
            cv2.fillPoly(overlay,[list_to_np_array], color )
            
            new_img = cv2.addWeighted(overlay, opacity, img, 1 - opacity, 0)
            # print(points_list)
            img = new_img
            
            return img
    
    # landmark detection function 
    def landmarksDetection(self, img, results, draw=False):
        img_height, img_width= img.shape[:2]
        # list[(x,y), (x,y)....]
        mesh_coord = [(int(point.x * img_width), int(point.y * img_height)) for point in results.multi_face_landmarks[0].landmark]
        if draw :
            [cv2.circle(img, p, 2, utils.GREEN, -1) for p in mesh_coord]

        # returning the list of tuples for each landmarks 
        return mesh_coord
    
    def draw_FACE_OVAL(self, frame, mask, colors, ref_img, results):      #피부만 눌렸을때
        # Inialize hair segmentation model / 머리카락 세분화 모델을 초기화합니다.
        hair_segmentation = HairSegmentation()
        copy = frame.copy()
        hair_mask = hair_segmentation(frame)

        # Get dyed frame. / 염료 처리된 프레임을 가져옵니다.
        dyed_image = mask.copy()
        dyed_image[:] = 255,255,255
        
        # Mask our dyed frame (pixels out of mask are black). / 염료 처리된 프레임에서 마스크를 적용합니다 (마스크 밖의 픽셀은 검은색).
        dyed_hair = cv2.bitwise_or(frame, dyed_image, mask=hair_mask) ################### 이거로 #########################
        dyed_hair =~dyed_hair

        if results.multi_face_landmarks:
            mesh_coords = self.landmarksDetection(frame, results, False)
            #====== 머리 추가 =========================================================
            addHair = mesh_coords[18][1] - mesh_coords[152][1]    # 18번매쉬.y좌표 - 0번매쉬.y좌표 / 18번을 [0, 14, 17, 18, 200, 199, 175] 중 1택 가능
            
            for i in [127, 162, 21, 54, 103,67, 109, 10, 338, 297, 332, 284, 251, 389, 356]:
                mesh_coords[i] = (mesh_coords[i][0], mesh_coords[i][1] + addHair)
            #==========================================================================

            mask =self.fillPolyTrans(mask, [mesh_coords[p] for p in self.FACE_OVAL], colors, opacity=1 )

            # 입술 뺄 시 아래 코드 사용
            mask =utils.fillPolyTrans(mask, [mesh_coords[p] for p in self.CLOSE_LIP], (0,0,0), opacity=1 )   # (입술+입술 내부) 포함해서 뺌

            # # 머리
            mask = cv2.bitwise_and(mask, dyed_hair)

            mask_copy = mask.copy
            mask_copy =~mask
            # #  마스킹 하기
            masked = cv2.bitwise_or(copy, mask_copy)
            # ref_img = makeup_ref_img.draw_FACE_OVAL(ref_img,utils.WHITE)
            matched = match_histograms(masked,ref_img, channel_axis= -1)

            # s_weighted_img = cv2.addWeighted(matched, s_mix/100, masked, 1-(s_mix/100), 0) # 두개의 이미지를 가중치에 따라서 다르게 보여줍니다.
            
            return matched, mask
        else:
            return None, None
                    
    def run(self, frame, ref_img):
        map_face_mesh = mp.solutions.face_mesh
        face_mesh = map_face_mesh.FaceMesh(min_detection_confidence =0.5, min_tracking_confidence=0.5)   
        mask = np.zeros_like(frame)
        results = face_mesh.process(frame)
        skin_mask = mask.copy()

        skin_result, skin_mask = self.draw_FACE_OVAL(frame, skin_mask, utils.WHITE, ref_img, results)


        return skin_result, skin_mask