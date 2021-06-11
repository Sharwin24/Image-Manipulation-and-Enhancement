import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.IOperation;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.MyFilter;
import cs3500.model.operation.Sharpening;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import cs3500.model.operation.IFilter;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract class for testing all {@link IFilter} methods and behavior.
 */
public abstract class AbstractFilterTest {

  private IOperation op;
  private IOperation zeros;
  private IImage redImage;
  private IImage testImage;

  /**
   * Creates a 3x3 Matrix of red pixels.
   *
   * @return an Image of a red square.
   */
  private IMatrix<IPixel> redSquare() {
    return new MatrixImpl<>(new PixelImpl(100, 0, 0), 3, 3);
  }

  /**
   * Creates a 3x3 Matrix of pixels.
   *
   * @return an Image of a square.
   */
  private IMatrix<IPixel> testSquare() {
    return new MatrixImpl<>(new PixelImpl(1, 2, 3), 3, 3);
  }

  @Before
  public void init() {
    this.op = this.constructFilter();
    this.zeros = new MyFilter();
    this.redImage = new ImageImpl(this.redSquare());
    this.testImage = new ImageImpl(this.testSquare());
  }

  // Filtering with a kernel of zeros makes entire array zeros.
  @Test
  public void filterWithZeros() {
    IImage zeroImage = this.zeros.apply(this.redImage);
    for (int i = 0; i < this.redImage.getPixelArray().getHeight(); i++) {
      for (int j = 0; j < this.redImage.getPixelArray().getWidth(); j++) {
        assertEquals(new PixelImpl(0, 0, 0), zeroImage.getPixelArray().getElement(i, j));
      }
    }
  }

  // Filtering an image makes changes to all channels of an image.
  @Test
  public void testFilter() {
    IImage filtered = this.op.apply(this.testImage);
    assertNotEquals(filtered.getPixelArray(), testImage.getPixelArray());
  }

  // Kernel size larger than image modifies image correctly
  @Test
  public void filterWithLargeKernel() {
    IImage smallImage = new ImageImpl(new MatrixImpl<>(new PixelImpl(0, 1, 0), 2, 3));
    IImage output = this.op.apply(smallImage);
    assertNotEquals(output, smallImage);
  }

  // Exceptions:
  // Filtering with null image given
  @Test(expected = IllegalArgumentException.class)
  public void filterWithNull() {
    this.op.apply(null);
  }

  /**
   * Constructs an IOperation.
   *
   * @return an IOperation.
   */
  protected abstract IOperation constructFilter();

  /**
   * Class for testing the Sharpening Filter.
   */
  public static class SharpeningTest extends AbstractFilterTest {

    @Override
    protected IOperation constructFilter() {
      return new Sharpening();
    }
  }

  /**
   * Class for testing the Blur Filter.
   */
  public static class BlurTest extends AbstractFilterTest {

    @Override
    protected IOperation constructFilter() {
      return new ImageBlur();
    }
  }

}
