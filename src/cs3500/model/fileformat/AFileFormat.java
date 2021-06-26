package cs3500.model.fileformat;

import cs3500.model.channel.EChannelType;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Abstract class to represent functions that all {@link IFileFormat} classes will utilize, such as
 * importing and exporting an image, as well as several helper methods that aid in the process of
 * reading/writing image data.
 */
public abstract class AFileFormat implements IFileFormat {

  private final String fileExtension;



  /**
   * Constructs the AFileFormat with the subclass' file extension.
   */
  public AFileFormat() {
    this.fileExtension = this.getFileExtension();
  }

  @Override
  public IImage importImage(String relativePath) throws IllegalArgumentException {
    if (relativePath == null) {
      throw new IllegalArgumentException("Null path provided");
    }
    // Get the file
    File file = new File(relativePath);
    // Read the image
    BufferedImage image;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read Image");
    }
    int height = 0;
    int width = 0;
    try {
      height = image.getHeight();
      width = image.getWidth();
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Image is empty");
    }
    // Initialize empty matrix to fill with imported pixels
    IMatrix<IPixel> pixelMatrix = new MatrixImpl<>(new PixelImpl(0, 0, 0), height, width);
    // Create an array of the pixels in the image as memory address to access all ARGB values.
    final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int[] rgb = getRGBAtXY(image, pixels, c, r); // Get ARGB values
        // Update with retrieved pixel
        pixelMatrix.updateEntry(new PixelImpl(rgb[0], rgb[1], rgb[2]), r, c);
      }
    }
    return new ImageImpl(pixelMatrix);
  }

  @Override
  public File exportImage(String relativePath, IImage image) throws IllegalArgumentException {
    if (relativePath == null || image == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    if (relativePath.equals("")) {
      throw new IllegalArgumentException("Cannot write to empty path");
    }
    String fileName = relativePath + this.fileExtension;
    int width = image.getPixelArray().getWidth();
    int height = image.getPixelArray().getHeight();
    BufferedImage outputImage;
    // Create empty image to fill with pixels
    outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        // Get RGB values
        int red = image.getPixelArray().getElement(r, c).getIntensity(EChannelType.RED);
        int green = image.getPixelArray().getElement(r, c).getIntensity(EChannelType.GREEN);
        int blue = image.getPixelArray().getElement(r, c).getIntensity(EChannelType.BLUE);
        // Creating rgb by shifting to next register to add next value
        // Representation of RGB integer with blocks of 8 bits -> RRRRRRRR | GGGGGGGG | BBBBBBBB
        // int rgb = (red << 16) | (green << 8) | blue; // Writing to entire register at once
        int rgb = red; // first address is red
        rgb = (rgb << 8) + green; // shift over 8 bits and add green
        rgb = (rgb << 8) + blue; // shift over 8 bits and add blue
        outputImage.setRGB(c, r, rgb); // Update BufferedImage with RGB value
      }
    }
    File outputFile = new File(fileName); // Create output file
    try {
      ImageIO.write(outputImage, this.fileExtension.substring(1), outputFile);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to write image to file");
    }
    return outputFile;
  }


  /**
   * Gets the RGB values from a point (x,y) in an image. Offers support for the alpha channel and
   * dynamically returns an integer array of size 3 or 4. The first three are: RED, GREEN, BLUE, and
   * the fourth(if applicable) is the alpha channel.
   *
   * @param image  the image to search in.
   * @param pixels an array of the pixels in the image, provided to avoid loop inefficiency.
   * @param x      the X coordinate of the pixel.
   * @param y      the Y coordinate of the pixel.
   * @return an integer array of size 3 or 4, with values for RED,GREEN,BLUE, and ALPHA.
   */
  protected int[] getRGBAtXY(BufferedImage image, byte[] pixels, int x, int y) {
    int[] rgb;
    boolean hasAlphaChannel = image.getAlphaRaster() != null;
    int pixelLength = 3;
    if (hasAlphaChannel) {
      pixelLength = 4;
      rgb = new int[4]; // include a spot for the alpha channel
    } else {
      rgb = new int[3];
    }
    int pos = (y * pixelLength * image.getWidth()) + (x * pixelLength); // find position of (x,y)
    if (hasAlphaChannel) {
      rgb[3] = pixels[pos++] & 0xFF; // Alpha channel
    }
    rgb[2] = pixels[pos++] & 0xFF; // Blue
    rgb[1] = pixels[pos++] & 0xFF; // Green
    rgb[0] = pixels[pos++] & 0xFF; // Red
    return rgb; // We & the value with 0 to enforce the [0,255] range
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
  protected File dumpAppendable(StringBuilder sb, String fileName)
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

  @Override
  public void createDirectory(String relativePath)
      throws IllegalArgumentException {
    // Create an empty directory with the given name at the given relative path.
    File directory = new File(relativePath);
    if (!directory.exists()) {
      directory.mkdirs();
    }
  }



}