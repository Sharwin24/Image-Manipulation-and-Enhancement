package cs3500.model.fileformat;

/**
 * A class representing a JPEG image file format. Offers the capability to import/export an image of
 * this type.
 */
public class JPEGFile extends AFileFormat {

  /**
   * Constructs a JPEGFile using the super's constructor.
   */
  public JPEGFile() {
    super();
  }

  @Override
  protected String getFileExtension() {
    return ".jpg";
  }
}