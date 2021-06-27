package cs3500.model;

import cs3500.model.image.IImage;
import cs3500.model.layer.ILayer;
import cs3500.model.operation.IOperation;
import cs3500.model.programmaticimages.IProgramImage;
import java.util.List;

/**
 * A mock model to test inputs from the controller.
 */
public class MockMultiLayerModel implements IMockMultiLayerModel {

  private final StringBuilder log;

  public MockMultiLayerModel() {
    this.log = new StringBuilder();
  }

  @Override
  public void applyOperations(IOperation... operations) throws IllegalArgumentException {

    this.log.append("applied operations " + operations.toString() + "\n");

  }

  @Override
  public void load(IImage image) throws IllegalArgumentException {
    this.log.append("loaded image " + image.toString() + "\n");
  }

  @Override
  public void setProgrammaticImage(IProgramImage imgToSet, int widthPx, int heightPx,
      int unitSizePx) throws IllegalArgumentException {
    this.log.append("set programmatic image of a " + imgToSet.toString() + " with"
        + " width=" + widthPx + ", height=" + heightPx + " and unit size=" + unitSizePx + "\n");
  }

  @Override
  public IImage getImage() {
    return null; // dummy method from the controller perspective
  }

  @Override
  public IIMEModel copy() {
    return null; // dummy method from the controller perspective
  }

  @Override
  public String getLog() {
    return this.log.toString();
  }

  @Override
  public void toggleInvisible(int layerIndex) throws IllegalArgumentException {
    log.append("toggled visibility of layer " + layerIndex + "\n");
  }

  @Override
  public void setCurrentLayer(int layerIndex) throws IllegalArgumentException {
    log.append("set current layer to index " + layerIndex + "\n");
  }

  @Override
  public void addLayer() {
    log.append("added a new layer" + "\n");
  }

  @Override
  public void deleteLayer(int layerIndex) throws IllegalArgumentException {
    log.append("deleted layer at index " + layerIndex + "\n");
  }

  @Override
  public void swapLayers(int layerIndex1, int layerIndex2) throws IllegalArgumentException {
    log.append("swapped layers at indices " + layerIndex1 + " and " + layerIndex2 + "\n");
  }

  @Override
  public List<ILayer> getLayers() {
    return null; // dummy method from the controller's perspective
  }

  @Override
  public void mosaic(int seeds) {
    log.append("mosaiced with " + seeds + " seeds");
  }

  @Override
  public void downscaleLayers(int newHeight, int newWidth) throws IllegalArgumentException {
    log.append("Downscaled with H:" + newHeight + " and W:" + newWidth);
  }

  @Override
  public void undo() throws IllegalArgumentException {
    log.append("undone" + "\n");
  }

  @Override
  public void redo() throws IllegalArgumentException {
    log.append("redone" + "\n");
  }

  @Override
  public void save() {
    log.append("saved current image to history" + "\n");
  }
}