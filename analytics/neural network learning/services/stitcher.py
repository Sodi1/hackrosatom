import glob
import logging
import os
import sys

import cv2 as cv


modes = (cv.Stitcher_PANORAMA, cv.Stitcher_SCANS)


class Stitcher:
    def __init__(self) -> None:
        self._mode = modes[0]
        self._images = []
        self._image_path = []
        self._input_image_path = '/input/*.jpg'
        self._output_img_name = 'result.jpg'
        logging.basicConfig(level=logging.DEBUG, format='[%(levelname)s] %(asctime)s - %(message)s',
                            datefmt='%d.%m.%Y %H:%M:%S')
        logging.info('Stitcher successful created.')

    def set_img_path(self, path: str) -> None:
        """
        Set path to folder with input images.
        :param path: Path to folder
        :return:
        """
        self._input_image_path = path

    def set_mode(self, mode: int) -> None:
        """
        Set mode for stitching.

        0 - Mode for creating photo panoramas. Expects images under perspective
        transformation and projects resulting pano to sphere.

        1 - Mode for composing scans. Expects images under affine
        transformation does not compensate exposure by default.

        :param mode: Mode for stitching
        :return:
        """
        self._mode = mode

    def set_output_img_name(self, output_filename: str) -> None:
        """
        Set output filename

        By default: `result.jpg`

        :param output_filename: Output filename
        :return:
        """
        self._output_img_name = output_filename

    def _get_full_img_path(self) -> None:
        """
        Get full path into filesystem to input images

        :return:
        """
        self._image_path = glob.glob(os.getcwd()+self._input_image_path)

    def _get_images(self) -> None:
        """
        Get images and put into list

        :return:
        """
        self._get_full_img_path()

        for image in self._image_path:
            img = cv.imread(cv.samples.findFile(image))
            if img is None:
                logging.warning(f'Can\'t read image: "{image}"')
            self._images.append(img)

    def run(self) -> None:
        """
        Run stitch process

        :return:
        """
        self._get_images()

        stitcher = cv.Stitcher.create(self._mode)
        status, pano = stitcher.stitch(self._images)

        if status == cv.Stitcher_ERR_NEED_MORE_IMGS:
            logging.error('Need more images for stitching!')
            sys.exit(-1)
        elif status == cv.Stitcher_OK:
            logging.info(f'Stitching completed successfully. Result in "{self._output_img_name}"')
        else:
            logging.error(f'Can\'t stitch images, error code = {status}')
            sys.exit(-1)

        cv.imwrite(self._output_img_name, pano)
        # cv.destroyAllWindows()
