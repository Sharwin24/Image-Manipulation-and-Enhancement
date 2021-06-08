package CS3500.model.operation;

import CS3500.ImageUtil;
import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.matrix.Kernel;

/**
 * Todo:
 */
public abstract class AColorTransform implements IOperation {

  @Override
  public abstract void apply(IImage image, Kernel kernel, EChannelType... channelTypes)
      throws IllegalArgumentException;

  /**
   * Todo:
   * @param image
   * @param kernel
   * @param channelTypes
   * @throws IllegalArgumentException
   */
  protected void applyHelper(IImage image, Kernel kernel, EChannelType... channelTypes)
      throws IllegalArgumentException {

  }
}