import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.IOperation;
import cs3500.model.operation.MyFilter;
import cs3500.model.operation.Sepia;
import cs3500.model.pixel.PixelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract class to test Color Transforms.
 */
public abstract class AbstractColorTransformTest {

  private IOperation op;
  private IOperation zeros;
  private IImage testImage;

  @Before
  public void init() {
    this.op = constructColorTransform();
    this.zeros = new MyFilter();
    this.testImage = new ImageImpl(new MatrixImpl<>(new PixelImpl(0, 100, 0), 10, 10));
  }

  // Color Transforming an image changes all channels.
  @Test
  public void testFilter() {
    IImage filtered = this.op.apply(this.testImage);
    assertNotEquals(filtered.getPixelArray(), testImage.getPixelArray());
  }

  // Color transform of zeros matrix results in black image.
  @Test
  public void filterWithZeros() {
    IImage zeroImage = this.zeros.apply(this.testImage);
    for (int i = 0; i < this.testImage.getPixelArray().getHeight(); i++) {
      for (int j = 0; j < this.testImage.getPixelArray().getWidth(); j++) {
        assertEquals(new PixelImpl(0, 0, 0), zeroImage.getPixelArray().getElement(i, j));
      }
    }
  }

  // Exceptions:
  // Color Transform when given null image
  @Test(expected = IllegalArgumentException.class)
  public void filterWithNull() {
    this.op.apply(null);
  }

  /**
   * Constructs a ColorTransform for testing.
   */
  protected abstract IOperation constructColorTransform();

  /**
   * Static class for testing Greyscale.
   */
  public static class GreyscaleTest extends AbstractColorTransformTest {

    @Override
    protected IOperation constructColorTransform() {
      return new Greyscale();
    }
  }

  /**
   * Static class for testing Sepia.
   */
  public static class SepiaTest extends AbstractColorTransformTest {

    @Override
    protected IOperation constructColorTransform() {
      return new Sepia();
    }
  }
}
