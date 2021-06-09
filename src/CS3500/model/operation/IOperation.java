package CS3500.model.operation;

import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import javax.imageio.IIOImage;

/**
 * An interface to represent an operation to be applied to an {@link IImage}
 */
public interface IOperation {
// When new Operations get added, they can extend this interface.
  public IImage apply(IImage applyTo);
}