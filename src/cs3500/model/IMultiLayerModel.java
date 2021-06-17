package cs3500.model;

import cs3500.model.fileformat.IFileFormat;
import cs3500.model.image.IImage;
import java.util.List;

/**
 * An interface for features a multi-layered model will implement. Offers functionality to apply
 * operations to separate layers.
 *
 * @param <Z> The implementation of an image.
 */
public interface IMultiLayerModel<Z> extends IStateTrackingIMEModel<Z> {

  // Capabilities:
  // Add a layer, and remove a specific layer.
  // Make a layer invisible
  // Set a layer as the current -> applies any edits to current layer only
  // exportAllLayers -> export every layer and export each layer as an image, AND create a txt to
  // store the locations of all the layer files

  /**
   * Imports a multi-layered image into this model's multiple layers.
   *
   * @param fileType the type of image file to import the layers as.
   * @param pathName the path to the text file with the list of layers to import in a file.
   * @throws IllegalArgumentException if the path is invalid
   */
  void importAllLayers(IFileFormat fileType, String pathName) throws IllegalArgumentException;

  /**
   * Export all the layers by adding all paths to a text file.
   *
   * @param fileType the type of image file to export the layers as.
   * @param pathName the path for the output file's name.
   * @throws IllegalArgumentException if the path is invalid.
   */
  void exportAllLayers(IFileFormat fileType, String pathName) throws IllegalArgumentException;

  /**
   * Toggles the layer at the given index to be invisible. Indexing start from zero and an index of
   * zero represents the bottom most layer.
   *
   * @param layerIndex the index of the layer.
   * @throws IllegalArgumentException if the layer index is out of bounds.
   */
  void toggleInvisible(int layerIndex) throws IllegalArgumentException;

  /**
   * Sets the current working layer to edit or import or export etc. Indexing start from zero and an
   * index of zero represents the bottom most layer.
   *
   * @param layerIndex the index of the layer to work with.
   * @throws IllegalArgumentException if the given layer index is out of bounds.
   */
  void setCurrentLayer(int layerIndex) throws IllegalArgumentException;

  /**
   * Adds a new layer to the end of this {@link IMultiLayerModel}'s {@link List} of layers.
   */
  void addLayer();

  /**
   * Deletes the layer at the given index in this {@link IMultiLayerModel}'s {@link List} of layers,
   * indexed from 0, left to right.
   *
   * @param layerIndex the layer to delete, indexed from 0, left to right.
   * @throws IllegalArgumentException if the supplied {@code layerIndex} does not lie in the
   *                                  inclusive range of integers {@code [0,size]}, where there are
   *                                  {@code size + 1} layers in this {@link IMultiLayerModel}.
   */
  void deleteLayer(int layerIndex)
      throws IllegalArgumentException;

  /**
   * Swaps the position of layers {@code layerIndex1} and {@code layerIndex2} in this {@link
   * IMultiLayerModel}'s {@link List} of layers.
   *
   * @param layerIndex1 the index of the first of the two layers of which to swap positions, indexed
   *                    from 0, from left to right.
   * @param layerIndex2 the index of the second of the two layers of which to swap positions,
   *                    indexed from 0, from left to right.
   * @throws IllegalArgumentException if either {@code layerIndex1} or {@code layerIndex2} does not
   *                                  lie in the inclusive range of integers {@code [0,size]}, where
   *                                  there are {@code size + 1} layers in this {@link
   *                                  IMultiLayerModel}.
   */
  void swapLayers(int layerIndex1, int layerIndex2)
      throws IllegalArgumentException;

  /**
   * Observer method to provide client-side access to the layers of which this {@link
   * IMultiLayerModel} consists.
   *
   * @return the layers in this {@link IMultiLayerModel}, as a {@link List}.
   */
  List<IStateTrackingIMEModel<IImage>> getLayers();

  class Layer {
    private boolean isVisible;
  }
}