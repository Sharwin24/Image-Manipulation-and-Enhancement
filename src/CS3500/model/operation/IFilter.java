package CS3500.model.operation;

import CS3500.model.image.IImage;

/**
 * Todo: Javadocs
 */
public interface IFilter extends IOperation {

  /**
   * Applies the subclass' filter to all 3 channels.
   *
   * @param image the Image to apply the filter to.
   * @return the final IImage after 3 filters.
   * @throws IllegalArgumentException if the given image is null.
   */
  IImage applyFilterToAllChannels(IImage image) throws IllegalArgumentException;

}