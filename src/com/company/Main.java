package com.company;


import CS3500.model.IStateTrackingIMEModel;
import CS3500.model.ProgramamticImages.BWNoise;
import CS3500.model.ProgramamticImages.Noise;
import CS3500.model.ProgramamticImages.PureNoise;
import CS3500.model.ProgramamticImages.RainbowNoise;
import CS3500.model.StateTrackingIMEModelImpl;
import CS3500.model.fileFormat.PPMFile;
import CS3500.model.image.ImageImpl;
import CS3500.model.matrix.MatrixImpl;
import CS3500.model.operation.ImageBlur;
import CS3500.model.operation.Sepia;
import CS3500.model.operation.Sharpening;
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
//    String filename;
//
//    if (args.length > 0) {
//      filename = args[0];
//    } else {
//      filename = "sample.ppm";
//    }
//    // Todo: Temporarily test here
//    IImage img = new ImageImpl(new MatrixImpl<>(new PixelImpl(1, 1, 1), 3, 3));
////    IImage img = ImageUtil.importPPM("src/Koala.ppm");
//    IMatrix<IPixel> px = img.getPixelArray();
//    String pixelsAsString = px.toString();
//
//    ImageBlur imageBlur = new ImageBlur();
//    MyFilter myFilter = new MyFilter();
//    System.out.println("Before Apply");
//    System.out.println(Utils.paddedPrint(pixelsAsString));
////    imageBlur.applyFilterToAllChannels(img);
//    IImage imgCopy = myFilter.applyFilterToAllChannels(img);
//    System.out.println("After Apply");
//    System.out.println(Utils.paddedPrint(imgCopy.getPixelArray().toString()));

//    IMatrix<String> m = new MatrixImpl<>(new ArrayList<>(Arrays.asList(
//        new ArrayList<>(Arrays.asList("a","b")),
//        new ArrayList<>(Arrays.asList("c", "d"))
//    )));
//    System.out.println(m.toString());

    //ImageUtil.readPPM(filename);

    // TODO: can abstract this to easily make checkerboard images
//    List<List<IPixel>> pxs = new ArrayList<>();
//
//    for (int i = 0; i < 200; i++) {
//      List<IPixel> thisRow = new ArrayList<>();
//      for (int j = 0; j < 200; j++) {
//        if ((i / 25 % 2) == (j / 25 % 2)) {
//          thisRow.add(new PixelImpl(0, 0, 0));
//        }
//        else {
//          thisRow.add(new PixelImpl(255, 255, 255));
//        }
//      }
//      pxs.add(thisRow);
//    }
//
//    IMatrix<IPixel> pxMatrix = new MatrixImpl<>(pxs);
//    IImage SAMPLE_IMAGE = new ImageImpl(pxMatrix);

    IStateTrackingIMEModel m = new StateTrackingIMEModelImpl(new ImageImpl(new MatrixImpl<>()));
//    m.importImage(new PPMFile(), "src/statue-of-liberty.ppm");
//    m.applyOperations(new Sepia(), new Sharpening(), new Sharpening(), new Sharpening(), new Sharpening());
//    m.exportImage(new PPMFile(), "new-statue-of-liberty");
//    m.importImage(new PPMFile(), "src/teletubbies-10-ft-tall.ppm");
    // m.exportImage(new PPMFile(), "exportedTeletubbies");
//    m.applyOperations(new ImageBlur(), new ImageBlur(), new ImageBlur(), new ImageBlur(),
//        new ImageBlur(), new ImageBlur(), new ImageBlur(), new ImageBlur());
//    m.applyOperations(new Greyscale(), new Sharpening(), new Sharpening(), new Sharpening());
//    m.applyOperations(new Sharpening());
//    m.applyOperations(new Sharpening());
//    m.applyOperations(new Sharpening());
//    m.applyOperations(new Sharpening());
//    m.applyOperations(new Sharpening());

//    m.importImage(new PPMFile(), "dachshunds.ppm");
//    m.applyOperations(new Sharpening(), new Sharpening(), new Sharpening());
//    m.exportImage(new PPMFile(), "edited-dachshunds");

//    m.exportImage(new PPMFile(), "blurredTeletubbies");

    //m.setProgrammaticImage(new RainbowNoise(), 699, 420, 500);

    //m.setProgrammaticImage(new PureNoise(), 500, 500, 1);
    //m.exportImage(new PPMFile(), "pureNoise");
    String abcd = "abcd.ppm";
    System.out.println(abcd.substring(abcd.lastIndexOf('.')));



  }
}