import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.model.fileformat.PPMFile;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import org.junit.Test;

/**
 * Tests for methods in the {@link cs3500.model.fileformat.PPMFile} class.
 */
public class PPMFileTest {

  /**
   * Tests for the {@link cs3500.model.fileformat.PPMFile#importImage(String)} method.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenGivenNullRelativePath() {
    new PPMFile().importImage(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenGivenEmptyRelativePath() {
    new PPMFile().importImage("");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenRelativePathDoesntEndInPPM() {
    new PPMFile().importImage("blablabla.bike");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenPathNotFound() {
    new PPMFile().importImage("cant find this path");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenPPMCorrupted() {
    new PPMFile().importImage("res/CorruptedFile.ppm");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenPPMNotOfTypeP3() {
    new PPMFile().importImage("res/NotP3.ppm");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenPPMEmpty() {
    new PPMFile().importImage("res/EmptyFile.ppm");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImportImageThrowsWhenNotGivenPPM() {
    new PPMFile().importImage("res/NotAPPM.txt");
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
        imported.getPixelArray().getElement(0,0));
  }

  /**
   * Tests for the {@link PPMFile#exportImage(String, IImage)} method.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testExportImageThrowsForNullPathName() {
    new PPMFile().exportImage(null, new ImageImpl(
        new MatrixImpl<>()));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testExportImageThrowsForNullImage() {
    new PPMFile().exportImage("nullImage->", null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testExportImageThrowsForEmptyPathName() {
    new PPMFile().exportImage("", new ImageImpl(
        new MatrixImpl<>()));
  }

  @Test
  public void testExportImageCreatesFile() {
    IMatrix<IPixel> pxs = new MatrixImpl<>(new PixelImpl(255, 0, 0),
        3, 3);
    assertTrue(new PPMFile().exportImage("res/testCreateFile",
        new ImageImpl(pxs)).exists());
  }






}