package cs3500.model.fileformat;

import cs3500.model.channel.EChannelType;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

/**
 * Todo: JavaDocs
 */
public class JPEGFile extends AFileFormat {

  @Override
  public File exportImage(String relativePath, IImage image) throws IllegalArgumentException {
    if (relativePath == null || image == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    if (relativePath.equals("")) {
      throw new IllegalArgumentException("Cannot write to empty path");
    }
    String fileNameJPEG = "res/" + relativePath + ".jpg";
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
    File outputFile = new File(fileNameJPEG);
    try {
      ImageIO.write(outputImage, "jpg", outputFile);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to write image to file");
    }
    return outputFile;
  }
}