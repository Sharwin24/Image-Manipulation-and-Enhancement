package CS3500.model.pixel;

import CS3500.Utils;
import CS3500.model.channel.ChannelImpl;
import CS3500.model.channel.EChannelType;
import CS3500.model.channel.IChannel;

public class PixelImpl implements IPixel {

  private IChannel red;
  private IChannel green;
  private IChannel blue;

  /**
   * TODO INVARIANT**
   *
   * @param r
   * @param g
   * @param b
   * @throws IllegalArgumentException
   */
  public PixelImpl(int r, int g, int b)
      throws IllegalArgumentException {
    this.red = new ChannelImpl(EChannelType.RED, Utils.checkIntBetween(r, 0, 255));
    this.green = new ChannelImpl(EChannelType.GREEN, Utils.checkIntBetween(g, 0, 255));
    this.blue = new ChannelImpl(EChannelType.BLUE, Utils.checkIntBetween(b, 0, 255));
  }

  @Override
  public int getIntensity(EChannelType type)
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

  @Override
  public String toString() {
    return "(" + this.red.toString() + ", " +
        this.green.toString() + ", "
        + this.blue.toString() + ")";
  }

}