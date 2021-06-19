package com.company;

import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.PNGFile;
import java.io.InputStreamReader;

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

//    IFileFormat format = new PNGFile();
//    IImage image = (IImage) format.importImage("src/facebookLogo.png");
//    for (int i = 0; i < image.getPixelArray().getHeight(); i++) {
//      for (int j = 0; j < image.getPixelArray().getWidth(); j++) {
//        System.out.print("[" + image.getPixelArray().getElement(i, j) + "] ");
//      }
//      System.out.println();
//    }
//    format.exportImage("exportedFacebookPNG", image);
    IMultiLayerIMEController controller =
        MultiLayerIMEControllerImpl.controllerBuilder().buildController();
    controller.run(new MultiLayerModelImpl());
//    IFileFormat format = new PNGFile();
//    format.createDirectory(");
  }
}