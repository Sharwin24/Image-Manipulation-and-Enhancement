package cs3500.model.fileformat;

import cs3500.model.channel.EChannelType;
import cs3500.model.image.IImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;

/**
 * Todo: JavaDocs
 */
public class PNGFile extends AFileFormat {

  /**
   *
   */
  public PNGFile() {
    super();
  }

  @Override
  protected String getFileExtension() {
    return ".png";
  }
}