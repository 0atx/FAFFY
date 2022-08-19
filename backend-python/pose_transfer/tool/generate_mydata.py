import os
import shutil
from PIL import Image
import cv2

IMG_EXTENSIONS = [
'.jpg', '.JPG', '.jpeg', '.JPEG',
'.png', '.PNG', '.ppm', '.PPM', '.bmp', '.BMP',
]
path = ".\\fashion\\99999.jpg"
# img = Image.open(path)
# imgcrop = img.crop((40, 0, 216, 256))

# imgcrop.save(os.path.join(".\\fashion_data\\test", "99999.jpg"))




path = ".\\fashion\\99999.jpg"
img = cv2.imread(path)

h,w,c = img.shape

h2=128
w2=64

if h2>h or w2>w :
    exit()
mid_x, mid_y = w//2,h//2
offset_x, offset_y = w2//2, h2//2
imgcrop=img[mid_y - offset_y:mid_y+offset_y,mid_x - offset_x:mid_x+offset_x]
cv2.imwrite(os.path.join(".\\market_data\\test", "99999.jpg"),imgcrop)