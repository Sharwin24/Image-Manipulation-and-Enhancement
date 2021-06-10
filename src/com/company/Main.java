package com.company;


import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.image.IImage;
import cs3500.model.operation.Greyscale;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.fileFormat.PPMFile;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;

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
    model.importImage(new PPMFile(), "res/Koala.ppm");

    model.applyOperations(new Greyscale());
    model.exportImage(new PPMFile(), "greyscaleKoala");

    model.undo();


    model.redo();
    model.undo();
    model.redo();
    model.exportImage(new PPMFile(), "GSkoala");


  }
}