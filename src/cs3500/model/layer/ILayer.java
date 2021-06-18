package cs3500.model.layer;

import cs3500.model.IMultiLayerModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.fileformat.IFileFormat;

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
   * Imports an image to this layer given the file format and the path. Saves the name of the file
   * locally to store local data on the imported image.
   *
   * @param format   the type of file to import.
   * @param filePath the path to the file to import.
   * @throws IllegalArgumentException if either arguments are null.
   */
  void importImage(IFileFormat format, String filePath) throws IllegalArgumentException;

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
}