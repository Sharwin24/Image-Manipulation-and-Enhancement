package cs3500.model.fileformat;

/**
 * A class to represent a PNG image. Offers the capability to to import/export an image of this
 * type. This class is a function object that can <code>export: IImage, String -> File</code> and
 * <code>Import: String -> IImage</code>, and has a unique file extension ".png"
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