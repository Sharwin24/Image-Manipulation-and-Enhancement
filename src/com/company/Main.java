package com.company;


import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.fileformat.PPMFile;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.model.programmaticimages.Checkerboard;
import cs3500.model.programmaticimages.PureNoise;

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

//    model.importImage(new PPMFile(), "res/elmo.ppm");
//
//    model.applyOperations(new Sharpening());
//    model.exportImage(new PPMFile(), "elmo-Sharpened");
//
//    model.undo();
//    model.applyOperations(new ImageBlur());
//    model.exportImage(new PPMFile(), "elmo-Blurred");
//
//    model.undo();
//    model.applyOperations(new Sepia());
//    model.exportImage(new PPMFile(), "elmo-Sepia");
//
//    model.undo();
//    model.applyOperations(new Greyscale());
//    model.exportImage(new PPMFile(), "elmo-GreyScale");
//
//
//    model.importImage(new PPMFile(), "res/owl-original.ppm");
//    model.applyOperations(new Sharpening());
//    model.exportImage(new PPMFile(), "owl-sharpened");
//
//    model.undo();
//    model.applyOperations(new ImageBlur());
//    model.exportImage(new PPMFile(), "owl-blurred");
//
//    model.undo();
//    model.applyOperations(new Sepia());
//    model.exportImage(new PPMFile(), "owl-sepia");
//
//    model.undo();
//    model.applyOperations(new Greyscale());
//    model.exportImage(new PPMFile(), "owl-greyscale");

    model.setProgrammaticImage(new PureNoise(), 100, 100, 1);
    model.exportImage(new PPMFile(), "Programmatic-Image-Pure-Noise");

    model.setProgrammaticImage(new Checkerboard(), 100, 100, 12);
    model.exportImage(new PPMFile(), "Programmatic-Image-CheckerBoard");








  }
}