package cs3500.model;

import cs3500.model.fileformat.IFileFormat;
import cs3500.model.image.IImage;
import cs3500.model.layer.ILayer;
import cs3500.model.layer.Layer;
import cs3500.model.operation.IOperation;
import cs3500.model.programmaticimages.IProgramImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    this.layersImageHeight = -1;
    this.layersImageWidth = -1;
  }

  @Override
  public void applyOperations(IOperation... operations) throws IllegalArgumentException {
    this.currentLayer.getModel().applyOperations(operations);
  }

  @Override
  public void load(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image is null");
    }
    if (this.layersImageWidth != -1 && this.layersImageHeight != -1
        && (image.getWidth() != this.layersImageWidth
        || image.getHeight() != this.layersImageHeight)) {
      throw new IllegalArgumentException("All layers must have the same height and width");
    }
    if (this.layersImageWidth == -1 && this.layersImageHeight == -1) {
      this.layersImageWidth = image.getWidth();
      this.layersImageHeight = image.getHeight();
      this.currentLayer.getModel().load(image);
    }
  }

  @Override
  public void setProgrammaticImage(IProgramImage imgToSet, int widthPx, int heightPx,
      int unitSizePx) throws IllegalArgumentException {
    if (imgToSet == null) {
      throw new IllegalArgumentException("Progam image is null");
    }
    if (this.layersImageWidth != -1 && this.layersImageHeight != -1) {
      if (widthPx != this.layersImageWidth || heightPx != this.layersImageHeight) {
        throw new IllegalArgumentException("Invalid Size for Image, All layers must have the same "
            + "size.");
      }
    }
    this.currentLayer.getModel().setProgrammaticImage(imgToSet, widthPx, heightPx, unitSizePx);
    this.layersImageHeight = heightPx;
    this.layersImageWidth = widthPx;
  }

  @Override
  public IImage getImage() {
    return this.currentLayer.getModel().getImage();
  }

//  @Override
//  public void importAllLayers(IFileFormat fileType, String pathName)
//      throws IllegalArgumentException {
//    // Import the given image at the file for all layers, regardless of current layer.
//    for (ILayer layer : this.listOfLayers) {
//      layer.importImage(fileType, pathName);
//    }
//  }

//  @Override
//  public void exportAllLayers(IFileFormat fileType, String pathName)
//      throws IllegalArgumentException {
//    // Create new folder with each image file exported in it
//    // Ignore layers that are marked invisible
//    // Create Text file with all exported paths
//    int layerCounter = 0;
//    for (ILayer layer : this.listOfLayers) {
//      layer.getModel().exportImage(fileType, pathName + "-layer-" + layerCounter);
//      layerCounter++;
//    }
//  }

  @Override
  public void toggleInvisible(int layerIndex) throws IllegalArgumentException {
    if (layerIndex < 0 || layerIndex >= this.listOfLayers.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    this.currentLayer.toggleInvisible();
  }

  @Override
  public void setCurrentLayer(int layerIndex) throws IllegalArgumentException {
    if (layerIndex < 0 || layerIndex >= this.listOfLayers.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    this.currentLayer = this.listOfLayers.get(layerIndex);
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
    this.listOfLayers.remove(layerIndex);
  }

  @Override
  public void swapLayers(int layerIndex1, int layerIndex2) throws IllegalArgumentException {
    if (layerIndex1 < 0 || layerIndex1 >= this.listOfLayers.size()
        || layerIndex2 < 0 || layerIndex2 >= this.listOfLayers.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    ILayer first = this.listOfLayers.get(layerIndex1);
    ILayer second = this.listOfLayers.get(layerIndex2);
    this.listOfLayers.set(layerIndex1, second);
    this.listOfLayers.set(layerIndex2, first);
  }

  @Override
  public List<ILayer> getLayers() {
    // return a deep copy
    List<ILayer> layersCopy = new ArrayList<>();
    // Collections.copy(layerCopy, this.listOfLayers);
    for (ILayer lyr : this.listOfLayers) {
      layersCopy.add(lyr.copy());
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
    this.currentLayer.getModel().save();
  }

}