import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.pixel.PixelImpl;
import cs3500.model.programmaticimages.Checkerboard;
import cs3500.view.IMEView;
import cs3500.view.TextualIMEView;
import java.awt.SystemColor;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing the Multilayer model implementation.
 */
public class MultiLayerModelTest {

  private IMultiLayerModel model;
  private IImage image;
  private IImage diffColorImage;

  @Before
  public void init() {
    this.model = new MultiLayerModelImpl();
    this.image = new ImageImpl(new MatrixImpl<>(new PixelImpl(255, 255, 255), 10, 10));
    this.diffColorImage = new ImageImpl(new MatrixImpl<>(new PixelImpl(100, 100, 100), 10, 10));
  }

  // applyOperations
  @Test
  public void testOperations() {
    this.model.load(image);
    this.model.applyOperations(new ImageBlur());
    assertNotEquals(this.image, this.model.getImage());
  }

  // load
  @Test
  public void testLoad() {
    this.model.load(this.image);
    assertEquals(10, this.model.getImage().getHeight());
    assertEquals(10, this.model.getImage().getWidth());
    assertEquals(this.image, this.model.getImage());
  }

  // setProgramImage
  @Test
  public void testProgramImage() {
    this.model.load(this.image);
    this.model.addLayer();
    this.model.setProgrammaticImage(new Checkerboard(), 10, 10, 1);
    IImage checkerBoard = new Checkerboard().createProgramImage(10, 10, 1);
    assertEquals(checkerBoard, this.model.getImage());
  }

  // getImage
  @Test
  public void testGetImage() {
    this.model.load(this.image);
    assertEquals(this.image, this.model.getImage());
  }

  // toggleInvisible
  @Test
  public void testInvisible() {
    this.model.load(this.image);
    this.model.toggleInvisible(0);
    assertTrue(this.model.getLayers().get(0).isInvisible());
  }

  // setCurrentLayer
  @Test
  public void testSetCurrentLayer() {
    this.model.addLayer();
    assertEquals(2, this.model.getLayers().size());
    this.model.load(diffColorImage);
    assertEquals(diffColorImage, this.model.getImage());
    this.model.setCurrentLayer(1);
    this.model.load(this.image);
    assertEquals(this.image, this.model.getImage());
  }

  // addLayer and deleteLayer
  @Test
  public void testAddLayer() {
    this.model.load(this.image);
    this.model.addLayer();
    this.model.addLayer();
    assertEquals(3, this.model.getLayers().size());
    this.model.deleteLayer(0);
    assertEquals(2, this.model.getLayers().size());
  }

  // swapLayers
  @Test
  public void testSwapLayers() {
    this.model.load(this.image);
    this.model.addLayer();
    this.model.setCurrentLayer(1);
    this.model.load(diffColorImage);
    this.model.setCurrentLayer(0);
    this.model.swapLayers(0, 1);
    assertEquals(this.diffColorImage.getPixelArray(), this.model.getImage().getPixelArray());
    this.model.setCurrentLayer(1);
    assertEquals(this.image.getPixelArray(), this.model.getImage().getPixelArray());

  }

  // getLayers
  @Test
  public void getLayersShouldReturnList() {
    assertEquals(1, model.getLayers().size());
    model.addLayer();
    model.addLayer();
    model.addLayer();
    assertEquals(4, model.getLayers().size());
    model.deleteLayer(3);
    assertEquals(3, model.getLayers().size());
  }

  // undo and redo
  @Test
  public void testUndoAndRedo() {
    this.model.load(image);
    this.model.applyOperations(new Greyscale());
    this.model.undo();
    assertEquals(this.image, this.model.getImage());
    this.model.redo();
    IImage greyScaleImage = new Greyscale().apply(this.image);
    assertEquals(greyScaleImage, this.model.getImage());
  }

  // save
  @Test
  public void testSave() {
    this.model.load(image);
    this.model.save();
    assertEquals(this.image, this.model.getImage());
  }

  // copy
  @Test
  public void testCopy() {
    this.model.load(image);
    this.model.addLayer();
    this.model.addLayer();
    IMultiLayerModel modelCopy = (IMultiLayerModel) this.model.copy();
    assertEquals(this.model.getImage(), modelCopy.getImage());
    assertEquals(this.model.getLayers().size(), modelCopy.getLayers().size());
  }

  // Exceptions
  // applyOperations gets invalid operation
  @Test(expected = IllegalArgumentException.class)
  public void applyOpGivenNull() {
    this.model.load(image);
    this.model.applyOperations(null);
  }

  // load gets null image
  @Test(expected = IllegalArgumentException.class)
  public void loadGivenNull() {
    this.model.load(null);
  }

  // setProgramImage gets invalid image or different size image on another layer
  @Test(expected = IllegalArgumentException.class)
  public void setProgramImageGivenNull() {
    this.model.setProgrammaticImage(null, 10, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setProgramImageGivenInvalidSize() {
    this.model.load(image);
    this.model.addLayer();
    this.model.setCurrentLayer(1);
    this.model.setProgrammaticImage(new Checkerboard(), 12, 12, 1);
  }

  // toggleInvisible is given invalid layerIndex
  @Test(expected = IllegalArgumentException.class)
  public void toggleInvisibleGivenInvalidIndex() {
    this.model.toggleInvisible(1);
  }

  // setCurrentLayer is given invalid layerIndex
  @Test(expected = IllegalArgumentException.class)
  public void setCurrentLayerGivenInvalidIndex() {
    this.model.setCurrentLayer(1);
  }

  // deleteLayer is given invalid layerIndex
  @Test(expected = IllegalArgumentException.class)
  public void deleteLayerGivenInvalidIndex() {
    this.model.deleteLayer(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteOnlyLayer() {
    this.model.deleteLayer(0);
  }

  // swapLayers is given invalid layerIndexes or same index
  @Test(expected = IllegalArgumentException.class)
  public void swapLayersGivenInvalidIndex() {
    this.model.load(image);
    this.model.addLayer();
    this.model.setCurrentLayer(1);
    this.model.load(this.diffColorImage);
    this.model.swapLayers(0, 2);
  }

  // undo fails
  @Test(expected = IllegalArgumentException.class)
  public void undoNotPossible() {
    this.model.load(this.image);
    this.model.undo();
  }

  // redo fails
  @Test(expected = IllegalArgumentException.class)
  public void redoNotPossible() {
    this.model.load(this.image);
    this.model.redo();
  }

}