import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.layer.ILayer;
import cs3500.model.layer.Layer;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.PixelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for tests for the Layer implementation.
 */
public class LayerTest {

  private ILayer layer;
  private IImage image;

  @Before
  public void init() {
    this.layer = new Layer();
    this.image = new ImageImpl(new MatrixImpl<>(new PixelImpl(255, 255, 255), 10, 10));
  }

  // isInvisible and toggleInvisible
  @Test
  public void testInvisible() {
    assertFalse(this.layer.isInvisible());
    this.layer.toggleInvisible();
    assertTrue(this.layer.isInvisible());
  }

  // getModel
  @Test
  public void testGetModel() {
    IStateTrackingIMEModel model = new StateTrackingIMEModelImpl();
    model.load(this.image);
    this.layer.modelLoad(this.image);
    assertEquals(model.getImage(), this.layer.getModel().getImage());
  }

  // getLayerHeight and Width
  @Test
  public void testGetHeightAndWidth() {
    assertEquals(-1, this.layer.getLayerHeight());
    assertEquals(-1, this.layer.getLayerWidth());
    this.layer.modelLoad(image);
    assertEquals(10, this.layer.getLayerHeight());
    assertEquals(10, this.layer.getLayerWidth());
  }

  // toString
  @Test
  public void testToString() {
    assertEquals(" | Visibility: true", this.layer.toString());
    this.layer.toggleInvisible();
    assertEquals(" | Visibility: false", this.layer.toString());
  }

  // copy
  @Test
  public void copyReturnsAnotherLayer() {
    this.layer.modelLoad(this.image);
    ILayer layerCopy = this.layer.copy();
    assertNotEquals(layerCopy, this.layer);
    assertNotEquals(layerCopy.getModel(), this.layer.getModel());
    assertEquals(layerCopy.getLayerHeight(), this.layer.getLayerHeight());
    assertEquals(layerCopy.getLayerWidth(), this.layer.getLayerWidth());
  }
}