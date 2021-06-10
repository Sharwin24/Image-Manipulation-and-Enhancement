package cs3500.model.operation;

import cs3500.model.image.IImage;

/**
 * An interface to represent a Filter {@link IOperation} that can be applied to an {@link IImage}.
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