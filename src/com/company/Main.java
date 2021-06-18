package com.company;

import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.PNGFile;

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
//    IMultiLayerIMEController controller =
//        new MultiLayerIMEControllerImpl(new MultiLayerModelImpl(),
//            new InputStreamReader(System.in), System.out, new TextualIMEView());
//    controller.run(new MultiLayerModelImpl());
    IFileFormat format = new PNGFile();
    format.createDirectory("dirName", "res");
  }
}