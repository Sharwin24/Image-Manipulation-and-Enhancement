package CS3500.model.pixel;

import CS3500.Utils;
import CS3500.model.channel.ChannelImpl;
import CS3500.model.channel.EChannelType;
import CS3500.model.channel.IChannel;

/**
 * A representation of a Pixel with...
 * <ul>
 *   <li>
 *     Assumes that the pixel has three channels, representing the red, green, and blue color values
 *     of that pixel.
 *   </li>
 *   <li>
 *     Assumes that the stated channels have a light intensity represented as an 8-bit integer, in
 *     this case represented as its decimal equivalent, in the range of integers 0-255.
 *   </li>
 * </ul>
 */
public class PixelImpl implements IPixel {

  private IChannel red;
  private IChannel green;
  private IChannel blue;

  /**
   * <p>Creates a new {@link PixelImpl} given the intensity of its
   * {@link EChannelType#RED}, {@link EChannelType#GREEN}, and
   * {@link EChannelType#BLUE} channels, and enforces the following
   * <b><i>invariant:</i></b></p>
   * <title><b><i>INVARIANT</i></b></title>
   * <div><sub>A {@link PixelImpl}'s intensity of its
   * {@link EChannelType#RED}, {@link EChannelType#GREEN}, and
   * {@link EChannelType#BLUE} channels is always an integer in the range [0,255].
   * Therefore this constructor sets any integer given that falls outside of that range
   * to the closest number in that range.
   * </sub></div>
   *
   * @param r the light intensity of this {@link PixelImpl}'s {@link EChannelType#RED} channel.
   * @param g the light intensity of this {@link PixelImpl}'s {@link EChannelType#GREEN} channel.
   * @param b the light intensity of this {@link PixelImpl}'s {@link EChannelType#BLUE} channel.
   */
  public PixelImpl(int r, int g, int b)
      throws IllegalArgumentException {
    this.red = new ChannelImpl(EChannelType.RED,
        Utils.setIntBetween(r, 0, 255));
    this.green = new ChannelImpl(EChannelType.GREEN,
        Utils.setIntBetween(g, 0, 255));
    this.blue = new ChannelImpl(EChannelType.BLUE, 
        Utils.setIntBetween(b, 0, 255));
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