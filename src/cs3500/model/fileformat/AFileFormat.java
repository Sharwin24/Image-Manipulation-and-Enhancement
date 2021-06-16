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
import javax.imageio.ImageIO;

/**
 *
 */
public abstract class AFileFormat implements IFileFormat<IImage> {

  private String fileExtension;

  /**
   *
   */
  public AFileFormat() {
    this.fileExtension = this.getFileExtension();
  }

  /**
   * @return
   */
  protected abstract String getFileExtension();

  @Override
  public IImage importImage(String relativePath) throws IllegalArgumentException {
    if (relativePath == null) {
      throw new IllegalArgumentException("Null path provided");
    }
    // Get the file
    File file;
    try {
      file = new File(relativePath);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to read file");
    }
    // Read the image
    BufferedImage image;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read Image");
    }
    int height = image.getHeight();
    int width = image.getWidth();
    IMatrix<IPixel> pixelMatrix = new MatrixImpl<>(new PixelImpl(0, 0, 0), height, width);
    final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int[] rgb = getRGBAtXY(image, pixels, c, r);
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
    String fileName = "res/" + relativePath + this.fileExtension;
    int width = image.getPixelArray().getWidth();
    int height = image.getPixelArray().getHeight();
    BufferedImage outputImage;
    outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int red = image.getPixelArray().getElement(r, c).getIntensity(EChannelType.RED);
        int green = image.getPixelArray().getElement(r, c).getIntensity(EChannelType.GREEN);
        int blue = image.getPixelArray().getElement(r, c).getIntensity(EChannelType.BLUE);
        int rgb = red; // first address of red
        rgb = (rgb << 8) + green; // shift over 8 bits and add green
        rgb = (rgb << 8) + blue; // shift over 8 bits and add blue
        outputImage.setRGB(c, r, rgb);
      }
    }
    File outputFile = new File(fileName);
    try {
      ImageIO.write(outputImage, this.fileExtension, outputFile);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to write image to file");
    }
    return outputFile;
  }


  /**
   * Todo
   *
   * @param image
   * @param pixels
   * @param x
   * @param y
   * @return
   */
  protected int[] getRGBAtXY(BufferedImage image, byte[] pixels, int x, int y) {
    int[] rgb;
    boolean hasAlphaChannel = image.getAlphaRaster() != null;
    int pixelLength = 3;
    if (hasAlphaChannel) {
      pixelLength = 4;
      rgb = new int[4];
    } else {
      rgb = new int[3];
    }
    int pos = (y * pixelLength * image.getWidth()) + (x * pixelLength);
    if (hasAlphaChannel) {
      rgb[3] = pixels[pos++] & 0xFF; // Alpha
    }
    rgb[2] = pixels[pos++] & 0xFF; // Blue
    rgb[1] = pixels[pos++] & 0xFF; // Green
    rgb[0] = pixels[pos++] & 0xFF; // Red
    return rgb;
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
}