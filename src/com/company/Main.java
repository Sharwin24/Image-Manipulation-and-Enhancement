package com.company;


import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.fileformat.PPMFile;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.PixelImpl;
import cs3500.model.programmaticimages.Noise;

/**
 * Main class.
 */
public class Main {

  /**
   * Main method.
   *
   * @param args optional args.
   */
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "sample.ppm";
    }

    IStateTrackingIMEModel<IImage> model =
        new StateTrackingIMEModelImpl(new ImageImpl(new MatrixImpl<>()));

    model.setProgrammaticImage(new Noise(PixelImpl.GREEN,
        PixelImpl.BLUE, PixelImpl.CYAN, PixelImpl.RED, PixelImpl.VIOLET, PixelImpl.YELLOW),
        1500, 1500, 1);
    model.applyOperations();
    model.exportImage(new PPMFile(), "Pure-Noise");


  }
}