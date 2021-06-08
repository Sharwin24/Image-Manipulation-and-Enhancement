package CS3500.model.operation;

import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.matrix.Kernel;
import javax.imageio.IIOImage;

/**
 * An interface to represent an operation to be applied to an {@link IImage}
 */
public interface IOperation {
  // Operations are applied to an IImage,
  // apply function(IImage, Kernel)
  // use IImage.getPixelArray to get the pixel array.
  // Use IMatrix functions to apply the kernel to the pixelArray
  // Each IOperation has a unique kernel

  void apply(IImage image, Kernel kernel, EChannelType... channelTypes)
      throws IllegalArgumentException;
}