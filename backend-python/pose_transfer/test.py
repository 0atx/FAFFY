import time
import os
from options.test_options import TestOptions
from data.data_loader import CreateDataLoader
from models.models import create_model
from util.visualizer import Visualizer
from util import html
import time

# opt = TestOptions().parse()
# opt.nThreads = 1   # test code only supports nThreads = 1
# opt.batchSize = 1  # test code only supports batchSize = 1
# opt.serial_batches = True  # no shuffle
# opt.no_flip = True  # no flip

def run(opt):
    # opt = TestOptions().parse()
    opt.nThreads = 1   # test code only supports nThreads = 1
    opt.batchSize = 1  # test code only supports batchSize = 1
    opt.serial_batches = True  # no shuffle
    opt.no_flip = True  # no flip
    opt.dataroot= "fashion_data"
    opt.name= "fashion_PATN"
    opt.model= "PATN"
    opt.phase= "test"
    opt.dataset_mode= "keypoint"
    opt.norm= "instance"
    opt.resize_or_crop= "no"
    opt.BP_input_nc= 18
    opt.which_model_netG= "PATN"
    opt.checkpoints_dir= "./checkpoints"
    opt.pairLst= os.path.join('fashion_data','fasion-resize-pairs-test.csv')
    opt.which_epoch= "latest"
    opt.results_dir= "./results"
    opt.display_id= 0
    opt.fineSize = 256




    data_loader = CreateDataLoader(opt)
    dataset = data_loader.load_data()
    model = create_model(opt)
    visualizer = Visualizer(opt)
    # create website
    web_dir = os.path.join(opt.results_dir, opt.name, '%s_%s' % (opt.phase, opt.which_epoch))

    # webpage = html.HTML(web_dir, 'Experiment = %s, Phase = %s, Epoch = %s' % (opt.name, opt.phase, opt.which_epoch))

    print(opt.how_many)
    print(len(dataset))

    model = model.eval()
    print(model.training)

    opt.how_many = 999999
    # test
    for i, data in enumerate(dataset):
        print(' process %d/%d img ..'%(i,opt.how_many))
        if i >= opt.how_many:
            break
        model.set_input(data)
        startTime = time.time()
        model.test()
        endTime = time.time()
        print(endTime-startTime)
        visuals = model.get_current_visuals()
        # print(type(visuals))
        img_path = model.get_image_paths()
        img_path = [img_path]
        # print(img_path)
        # visualizer.save_images(webpage, visuals, img_path)
        return visuals["vis"]

    # webpage.save()
if __name__ == '__main__':
    run()



