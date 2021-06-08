package com.company;


import CS3500.ImageUtil;
import CS3500.Utils;
import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.image.ImageImpl;
import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;
import CS3500.model.operation.IFilter;
import CS3500.model.operation.IOperation;
import CS3500.model.operation.ImageBlur;
import CS3500.model.operation.MyFilter;
import CS3500.model.operation.Sharpening;
import CS3500.model.pixel.IPixel;
import CS3500.model.pixel.PixelImpl;

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
    IImage img = new ImageImpl(new MatrixImpl<>(new PixelImpl(1, 1, 1), 3, 3));
//    IImage img = ImageUtil.importPPM("src/Koala.ppm");
    IMatrix<IPixel> px = img.getPixelArray();
    String pixelsAsString = px.toString();

    ImageBlur imageBlur = new ImageBlur();
    IFilter myFilter = new MyFilter();
    System.out.println("Before Apply");
    System.out.println(Utils.paddedPrint(pixelsAsString));
//    imageBlur.applyFilterToAllChannels(img);
    myFilter.applyFilterToAllChannels(img);
    System.out.println("After Apply");
    System.out.println(Utils.paddedPrint(img.getPixelArray().toString()));

//    IMatrix<String> m = new MatrixImpl<>(new ArrayList<>(Arrays.asList(
//        new ArrayList<>(Arrays.asList("a","b")),
//        new ArrayList<>(Arrays.asList("c", "d"))
//    )));
//    System.out.println(m.toString());

    //ImageUtil.readPPM(filename);
  }
}