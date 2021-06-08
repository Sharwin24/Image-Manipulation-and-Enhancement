package CS3500.model.pixel;

import CS3500.Utils;
import CS3500.model.channel.EChannelType;
import CS3500.model.channel.IChannel;

public class PixelImpl implements IPixel {
  private IChannel red;
  private IChannel green;
  private IChannel blue;


  @Override
  public double getIntensity(EChannelType type)
      throws IllegalArgumentException {
    Utils.checkNotNull(type, "cannot query an intensity value of a null channel type");

    switch (type) {
      case RED:
        return this.red.getIntensity();
      case GREEN:
        return this.green.getIntensity();
      case BLUE:
        return this.blue.getIntensity();
      default:
        throw new IllegalArgumentException("unsupported channel type, you broke Java, wow");
    }
  }


}