package CS3500.model.operation;

import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.matrix.Kernel;

public class Greyscale extends AColorTransform {

  @Override
  public void apply(IImage image, Kernel kernel, EChannelType... channelTypes)
      throws IllegalArgumentException {

  }
}