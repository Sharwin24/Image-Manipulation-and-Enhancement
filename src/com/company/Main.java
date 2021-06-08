package com.company;


import CS3500.ImageUtil;
import CS3500.Utils;
import CS3500.model.image.IImage;
import CS3500.model.matrix.IMatrix;
import CS3500.model.operation.Sharpening;
import CS3500.model.pixel.IPixel;

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

//    IImage img = ImageUtil.importPPM("src/Koala.ppm");
//    IMatrix<IPixel> px = img.getPixelArray();
//    String pixelsAsString = px.toString();
//    System.out.println(Utils.paddedPrint(pixelsAsString));
    // Todo: Temporarily test here
//    IMatrix<String> m = new MatrixImpl<>(new ArrayList<>(Arrays.asList(
//        new ArrayList<>(Arrays.asList("a","b")),
//        new ArrayList<>(Arrays.asList("c", "d"))
//    )));
//    System.out.println(m.toString());

    //ImageUtil.readPPM(filename);
  }
}