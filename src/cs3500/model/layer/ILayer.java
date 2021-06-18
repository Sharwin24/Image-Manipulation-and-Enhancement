package cs3500.model.layer;

import cs3500.model.IMultiLayerModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.fileformat.IFileFormat;

/**
 * An interface to represent a Layer within a Multi-layered
 *
 * @param <T> the implementation of an image.
 */
public interface ILayer<T> {

  /**
<<<<<<< HEAD
   * TODO
   * @return
=======
   * Determines if this layer is invisible or not.
   *
   * @return a boolean if the layer is invisible or not.
>>>>>>> 7c0d86e8bcc03718cc71ece910691bfa8487b4e5
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
  void importImage(IFileFormat<T> format, String filePath) throws IllegalArgumentException;

  /**
   * Gets the model for this layer for delegation.
   *
   * @return a {@link IStateTrackingIMEModel} that this layer utilizes to track state
   */
  IStateTrackingIMEModel<T> getModel();

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