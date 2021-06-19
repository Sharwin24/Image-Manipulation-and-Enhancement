package cs3500.model.layer;

import cs3500.model.IIMEModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.image.IImage;

/**
 * An interface to represent a Layer within a Multi-layered
 */
public interface ILayer {

  /**
   * Determines if this layer is invisible or not.
   *
   * @return a boolean if the layer is invisible or not.
   */
  boolean isInvisible();

  /**
   * Toggles the invisibility of this layer.
   */
  void toggleInvisible();

  /**
   * Gets the model for this layer for delegation.
   *
   * @return a {@link IStateTrackingIMEModel} that this layer utilizes to track state
   */
  IStateTrackingIMEModel getModel();

  /**
   * Gets the height of this layer.
   *
   * @return an integer representing the height.
   */
  int getLayerHeight();

  /**
   * Gets the width of this layer.
   *
   * @return an integer representing the width.
   */
  int getLayerWidth();

  @Override
  String toString();

  /**
   * Todo
   *
   * @return
   */
  ILayer copy();
}