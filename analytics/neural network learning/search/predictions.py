import torch
import numpy as np
from torch.utils.data import DataLoader
import matplotlib.pyplot as plt

from search.dataset import Dataset
import search.augmentations
from search.utils import *


# load best saved checkpoint
best_model = torch.load('search/best_model.pth')


def run(path):
    # create test dataset
    test_dataset = Dataset(
        ''.join(path.split('\\')[:1])+'/',
        ''.join(path.split('\\')[:1])+'/',
        augmentation=search.augmentations.get_validation_augmentation(),
        preprocessing=search.augmentations.get_preprocessing(preprocessing_fn),
        classes=CLASSES,
    )

    test_dataloader = DataLoader(test_dataset)

    image, gt_mask = test_dataset[0]

    # gt_mask = gt_mask.squeeze()

    x_tensor = torch.from_numpy(image).to(DEVICE).unsqueeze(0)
    pr_mask = best_model.predict(x_tensor)
    pr_mask = (pr_mask.squeeze().cpu().numpy().round())
    plt.imsave(path[:-4]+'_mask.png', pr_mask)

    # visualize(
    #     # image=image_vis,
    #     ground_truth_mask=gt_mask,
    #     predicted_mask=pr_mask
    # )
