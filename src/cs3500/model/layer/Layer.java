package cs3500.model.layer;

import cs3500.model.IMultiLayerModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.image.IImage;

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
   * Constructs a Layer with the given model.
   *
   * @param model the model to build the layer with.
   */
  public Layer(IStateTrackingIMEModel model) {
    this.model = model;
    this.isInvisible = false;
    this.layerHeight = -1;
    this.layerWidth = -1;
    this.layerName = "";
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
  public int getLayerHeight() {
    return this.layerHeight;
  }

  @Override
  public int getLayerWidth() {
    return this.layerWidth;
  }

  @Override
  public String toString() {
    return this.layerName + "Visible: " + !this.isInvisible;
  }

  @Override
  public ILayer copy() {
    return new Layer(this.model.copy());
  }
}