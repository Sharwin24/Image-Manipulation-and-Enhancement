package cs3500.model;

import cs3500.model.image.IImage;
import java.util.List;

/**
 * @param <Z>
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
   * @param layers the list of layers to import
   * @throws IllegalArgumentException
   */
  void importAllLayers(List<Z> layers) throws IllegalArgumentException;

  /**
   * @param layerLocations
   * @throws IllegalArgumentException
   */
  void exportAllLayers(String layerLocations) throws IllegalArgumentException;

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

}