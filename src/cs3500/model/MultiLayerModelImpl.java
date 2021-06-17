package cs3500.model;

import cs3500.model.fileformat.IFileFormat;
import cs3500.model.image.IImage;
import cs3500.model.operation.IOperation;
import cs3500.model.programmaticimages.IProgramImage;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class to represent the Model for an Image processor with multiple layer. Offers functionality
 * to apply operations to different layers and to import/export separate layers.
 */
public class MultiLayerModelImpl implements IMultiLayerModel<IImage> {

  // Represent Layers
  // Layer: A stateTracking model to track and edit one image in one layer.
  // Current Working Layer: represents the current working layer
  private final List<IStateTrackingIMEModel<IImage>> layersList; // layer 0 is bottom
  private IStateTrackingIMEModel<IImage> currentLayer; // current working layer
  private final HashMap<Integer, Boolean> invisibleLayers; // <LayerIndex, Invisibility>

  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructs a Multi-Layer model with defaults for the class fields. Initializes lists as empty,
   * and the default appendable as {@code System.out} and default readable as {@code System.in}.
   */
  public MultiLayerModelImpl() {
    this.layersList = new ArrayList<>();
    this.layersList.add(new StateTrackingIMEModelImpl());
    this.currentLayer = this.layersList.get(0);
    this.rd = new InputStreamReader(System.in);
    this.ap = System.out;
    this.invisibleLayers = new HashMap<>();
  }

  @Override
  public void applyOperations(IOperation... operations) throws IllegalArgumentException {
    this.currentLayer.applyOperations(operations);
  }

  @Override
  public void importImage(IFileFormat format, String fileName) throws IllegalArgumentException {
    this.currentLayer.importImage(format, fileName);
  }

  @Override
  public void exportImage(IFileFormat format, String fileName) throws IllegalArgumentException {
    this.currentLayer.exportImage(format, fileName);
  }

  @Override
  public void setProgrammaticImage(IProgramImage imgToSet, int widthPx, int heightPx,
      int unitSizePx) throws IllegalArgumentException {
    this.currentLayer.setProgrammaticImage(imgToSet, widthPx, heightPx, unitSizePx);
  }

  @Override
  public void importAllLayers(IFileFormat fileType, String pathName)
      throws IllegalArgumentException {
    // Todo
  }

  @Override
  public void exportAllLayers(IFileFormat fileType, String pathName)
      throws IllegalArgumentException {
    int layerCtr = 0;
    for (IStateTrackingIMEModel m : layersList) {
      m.exportImage(fileType, pathName + "-layer-" + layerCtr);
      layerCtr++;
    }
  }

  @Override
  public void toggleInvisible(int layerIndex) throws IllegalArgumentException {
    if (layerIndex < 0 || layerIndex >= this.layersList.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    boolean currentInvisibility = this.invisibleLayers.get(layerIndex);
    this.invisibleLayers.put(layerIndex, !currentInvisibility);
  }

  @Override
  public void setCurrentLayer(int layerIndex) throws IllegalArgumentException {
    if (layerIndex < 0 || layerIndex >= this.layersList.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    this.currentLayer = this.layersList.get(layerIndex);
  }

  @Override
  public void addLayer() {
    this.layersList.add(new StateTrackingIMEModelImpl());
    this.invisibleLayers.put(this.layersList.size() - 1, false);
  }

  @Override
  public void deleteLayer(int layerIndex) throws IllegalArgumentException {
    if (layerIndex < 0 || layerIndex >= this.layersList.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    this.layersList.remove(layerIndex);
    this.invisibleLayers.remove(layerIndex);
  }

  @Override
  public void swapLayers(int layerIndex1, int layerIndex2) throws IllegalArgumentException {
    if (layerIndex1 < 0 || layerIndex1 >= this.layersList.size()
        || layerIndex2 < 0 || layerIndex2 >= this.layersList.size()) {
      throw new IllegalArgumentException("Layer Index out of bounds");
    }
    IStateTrackingIMEModel<IImage> first = this.layersList.get(layerIndex1);
    IStateTrackingIMEModel<IImage> second = this.layersList.get(layerIndex2);
    this.layersList.set(layerIndex2, first);
    this.layersList.set(layerIndex1, second);
  }

  @Override
  public List<IStateTrackingIMEModel<IImage>> getLayers() {
    // return a deep copy
    List<IStateTrackingIMEModel<IImage>> copyOfLayersList = new ArrayList<>();
    for (IStateTrackingIMEModel<IImage> m : this.layersList) {
      copyOfLayersList.add(m);
    }
    return copyOfLayersList;
  }

  @Override
  public void undo() throws IllegalArgumentException {
    this.currentLayer.undo();
  }

  @Override
  public void redo() throws IllegalArgumentException {
    this.currentLayer.redo();
  }

  @Override
  public void save() {
    this.currentLayer.save();
  }

  @Override
  public IImage retrieve() {
    return this.currentLayer.retrieve();
  }
}