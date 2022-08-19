from fastapi import FastAPI
from pydantic import BaseModel
from test import run
from options.test_options import TestOptions
import uvicorn
import cv2
import base64
import csv
import os
from fastapi.middleware.cors import CORSMiddleware
from PIL import Image

origins = ["*"]

app = FastAPI()
opt = TestOptions().parse()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
class Item(BaseModel):
    input: int
    pose: int


@app.get("/")
def tt():
    return "hello"

@app.post("/gan")
def test(item: Item):
    inputs = ["","input1.jpg","input2.jpg","input3.jpg","input4.png","input5.png","input6.png"]
    poses = ["","pose1.jpg","pose2.jpg","pose3.jpg","pose4.jpg","pose5.jpg","pose6.jpg","pose7.jpg","pose8.jpg","pose9.jpg",
    "pose10.jpg","pose11.jpg","pose12.jpg","pose13.png","pose14.png","pose15.png","pose16.jpg","pose17.jpg","pose18.jpg","pose19.jpg"]

    f = open(os.path.join('fashion_data','fasion-resize-pairs-test.csv'), 'w', encoding='utf-8')
    wr = csv.writer(f)
    wr.writerow(["from", "to"])
    # wr.writerow([poses[item.pose],inputs[item.input]])
    wr.writerow([poses[item.input],poses[item.pose]])
    f.close()
    img = run(opt)
    img = Image.fromarray(img)
    img.save('result.png',"png")
    # retval, buffer = cv2.imencode('.jpg', img)
    # jpg_as_text = base64.b64encode(buffer)
    # print(jpg_as_text.decode('utf-8'))
    # data = {"img":jpg_as_text.decode('utf-8')}
    with open("result.png", "rb") as imageFile:
        str = base64.b64encode(imageFile.read())
        data = {"img":str}
    # data=json.dumps(data)
    return data


if __name__ == "__main__":
    uvicorn.run("main:app", host="127.0.0.1", port=8083)