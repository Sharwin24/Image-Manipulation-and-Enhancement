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
 * Todo: JavaDocs
 */
public class MultiLayerModelImpl implements IMultiLayerModel<IImage> {

  // Represent Layers
  // Layer: A stateTracking model to track and edit one image in one layer.
  // Current Working Layer: represents the current working layer
  private final List<IStateTrackingIMEModel> layersList;
  private final IStateTrackingIMEModel currentLayer;
  private final HashMap<IStateTrackingIMEModel, Boolean> invisibleLayers;

  private final Readable rd;
  private final Appendable ap;

  /**
   * Todo: Constructor
   */
  public MultiLayerModelImpl() {
    this.layersList = new ArrayList<>();
    this.currentLayer = new StateTrackingIMEModelImpl();
    this.rd = new InputStreamReader(System.in);
    this.ap = System.out;
    this.invisibleLayers = new HashMap<>();
  }


  @Override
  public void applyOperations(IOperation... operations) throws IllegalArgumentException {

  }

  @Override
  public void importImage(IFileFormat format, String fileName) throws IllegalArgumentException {

  }

  @Override
  public void exportImage(IFileFormat format, String fileName) throws IllegalArgumentException {

  }

  @Override
  public void setProgrammaticImage(IProgramImage imgToSet, int widthPx, int heightPx,
      int unitSizePx) throws IllegalArgumentException {

  }

  @Override
  public void importAllLayers(IFileFormat fileType, String pathName) throws IllegalArgumentException {

  }

  @Override
  public void exportAllLayers(IFileFormat fileType, String pathName) throws IllegalArgumentException {

  }

  /**
   * Determines if the layer at the given layer index is invisible or not. Indexing start from zero
   * and an index of zero represents the bottom most layer.
   *
   * @param layerIndex the index of the layer.
   * @return a boolean whether the specified layer is invisible or not.
   * @throws IllegalArgumentException if the given layer index is out of bounds.
   */
  protected boolean isLayerInvisible(int layerIndex) throws IllegalArgumentException {
    return false; // TODO
  }

  @Override
  public void toggleInvisible(int layerIndex) throws IllegalArgumentException {

  }

  @Override
  public void setCurrentLayer(int layerIndex) throws IllegalArgumentException {

  }

  @Override
  public void addLayer() {

  }

  @Override
  public void deleteLayer(int layerIndex) throws IllegalArgumentException {

  }

  @Override
  public void swapLayers(int layerIndex1, int layerIndex2) throws IllegalArgumentException {

  }

  @Override
  public List<IStateTrackingIMEModel> getLayers() {
    return null;
  }

  @Override
  public void undo() throws IllegalArgumentException {
    currentLayer.undo();
  }

  @Override
  public void redo() throws IllegalArgumentException {
    currentLayer.redo();
  }

  @Override
  public void save() {

  }

  @Override
  public IImage retrieve() {
    return null;
  }
}