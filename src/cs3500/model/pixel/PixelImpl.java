package cs3500.model.pixel;

import cs3500.Utils;
import cs3500.model.channel.ChannelImpl;
import cs3500.model.channel.EChannelType;
import cs3500.model.channel.IChannel;
import cs3500.model.matrix.IMatrix;
import java.util.Objects;

/**
 * A representation of a Pixel which...
 * <ul>
 *   <li>
 *     Assumes that the pixel has three channels, representing the red, green, and blue color values
 *     of that pixel.
 *   </li>
 *   <li>
 *     Assumes that the stated channels have a light intensity represented as an 8-bit integer, in
 *     this case represented as its decimal equivalent, in the inclusive range of integers 0-255.
 *   </li>
 *   <li>
 *     Also provides some {@code public static final} constants representing predetermined pixels to
 *     be used for convenience.
 *   </li>
 * </ul>
 */
public class PixelImpl implements IPixel {

  private IChannel red;
  private IChannel green;
  private IChannel blue;

  // Constants representing a pixel of a given color to be used for convenience.
  public static final PixelImpl RED = new PixelImpl(255, 0, 0);
  public static final PixelImpl GREEN = new PixelImpl(0, 255, 0);
  public static final PixelImpl BLUE = new PixelImpl(0, 0, 255);
  public static final PixelImpl YELLOW = new PixelImpl(255, 255, 0);
  public static final PixelImpl MAGENTA = new PixelImpl(255, 0, 255);
  public static final PixelImpl CYAN = new PixelImpl(0, 255, 255);
  public static final PixelImpl ORANGE = new PixelImpl(255, 128, 0);
  public static final PixelImpl INDIGO = new PixelImpl(29, 0, 51);
  public static final PixelImpl VIOLET = new PixelImpl(127, 0, 255);
  public static final PixelImpl BLACK = new PixelImpl(0, 0, 0);
  public static final PixelImpl WHITE = new PixelImpl(255, 255, 255);

  /**
   * <p>Creates a new {@link PixelImpl} given the intensity of its
   * {@link EChannelType#RED}, {@link EChannelType#GREEN}, and {@link EChannelType#BLUE} channels,
   * and enforces the following.
   * <b><i>invariant:.</i></b></p>
   * <title><b><i>INVARIANT.</i></b></title>
   * <div><sub>A {@link PixelImpl}'s intensity of its
   * {@link EChannelType#RED}, {@link EChannelType#GREEN}, and
   * {@link EChannelType#BLUE} channels is always an integer in the range [0,255].
   * Therefore this constructor sets any integer given that falls outside of that range
   * to the closest number in that range. This easily promotes 'clamping' when editing an image
   * causes one of the channels' light intensity to extend out of bounds of [0, 255].
   * </sub></div>
   *
   * @param r the light intensity of this {@link PixelImpl}'s {@link EChannelType#RED} channel.
   * @param g the light intensity of this {@link PixelImpl}'s {@link EChannelType#GREEN} channel.
   * @param b the light intensity of this {@link PixelImpl}'s {@link EChannelType#BLUE} channel.
   */
  public PixelImpl(int r, int g, int b) {
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
    return this.getIntensity(EChannelType.RED)
        + " "
        + this.getIntensity(EChannelType.GREEN)
        + " "
        + this.getIntensity(EChannelType.BLUE);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IPixel)) {
      return false;
    }

    IPixel otherPixel = (IPixel) o;
    boolean red = this.red.getIntensity() == otherPixel.getIntensity(EChannelType.RED);
    boolean green = this.green.getIntensity() == otherPixel.getIntensity(EChannelType.GREEN);
    boolean blue = this.blue.getIntensity() == otherPixel.getIntensity(EChannelType.BLUE);
    return red && green && blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

}