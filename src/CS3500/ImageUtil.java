package CS3500;

import CS3500.model.IIMEModel;
import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.image.ImageImpl;
import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;
import CS3500.model.operation.Greyscale;
import CS3500.model.operation.ImageBlur;
import CS3500.model.pixel.IPixel;
import CS3500.model.pixel.PixelImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static IImage importPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      //System.out.println("File " + filename + " not found!");
      //return;
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 256): " + maxValue);

    List<List<IPixel>> pixels = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      List<IPixel> thisRow = new ArrayList<>();
      for (int j = 0; j < 6; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        //System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
        IPixel thisPixel = new PixelImpl(r, g, b);
        thisRow.add(thisPixel);
      }
      pixels.add(thisRow);
    }

    IImage imageAsMatrixOfPixels = new ImageImpl(new MatrixImpl<>(pixels));
    return imageAsMatrixOfPixels;
  }

  /**
   * Todo: Javadocs
   */
  public static String exportPPM(String fileName, IImage im) throws IllegalArgumentException {
    Utils.checkNotNull(fileName, "cannot export to null file name");
    Utils.checkNotNull(im, "cannot export a null image");

    StringBuilder sb = new StringBuilder();
    sb.append(Utils.println("P3"));
    sb.append(Utils.println("# " + fileName));

    for (int row = 0; row < im.getPixelArray().getHeight(); row++) {
      for (int col = 0; col < im.getPixelArray().getWidth(); col++) {
        sb.append(im.getPixelArray().getElement(row, col).getIntensity(EChannelType.RED))
            .append(" ")
            .append(im.getPixelArray().getElement(row, col).getIntensity(EChannelType.GREEN))
            .append(" ")
            .append(im.getPixelArray().getElement(row, col).getIntensity(EChannelType.BLUE))
            .append("\t");
      }
      sb.append("\n");
    }

    return sb.toString();

  }
}