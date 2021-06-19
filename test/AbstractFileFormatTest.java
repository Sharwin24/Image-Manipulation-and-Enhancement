import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.model.fileformat.PPMFile;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for Testing FileFormatMethods.
 */
public abstract class AbstractFileFormatTest {

  private IFileFormat format;

  @Before
  public void init() {
    this.format = this.constructFileFormat();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenGivenNullRelativePath() {
    this.format.importImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenGivenEmptyRelativePath() {
    this.format.importImage("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenRelativePathDoesntEndInPPM() {
    this.format.importImage("blablabla.bike");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenPathNotFound() {
    this.format.importImage("cant find this path");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenPPMCorrupted() {
    this.format.importImage("res/CorruptedFile.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenPPMNotOfTypeP3() {
    this.format.importImage("res/NotP3.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenPPMEmpty() {
    this.format.importImage("res/EmptyFile.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenNotGivenPPM() {
    this.format.importImage("res/NotAPPM.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportImageThrowsForNullPathName() {
    this.format.exportImage(null, new ImageImpl(
        new MatrixImpl<>()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportImageThrowsForNullImage() {
    this.format.exportImage("nullImage->", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportImageThrowsForEmptyPathName() {
    this.format.exportImage("", new ImageImpl(
        new MatrixImpl<>()));
  }

  @Test
  public void testExportImageCreatesFile() {
    IMatrix<IPixel> pxs = new MatrixImpl<>(new PixelImpl(255, 0, 0),
        3, 3);
    assertTrue(this.format.exportImage("res/testCreateFile",
        new ImageImpl(pxs)).exists());
  }

  protected abstract IFileFormat constructFileFormat();

  /**
   * Class for Constructing a PPM to test with.
   */
  public static class PPMTest extends AbstractFileFormatTest {

    @Override
    protected IFileFormat constructFileFormat() {
      return new PPMFile();
    }

    @Test
    public void testImportImageSuccessfulReturnsImageWithSameDimensions() {
      IImage imported = new PPMFile().importImage("res/Simple8x8.ppm");
      assertEquals(8, imported.getPixelArray().getHeight());
      assertEquals(8, imported.getPixelArray().getWidth());
    }

    @Test
    public void testImportImageSuccessfulReturnsImageWithPixels() {
      IImage imported = new PPMFile().importImage("res/Simple8x8.ppm");
      assertEquals(new PixelImpl(255, 0, 0),
          imported.getPixelArray().getElement(0, 0));
    }
  }

  /**
   * Class for Constructing a PNG to test with.
   */
  public static class PNGTest extends AbstractFileFormatTest {

    @Override
    protected IFileFormat constructFileFormat() {
      return new PNGFile();
    }
  }

  /**
   * Class for Constructing a JPEG to test with.
   */
  public static class JPEGTest extends AbstractFileFormatTest {

    @Override
    protected IFileFormat constructFileFormat() {
      return new JPEGFile();
    }
  }
}