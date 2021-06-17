package cs3500.model.fileformat;

/**
 * A class to represent a PNG image. Offers the capability to import/export an image of * this
 * type.
 */
public class PNGFile extends AFileFormat {

  /**
   * Constructs a PNGFile using the super's constructor.
   */
  public PNGFile() {
    super();
  }

  @Override
  protected String getFileExtension() {
    return ".png";
  }
}