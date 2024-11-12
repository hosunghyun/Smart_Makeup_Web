import cv2
import numpy as np

def nothing(x):
    pass

src = cv2.imread("pre_ref_img/lip/106.jpg")
ref = cv2.imread("pre_ref_img/lip/106-1.jpg")
scr = cv2.cvtColor(src, cv2.COLOR_BGR2HSV)
# scr = cv2.cvtColor(src, cv2.COLOR_BGR2RGB)
# scr = cv2.cvtColor(src, cv2.COLOR_BGR2YCrCb)
# ref1= ~ref
cv2.namedWindow("Trackbar Windows")


cv2.createTrackbar("h_start", "Trackbar Windows", 0, 255, nothing)
cv2.createTrackbar("s_start", "Trackbar Windows", 0, 255, nothing)
cv2.createTrackbar("v_start", "Trackbar Windows", 0, 255, nothing)
cv2.createTrackbar("h_end", "Trackbar Windows", 0, 255, nothing)
cv2.createTrackbar("s_end", "Trackbar Windows", 0, 255, nothing)
cv2.createTrackbar("v_end", "Trackbar Windows", 0, 255, nothing)

cv2.setTrackbarPos("h_start", "Trackbar Windows", 0)
cv2.setTrackbarPos("s_start", "Trackbar Windows", 0)
cv2.setTrackbarPos("v_start", "Trackbar Windows", 0)
cv2.setTrackbarPos("h_end", "Trackbar Windows", 255)
cv2.setTrackbarPos("s_end", "Trackbar Windows", 255)
cv2.setTrackbarPos("v_end", "Trackbar Windows", 255)

while cv2.waitKey(1) != ord('q'):

    h_start = cv2.getTrackbarPos("h_start", "Trackbar Windows")
    s_start = cv2.getTrackbarPos("s_start", "Trackbar Windows")
    v_start = cv2.getTrackbarPos("v_start", "Trackbar Windows")
    h_end = cv2.getTrackbarPos("h_end", "Trackbar Windows")
    s_end = cv2.getTrackbarPos("s_end", "Trackbar Windows")
    v_end = cv2.getTrackbarPos("v_end", "Trackbar Windows")

    mask = cv2.inRange(src, (0,0,183), (78,79,255))
    mask = ~mask
    # output = cv2.bitwise_or(src,src, mask = mask)
    output = cv2.bitwise_or(src, ref)
    # cv2.imwrite('pre_ref_img/lip/106-2.jpg', mask)
    cv2.imshow("Trackbar Windows", mask)
    cv2.imshow("Windows", output)

cv2.destroyAllWindows()
