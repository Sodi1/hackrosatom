import matplotlib.pyplot as plt
import numpy as np

from eolearn.core import LoadTask

import gc
import json
import os
import sys
from glob import glob

from memory_profiler import profile


def plot_data(eopatch, folder, i):
    fig = plt.figure(figsize=(1, 1), clear=True)
    ax = plt.Axes(fig, [0., 0., 1., 1.])
    ax.set_axis_off()
    fig.add_axes(ax)
    # ax.imshow(np.clip(eopatch.data['L2A'][i][..., 12].squeeze(), 0, 1), vmin=0, vmax=1)
    ax.imshow(np.clip(eopatch.data['L2A'][i][..., [
        3, 2, 1]] * 2.5, 0, 1), vmin=0, vmax=1)
    try:
        fig.savefig(f'D\\{folder}\\{i+1}.png')
    except FileNotFoundError:
        os.makedirs(f'D\\{folder}')
        fig.savefig(f'D\\{folder}\\{i+1}.png')
    plt.close(fig)
    # plt.close("all")
    plt.clf()
    fig.clear()
    del fig
    gc.collect()
# def plot_data(eopatch, folder, i):
#     fig = plt.figure(figsize=(1, 1), clear=True)
#     ax = plt.Axes(fig, [0., 0., 1., 1.])
#     ax.set_axis_off()
#     fig.add_axes(ax)
#     ax.imshow(np.clip(eopatch.mask['SNW']
#               [i].squeeze(), 0, 1), vmin=0, vmax=1)
#     # ax.imshow(np.clip(eopatch.data['L2A'][i][..., [
#     #     3, 2, 1]] * 2.5, 0, 1), vmin=0, vmax=1)
#     try:
#         fig.savefig(f'mask\\SNW\\{folder}\\{i+1}.png')
#     except FileNotFoundError:
#         os.makedirs(f'mask\\SNW\\{folder}')
#         fig.savefig(f'mask\\SNW\\{folder}\\{i+1}.png')
#     plt.close(fig)
#     # plt.close("all")
#     plt.clf()
#     fig.clear()
#     del fig
#     gc.collect()

def main(data_folder, coord_folder, patch_folders):
    load = LoadTask('')
    print(patch_folders)

    # import sys
    # sys.exit(0)

    for folder in patch_folders:
        eopatch = load.execute(eopatch_folder=folder.replace('\\', '/'))

        times = [x.strftime('%d.%m.%Y, %H:%M:%S') for x in eopatch.timestamp]
        # print(*times, sep='\n')
        # import sys
        # sys.exit(0)

        with open(f'{coord_folder}\\coord.txt', 'a+', encoding='utf-8') as f:
            f.write(f'"{folder}",{str(eopatch.bbox)}\n')

        dict_of_dates = {}
        with open(f'{coord_folder}\\timestamps.json', 'r', encoding='utf-8') as f:
            dict_of_dates = json.loads(f.read())
        dict_of_dates[folder.replace('\\', '').replace('data', '')] = times
        with open(f'{coord_folder}\\timestamps.json', 'w', encoding='utf-8') as f:
            f.write(json.dumps(dict_of_dates, indent=4))

        print(eopatch)
        folder = folder.split("\\")[1]

        # for i in range(len(eopatch.data['L2A'])):
        #     plot_data(eopatch, folder, i)

        del eopatch
        gc.collect()

    del load
