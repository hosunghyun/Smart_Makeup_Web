import cv2
from skimage.exposure import match_histograms
import pandas as pd
import numpy as np
import mediapipe as mp
import utils
import makeup_ref_img

#==========머리 마스킹 ========
from HairSegmentation import HairSegmentation
from PIL import Image
# from keras.model import load_model
import tensorflow as tf

class makeup_lips():
    def __init__(self):
        self.LIPS=[ 61, 146, 91, 181, 84, 17, 314, 405, 321, 375, 291, 
            308, 324, 318, 402, 317, 14, 87, 178, 88, 95,
            185, 40, 39, 37,0 ,267 ,269 ,270 ,409, 
            415, 310, 311, 312, 13, 82, 81, 42, 183, 78 ] # 아랫입술+윗입술

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
            new_img = cv2.addWeighted(overlay, opacity, img, 1 - opacity, 0)
            # print(points_list)
            img = new_img
            cv2.fillPoly(overlay,[list_to_np_array], color )
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
    
    def draw_LIPS(self, frame, mask, colors, ref_img, results, l_mix):
        copy = frame.copy()
        if results.multi_face_landmarks:
            print(1)
            mesh_coords = self.landmarksDetection(frame, results, False)
            mask = utils.fillPolyTrans(mask, [mesh_coords[p] for p in self.LIPS], colors, opacity=1)
            mask_copy = mask.copy()
            mask_copy =~mask
            ##마스킹 하기
            masked = cv2.bitwise_or(copy, mask_copy)
            # cv2.imshow('lip_result', masked)  
            # ref_img = makeup_ref_img.draw_LIPS(ref_img,utils.WHITE)
            matched = match_histograms(masked, ref_img, channel_axis=-1)
            m_weighted_img = cv2.addWeighted(matched,l_mix/100, masked, 1-(l_mix/100), 0) # 두개의 이미지를 가중치에 따라서 다르게 보여줍니다.
            return m_weighted_img, mask
        else:
            return None, None
                    
    def run(self, frame, ref_img, l_mix):
        map_face_mesh = mp.solutions.face_mesh
        face_mesh = map_face_mesh.FaceMesh(min_detection_confidence =0.5, min_tracking_confidence=0.5)   
        mask = np.zeros_like(frame)
        results = face_mesh.process(frame)
        lip_mask = mask.copy()

        lip_result, lip_mask = self.draw_LIPS(frame, lip_mask, utils.WHITE, ref_img, results, l_mix)

        return lip_result, lip_mask