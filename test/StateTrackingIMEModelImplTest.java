import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import cs3500.model.IIMEModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.fileFormat.IFileFormat;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.IOperation;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import cs3500.model.programmaticImages.Checkerboard;
import cs3500.model.programmaticImages.RainbowNoise;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.plaf.nimbus.State;
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
   * Tests for the
   * {@link StateTrackingIMEModelImpl#StateTrackingIMEModelImpl(cs3500.model.image.IImage)}
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
    IStateTrackingIMEModel mdl = new StateTrackingIMEModelImpl(simple3x3);
    IImage oldImage = mdl.retrieve().copy();
    mdl.applyOperations(new ImageBlur(), new Sepia());
    IImage newImage = mdl.retrieve().copy();

    assertNotEquals(newImage, oldImage);
  }

  /**
   * Tests for the {@link StateTrackingIMEModelImpl#undo()} method.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testUndoThrowsWhenNothingToUndo() {
    IStateTrackingIMEModel m = new StateTrackingIMEModelImpl();
    m.undo();
  }

  @Test
  public void testUndoUndoesOperation() {
    IStateTrackingIMEModel m =
        new StateTrackingIMEModelImpl();
    m.setProgrammaticImage(new RainbowNoise(), 10, 10, 1);
    IImage originalImage = m.retrieve();
    m.applyOperations(new Sepia());
    m.undo();
    IImage undoneImage = m.retrieve();

    assertEquals(undoneImage, originalImage);
  }

  @Test
  public void testUndoTwice() {
    IStateTrackingIMEModel m =
        new StateTrackingIMEModelImpl();
    m.setProgrammaticImage(new RainbowNoise(), 10, 10, 1);
    IImage originalImage = m.retrieve();
    m.applyOperations(new Sepia(), new Sharpening());
    m.undo();
    m.undo();
    IImage undoneImage = m.retrieve();

    assertEquals(undoneImage, originalImage);
  }

  /**
   * Tests for the {@link StateTrackingIMEModelImpl#redo()} method.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRedoThrowsWhenNothingToRedo() {
    IStateTrackingIMEModel m = new StateTrackingIMEModelImpl();
    m.redo();
  }

  @Test
  public void testRedoRedoesOperation() {
    IStateTrackingIMEModel m =
        new StateTrackingIMEModelImpl();
    m.setProgrammaticImage(new RainbowNoise(), 10, 10, 1);
    m.applyOperations(new Sepia());
    IImage originalImage = m.retrieve();
    m.undo();
    m.redo();
    IImage RedoneImage = m.retrieve();

    assertEquals(RedoneImage, originalImage);
  }

  @Test
  public void testRedoTwice() {
    IStateTrackingIMEModel m =
        new StateTrackingIMEModelImpl();
    m.setProgrammaticImage(new RainbowNoise(), 10, 10, 1);
    IImage originalImage = m.retrieve();
    m.applyOperations(new Sepia(), new Sharpening());
    m.undo();
    m.undo();
    m.redo();
    m.redo();
    IImage RedoneImage = m.retrieve();

    assertEquals(RedoneImage, originalImage);
  }

  /**
   * Tests for the {@link StateTrackingIMEModelImpl#save()} method.
   */
  @Test
  public void testSaveSavesNewCopy() {
    IStateTrackingIMEModel m = new StateTrackingIMEModelImpl();
    m.setProgrammaticImage(new Checkerboard(), 10, 10, 2);
    IImage oldImage = m.retrieve();
    m.save();
    IImage savedImage = m.retrieve();

    assertEquals(oldImage, savedImage);
    assertFalse(oldImage == savedImage);
  }

  /**
   * Tests for the {@link StateTrackingIMEModelImpl#retrieve()} method.
   */
  @Test
  public void testRetrieveReturnsEqualCopyNotReference() {
    IStateTrackingIMEModel m =
        new StateTrackingIMEModelImpl();
    IImage emptyImage = new ImageImpl(new MatrixImpl<>());
    assertEquals(emptyImage, m.retrieve());
    assertFalse(emptyImage == m.retrieve());
  }

  /**
   * Tests for the {@link StateTrackingIMEModelImpl#importImage(IFileFormat, String)} method.
   */

}