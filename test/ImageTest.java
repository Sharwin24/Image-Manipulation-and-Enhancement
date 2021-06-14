import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.model.channel.EChannelType;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing the Image implementation.
 */
public class ImageTest {

  private IImage redImage;
  private IImage blankImage;

  /**
   * Creates a 3x3 Matrix of red pixels.
   *
   * @return an Image of a red square.
   */
  private IMatrix<IPixel> redSquare() {
    return new MatrixImpl<>(new PixelImpl(100, 0, 0), 3, 3);
  }

  /**
   * Creates a 3x3 Matrix of black pixels.
   *
   * @return an Image of a black square.
   */
  private IMatrix<IPixel> blackSquare() {
    return new MatrixImpl<>(new PixelImpl(0, 0, 0), 3, 3);
  }

  @Before
  public void init() {
    this.redImage = new ImageImpl(redSquare());
    IImage blackImage = new ImageImpl(blackSquare());
    this.blankImage = new ImageImpl(new MatrixImpl<>());
  }

  // extractChannel
  @Test
  public void testExtractChannel() {
    IMatrix<Integer> expected = new MatrixImpl<>(100, 3, 3);
    assertEquals(expected, redImage.extractChannel(EChannelType.RED));
  }

  // getPixelArray
  @Test
  public void testGetPixelArray() {
    assertEquals(3, redImage.getPixelArray().getWidth());
    assertEquals(3, redImage.getPixelArray().getHeight());
    assertEquals(new PixelImpl(100, 0, 0), redImage.getPixelArray().getElement(2, 2));
  }

  @Test
  public void blankImagePixelArray() {
    assertEquals(0, blankImage.getPixelArray().getWidth());
    assertEquals(0, blankImage.getPixelArray().getHeight());
  }

  // copy returns a deep copy
  @Test
  public void testDeepCopy() {
    assertNotEquals(this.redImage.copy().hashCode(),
        new ImageImpl(new MatrixImpl<>(new PixelImpl(100, 0, 0), 3, 3)).hashCode());
  }

  // Exceptions:
  // Constructor Exception for invalid Imatrix
  @Test(expected = IllegalArgumentException.class)
  public void imageConstructorGivenNullMatrix() {
    IImage image = new ImageImpl(new MatrixImpl<>(null, 0, 0));
  }
}