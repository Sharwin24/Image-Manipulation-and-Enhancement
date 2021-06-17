package cs3500.model.layer;

import cs3500.model.IMultiLayerModel;

/**
 * An interface to represent a Layer within a Multi-layered
 *
 * @param <T> the implementation of an image.
 */
public interface ILayer<T> {

  boolean isInvisible();

  void importImage(T image);

  IMultiLayerModel<T> getModel();

  @Override
  String toString();
}