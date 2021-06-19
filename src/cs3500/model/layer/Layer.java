package cs3500.model.layer;

import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.StateTrackingIMEModelImpl;

/**
 * A class to represent a single Layer in a Multi-layered image.
 */
public class Layer implements ILayer {

  private boolean isInvisible;
  private final IStateTrackingIMEModel model;
  private int layerHeight;
  private int layerWidth;
  private String layerName;

  /**
   * Constructs a Layer with default parameters.
   */
  public Layer() {
    this.model = new StateTrackingIMEModelImpl();
    this.isInvisible = false;
    this.layerHeight = -1;
    this.layerWidth = -1;
    this.layerName = "";
  }

  /**
   * Constructs a Layer with tall given arguments.
   *
   * @param model the model to build the layer with.
   */
  public Layer(IStateTrackingIMEModel model, boolean isInvisible, int layerHeight, int layerWidth
      , String layerName) {
    this.model = model;
    this.isInvisible = isInvisible;
    this.layerHeight = layerHeight;
    this.layerWidth = layerWidth;
    this.layerName = layerName;
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
    this.layerWidth = this.model.getImage().getWidth();
    this.layerHeight = this.model.getImage().getHeight();
    return this.model;
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
    return this.layerName + " | Visibility: " + !this.isInvisible;
  }

  @Override
  public ILayer copy() {
    return new Layer((IStateTrackingIMEModel) this.model.copy(), this.isInvisible,
        this.layerHeight, this.layerWidth, this.layerName);
  }
}