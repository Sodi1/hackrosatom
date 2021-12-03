import torch
from torch.utils.data import DataLoader

from dataset import Dataset
import augmentations
from utils import *

# load best saved checkpoint
best_model = torch.load('best_model.pth')

# create test dataset
test_dataset = Dataset(
    x_test_dir,
    y_test_dir,
    augmentation=augmentations.get_validation_augmentation(),
    preprocessing=augmentations.get_preprocessing(preprocessing_fn),
    classes=CLASSES,
)

test_dataloader = DataLoader(test_dataset)

# evaluate model on test set
test_epoch = smp.utils.train.ValidEpoch(
    model=best_model,
    loss=smp.utils.losses.DiceLoss(),
    metrics=[
    smp.utils.metrics.IoUMetric(threshold=0.5),
    ],
    device=DEVICE,
)

def run_test():
    logs = test_epoch.run(test_dataloader)
    print(logs)
