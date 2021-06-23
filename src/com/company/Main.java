package com.company;

import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IIMEModel;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.Downscale;
import cs3500.model.pixel.PixelImpl;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    // add calls to methods to manipulate images here...

//    final String scriptToRun =
//        //  scriptToStringBuilder("scripts/ExampleScript1.txt");
//        "new \n"
//            + "new \n"
//            + "current 2 \n"
//            + "programmatic bwnoise 474 270 20\n"
//            + "current 1\n"
//            + "import PPM res/rover.ppm\n"
//            + "delete 0\n"
//            + "apply sepia sharpen greyscale blur sharpen\n"
//            + "export JPEG layers\n"
//            + "undo\n"
//            + "redo\n"
//            + "redo\n"
//            + "swap 0 1\n"
//            + "delete 0\n"
//            + "delete 0\n"
//            + "import JPEG layers res/exampleLayers\n"
//            + "delete 0 \n"
//            + "delete 0\n"
//            + "programmatic rainbownoise 100 100 10\n"
//            + "new\n"
//            + "current 1 \n"
//            + "programmatic purenoise 100 100 10\n"
//            + "new\n"
//            + "current 2\n"
//            + "programmatic checkerboard 100 100 10\n"
//            + "save\n"
//            + "visibility 1\n"
//            + "current 0\n"
//            + "export PNG res/finalImage-Rover\n";
//    IMultiLayerIMEController controller =
//        MultiLayerIMEControllerImpl.controllerBuilder()
//            .readable(new StringReader(scriptToRun)).buildController();
//    controller.run();

    // A NOTE TO THE TA: to run this interactively, comment out lines 54-58 and uncomment
    // the following lines
    // IMultiLayerIMEController controllerInteractive =
    // MultiLayerIMEControllerImpl.controllerBuilder().buildController();

    IMultiLayerModel model = new MultiLayerModelImpl();
    IImage image = new ImageImpl(new MatrixImpl<>(new PixelImpl(100, 0, 0), 10, 10));
    Downscale downscale = new Downscale(model, 5, 5);
    model.load(image);
    downscale.apply();
  }

  /**
   * Utility method to read a script text file for the controller directly into a StringReader that
   * will be passed to the main method.
   *
   * @param pathName the path where the script is located.
   * @return A StringReader with the contents of the script file
   * @throws IllegalArgumentException if the path name could not be read from.
   */
  private static StringReader scriptToStringBuilder(String pathName)
      throws IllegalArgumentException {
    try {
      byte[] outputFromFile = Files.readAllBytes(Paths.get(pathName));
      return new StringReader(new String(outputFromFile));
    } catch (IOException e) {
      throw new IllegalArgumentException("path \"" + pathName + "\" not able to be read from");
    }
  }
}