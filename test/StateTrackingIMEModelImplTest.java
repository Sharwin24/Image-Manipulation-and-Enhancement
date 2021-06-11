import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.model.IIMEModel;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.IOperation;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for methods in the {@link cs3500.model.StateTrackingIMEModelImpl} class.
 */
public class StateTrackingIMEModelImplTest {

  private final IImage simple3x3 =
      new ImageImpl(new MatrixImpl<>(new ArrayList<>(Arrays.asList(
          PixelImpl.RED,
          PixelImpl.GREEN,
          PixelImpl.BLUE)), 3));


  /**
   * Tests for the {@link StateTrackingIMEModelImpl#StateTrackingIMEModelImpl()} method.
   */
  @Test
  public void testNullaryConstructorSetsEmptyImage() {
    assertEquals(new ImageImpl(new MatrixImpl<>()),
        new StateTrackingIMEModelImpl().retrieve());
  }

  /**
   * Tests for the {@link StateTrackingIMEModelImpl# StateTrackingIMEModelImpl(cs3500.model.image.IImage)}
   * constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testImageConstructorThrowsWhenPassedNullImage() {
    new StateTrackingIMEModelImpl(null);
  }

  /**
   * Tests for the {@link StateTrackingIMEModelImpl#applyOperations(IOperation...)} method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testApplyOperationsThrowsWhenOperationsNull() {
    new StateTrackingIMEModelImpl().applyOperations(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyOperationsThrowsWhenOperationsHasNull() {
    new StateTrackingIMEModelImpl().applyOperations(new ImageBlur(), null);
  }

  @Test
  public void testApplyOperationsActuallyChangesImage() {
    StateTrackingIMEModelImpl mdl = new StateTrackingIMEModelImpl(simple3x3);
    IImage oldImage = mdl.retrieve().copy();
    mdl.applyOperations(new ImageBlur(), new Sepia());
    IImage newImage = mdl.retrieve().copy();

    assertNotEquals(newImage, oldImage);
  }


}