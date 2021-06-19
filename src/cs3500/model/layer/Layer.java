package cs3500.model.layer;

import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.image.IImage;

/**
 * A class to represent a single Layer in a Multi-layered image.
 */
public class Layer implements ILayer {

  private boolean isInvisible;
  private final IStateTrackingIMEModel model;
  private int layerHeight;
  private int layerWidth;

  /**
   * Constructs a Layer with default parameters.
   */
  public Layer() {
    this.model = new StateTrackingIMEModelImpl();
    this.isInvisible = false;
    this.layerHeight = -1;
    this.layerWidth = -1;
  }

  /**
   * Constructs a Layer with tall given arguments.
   *
   * @param model the model to build the layer with.
   */
  public Layer(IStateTrackingIMEModel model, boolean isInvisible, int layerHeight, int layerWidth) {
    this.model = model;
    this.isInvisible = isInvisible;
    this.layerHeight = layerHeight;
    this.layerWidth = layerWidth;
  }

  @Override
  public boolean isInvisible() {
    return this.isInvisible;
  }

  @Override
  public void toggleInvisible() {
    this.isInvisible = !this.isInvisible;
  }

  @Override
  public IStateTrackingIMEModel getModel() {
    return this.model;
  }

  @Override
  public void modelLoad(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image is null");
    }
    this.model.load(image);
    this.updateFields();
  }

  /**
   * Updates the fields for this layer whenever the model is used.
   */
  private void updateFields() {
    this.layerWidth = this.model.getImage().getWidth();
    this.layerHeight = this.model.getImage().getHeight();
  }

  @Override
  public int getLayerHeight() {
    return this.layerHeight;
  }

  @Override
  public int getLayerWidth() {
    return this.layerWidth;
  }

  @Override
  public String toString() {
    return " | Visibility: " + !this.isInvisible;
  }

  @Override
  public ILayer copy() {
    this.updateFields();
    return new Layer((IStateTrackingIMEModel) this.model.copy(), this.isInvisible,
        this.layerHeight, this.layerWidth);
  }
}