import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.model.fileformat.PPMFile;

/**
 * Class for Testing FileFormatMethods.
 */
public abstract class AbstractFileFormatTest {

  protected abstract IFileFormat constructFileFormat();

  /**
   * Todo:
   */
  public static class PPMTest extends AbstractFileFormatTest {

    @Override
    protected IFileFormat constructFileFormat() {
      return new PPMFile();
    }
  }

  /**
   * Todo:
   */
  public static class PNGTest extends AbstractFileFormatTest {

    @Override
    protected IFileFormat constructFileFormat() {
      return new PNGFile();
    }
  }

  /**
   * Todo:
   */
  public static class JPEGTest extends AbstractFileFormatTest {

    @Override
    protected IFileFormat constructFileFormat() {
      return new JPEGFile();
    }
  }
}