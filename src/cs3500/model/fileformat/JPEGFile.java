package cs3500.model.fileformat;

/**
 * A class representing a JPEG image file format. Offers the capability to import/export an image of
 * this type. This class is a function object that can <code>export: IImage, String -> File</code>
 * and <code>Import: String -> IImage</code>,
 * and has a unique file extension ".jpg"
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