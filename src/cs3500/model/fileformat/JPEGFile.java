package cs3500.model.fileformat;

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
import javax.imageio.ImageIO;

/**
 * Todo: JavaDocs
 */
public class JPEGFile extends AFileFormat {
  
  @Override
  public File exportImage(String relativePath, IImage image) throws IllegalArgumentException {
    return null;
  }
}