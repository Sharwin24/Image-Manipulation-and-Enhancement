package cs3500.model;

import cs3500.model.image.IImage;
import cs3500.model.layer.ILayer;
import cs3500.model.layer.Layer;
import cs3500.model.operation.IOperation;
import cs3500.model.programmaticimages.IProgramImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class to represent the Model for an Image processor with multiple layer. Offers functionality
 * to apply operations to different layers and to import/export separate layers.
 */
public class MultiLayerModelImpl implements IMultiLayerModel {

  // Represent Layers
  // Layer: A stateTracking model to track and edit one image in one layer.
  // Current Working Layer: represents the current working layer

  // Layer Class implementation
  private final List<ILayer> listOfLayers;
  private ILayer currentLayer;
  private int currentIndex;
  private int layersImageHeight;
  private int layersImageWidth;

  /**
   * Constructs a Multi-Layer model with defaults for the class fields. Initializes lists as empty,
   * and the default appendable as {@code System.out} and default readable as {@code System.in}.
   */
  public MultiLayerModelImpl() {
    this.listOfLayers = new ArrayList<>();
    this.listOfLayers.add(new Layer());
    this.currentLayer = this.listOfLayers.get(0);
    this.currentIndex = 0;
    this.layersImageHeight = -1;
    this.layersImageWidth = -1;
  }

  /**
   * Constructs a custom Multi-layer model with given parameters.
   *
   * @param layerCopy    the list of layers as a copy.
   * @param currentLayer the current layer the model is on.
   * @param height       the layer's height.
   * @param width        the layer's width.
   */
  private MultiLayerModelImpl(List<ILayer> layerCopy, ILayer currentLayer, int height, int width) {
    this.listOfLayers = layerCopy;
    this.currentLayer = currentLayer;
    this.layersImageHeight = height;
    this.layersImageWidth = width;

  }

  @Override
  public void applyOperations(IOperation... operations) throws IllegalArgumentException {
    if (operations == null || operations.length == 0) {
      throw new IllegalArgumentException("Invalid operations");
    }
    this.currentLayer.getModel().applyOperations(operations);
  }

  @Override
  public void load(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image is null");
    }
    if (this.layersImageWidth == -1 && this.layersImageHeight == -1) {
      this.currentLayer.modelLoad(image);
      this.layersImageWidth = image.getWidth();
      this.layersImageHeight = image.getHeight();
    } else {
      if (image.getHeight() != this.layersImageHeight
          || image.getWidth() != this.layersImageWidth) {
        throw new IllegalArgumentException("All layers must have the same height and width");
      }
      this.currentLayer.modelLoad(image);
    }
  }

  @Override
  public void setProgrammaticImage(IProgramImage imgToSet, int widthPx, int heightPx,
      int unitSizePx) throws IllegalArgumentException {
    if (imgToSet == null) {
      throw new IllegalArgumentException("Program image is null");
    }
    if (this.layersImageWidth == -1 && this.layersImageHeight == -1) {
      this.currentLayer.getModel().setProgrammaticImage(imgToSet, widthPx, heightPx, unitSizePx);
      this.layersImageHeight = heightPx;
      this.layersImageWidth = widthPx;
    } else {
      if (widthPx != this.layersImageWidth || heightPx != this.layersImageHeight) {
        throw new IllegalArgumentException("Invalid given Size, All layers must have same size");
      }
      this.currentLayer.getModel().setProgrammaticImage(imgToSet, widthPx, heightPx, unitSizePx);
    }

  }

  @Override
  public IImage getImage() {
    return this.currentLayer.getModel().getImage();
  }


  @Override
  public void toggleInvisible(int layerIndex) throws IllegalArgumentException {
    if (layerIndex < 0 || layerIndex >= this.listOfLayers.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    this.listOfLayers.get(layerIndex).toggleInvisible();
  }

  @Override
  public void setCurrentLayer(int layerIndex) throws IllegalArgumentException {
    if (layerIndex < 0 || layerIndex >= this.listOfLayers.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    this.currentLayer = this.listOfLayers.get(layerIndex);
    this.currentIndex = layerIndex;
  }

  @Override
  public void addLayer() {
    this.listOfLayers.add(new Layer());
  }

  @Override
  public void deleteLayer(int layerIndex) throws IllegalArgumentException {
    if (layerIndex < 0 || layerIndex >= this.listOfLayers.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    if (this.listOfLayers.size() == 1) {
      throw new IllegalArgumentException("Cannot delete last layer");
    }
    this.listOfLayers.remove(layerIndex);
  }

  @Override
  public void swapLayers(int layerIndex1, int layerIndex2) throws IllegalArgumentException {
    if (layerIndex1 < 0 || layerIndex1 >= this.listOfLayers.size()
        || layerIndex2 < 0 || layerIndex2 >= this.listOfLayers.size()
        || layerIndex1 == layerIndex2) {
      throw new IllegalArgumentException("Layer Indexes invalid");
    }
    Collections.swap(this.listOfLayers, layerIndex1, layerIndex2);
    this.currentLayer = this.listOfLayers.get(this.currentIndex);
  }

  @Override
  public List<ILayer> getLayers() {
    // return a deep copy
    List<ILayer> layersCopy = new ArrayList<>();
    // Collections.copy(layerCopy, this.listOfLayers);
    for (ILayer lyr : this.listOfLayers) {
      layersCopy.add(lyr);
    }
    return layersCopy;
  }

  @Override
  public void undo() throws IllegalArgumentException {
    this.currentLayer.getModel().undo();
  }

  @Override
  public void redo() throws IllegalArgumentException {
    this.currentLayer.getModel().redo();
  }

  @Override
  public void save() {
    for (ILayer layer : this.listOfLayers) {
      layer.getModel().save();
    }
  }

  @Override
  public IMultiLayerModel copy() {
    List<ILayer> layers = new ArrayList<>();
    for (ILayer layer : this.listOfLayers) {
      layers.add(layer.copy());
    }
    return new MultiLayerModelImpl(layers, this.currentLayer.copy(), this.layersImageHeight,
        this.layersImageWidth);
  }

}