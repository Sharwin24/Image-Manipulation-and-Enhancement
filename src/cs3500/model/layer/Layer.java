package cs3500.model.layer;

import cs3500.model.IMultiLayerModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.image.IImage;

/**
 * A class to represent a single Layer in a Multi-layered image.
 */
public class Layer implements ILayer {

  private boolean isInvisible;
  private IStateTrackingIMEModel<IImage> model;
  private int layerHeight;
  private int layerWidth;
  private String layerName;

  /**
   * Constructs a Layer with default parameters.
   */
  public Layer() {
    this.model = new MultiLayerModelImpl();
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
  public void importImage(IFileFormat<IImage> format, String filePath) {
    if (format == null || filePath == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    this.layerName = filePath;
    this.model.importImage(format, filePath);
    this.layerHeight = this.model.retrieve().getHeight();
    this.layerWidth = this.model.retrieve().getWidth();
  }

  @Override
  public IStateTrackingIMEModel<IImage> getModel() {
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
}