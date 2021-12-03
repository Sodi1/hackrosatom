from glob import glob
import os

from multiprocessing import Process

import patching


def chunks(lst, n):
    for i in range(0, len(lst), n):
        yield lst[i:i + n]

if __name__ == '__main__':
    chunk_size = 10
    data_folder = 'data\\'
    coord_folder = 'coord\\'
    patch_folders = glob(os.path.join(data_folder, "*", ""))
    chunks_folder = list(chunks(patch_folders, chunk_size))
    for chunk in chunks_folder:
        p = Process(target=patching.main, args=(
            data_folder, coord_folder, chunk))
        p.start()
        p.join()
