package cs3500.model.fileformat;

import cs3500.Utils;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A PPM file format that stores images with respect to their width, height,
 * and each pixel's RGB values, out of some maximum value, assumed here to be 255.
 * Allows the client to import and export PPM files using objects of this type as function objects.
 */
public class PPMFile implements IFileFormat<IImage> {

  @Override
  public IImage importImage(String relativePath)
      throws IllegalArgumentException {
    Utils.checkNotNull(relativePath, "cannot import an image with a null file name");
    if (relativePath.equals("")) {
      throw new IllegalArgumentException("cannot determine empty path name");
    }
    try {
      if (!(relativePath.substring(relativePath.lastIndexOf('.')).equals(".ppm"))) {
        throw new IllegalArgumentException("Not a valid ppm file. When importing a ppm, please "
            + "make sure that the file ends in \".ppm\"");
      }
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("please specify the file extension, i.e. \".ppm\"");
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(relativePath));
    } catch (FileNotFoundException e) {
      //System.out.println("File " + filename + " not found!");
      //return;
      throw new IllegalArgumentException("File " + relativePath + " not found!");
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
    try {
      token = sc.next();
      if (!token.equals("P3")) {
        System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      }
      try {
        int width = sc.nextInt();
        System.out.println("Width of image: " + width);
        int height = sc.nextInt();
        System.out.println("Height of image: " + height);
        int maxValue = sc.nextInt();
        System.out.println("Maximum value of a color in this file (usually 256): " + maxValue);
        List<List<IPixel>> pixels = new ArrayList<>();
        for (int i = 0; i < height; i++) {
          List<IPixel> thisRow = new ArrayList<>();
          for (int j = 0; j < width; j++) {
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
      } catch (InputMismatchException e) {
        throw new IllegalArgumentException("could not read from PPM file. Check to see if it is "
            + "corrupted or not formatted correctly");
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("PPM file was empty. Unable to import");
    }
  }

  @Override
  public File exportImage(String relativePath, IImage image)
      throws IllegalArgumentException {
    Utils.checkNotNull(relativePath, "cannot export image to a null file name");
    Utils.checkNotNull(image, "cannot export a null image");
    if (relativePath.equals("")) {
      throw new IllegalArgumentException("cannot write to empty path name");
    }
    String fileNamePPM = "res/" + relativePath + ".ppm";

    StringBuilder fileContents = new StringBuilder();
    fileContents.append(Utils.println("P3"));
    fileContents.append(Utils.println(image.getPixelArray().getWidth() + " "
        + image.getPixelArray().getHeight()));
    fileContents.append(Utils.println("255"));

    for (int pxRow = 0; pxRow < image.getPixelArray().getHeight(); pxRow++) {
      for (int pxCol = 0; pxCol < image.getPixelArray().getWidth(); pxCol++) {
        fileContents.append(" " + image.getPixelArray().getElement(pxRow, pxCol).toString() + "\t");
      }
      fileContents.append("\n");
    }

    return this.dumpAppendable(fileContents, fileNamePPM);
  }

  /**
   * Dumps the contents of the given {@link StringBuilder} into a file with the specified {@code
   * fileName} and closes that file to save it to the specified path relative to the working
   * (project) directory. Overrwrites the file at the given path
   *
   * @param sb       the {@link StringBuilder} to store the contents of in a file.
   * @param fileName the relative path of the file to which {@code sb} should be stored.
   * @return the file that was created.
   * @throws IllegalArgumentException If <ul>
   *                                  <li>the path is invalid</li>
   *                                  <li>the path is {@code null}</li>
   *                                  <li>the path is the empty String</li>
   *                                  </ul>
   */
  private File dumpAppendable(StringBuilder sb, String fileName)
      throws IllegalArgumentException {
    File f = new File(fileName);
    BufferedWriter buf = null;
    try {
      buf = new BufferedWriter(new FileWriter(f));
      buf.append(sb);
      f.createNewFile();
    } catch (IOException e) {
      throw new IllegalArgumentException("couldn't write to file");
    } finally {
      if (buf != null) {
        try {
          buf.close();

        } catch (IOException e) {
          throw new IllegalArgumentException("could not close writer");
        }
      }
    }
    return f;
  }


}