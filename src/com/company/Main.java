package com.company;


import CS3500.ImageUtil;
import CS3500.Utils;
import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.image.ImageImpl;
import CS3500.model.matrix.IMatrix;
import CS3500.model.operation.ImageBlur;
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
    // Todo: Temporarily test here

    IImage img = ImageUtil.importPPM("src/Koala.ppm");
    IMatrix<IPixel> px = img.getPixelArray();
    String pixelsAsString = px.toString();

    ImageBlur imageBlur = new ImageBlur();
    System.out.println("Before Apply");
    System.out.println(Utils.paddedPrint(pixelsAsString));
    imageBlur.applyToChannel(img, EChannelType.RED);
    System.out.println("After Apply");

//    IMatrix<String> m = new MatrixImpl<>(new ArrayList<>(Arrays.asList(
//        new ArrayList<>(Arrays.asList("a","b")),
//        new ArrayList<>(Arrays.asList("c", "d"))
//    )));
//    System.out.println(m.toString());

    //ImageUtil.readPPM(filename);
  }
}