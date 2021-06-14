package cs3500.model.channel;

import cs3500.Utils;
import cs3500.model.pixel.IPixel;
import java.util.Objects;

/**
 * <p>A {@link IChannel} of a {@link IPixel} represents the value, between 0 and 255 (inclusive)
 * of the amount of {@link EChannelType#RED}, {@link EChannelType#GREEN}, or {@link
 * EChannelType#BLUE} light that the pixel has. </p>
 * <title>
 * <b><u>INVARIANT</u></b>
 * </title>
 * <div><sub>Assumes an <b><i>invariant</i></b> that the intensity of this
 * {@link ChannelImpl} is an {@code int} in the range [0,255].</sub></div>
 */
public class ChannelImpl implements IChannel {

  private final EChannelType color;
  private int intensity; // INVARIANT: should be in the range [0, 255]


  /**
   * Creates a new {@link ChannelImpl} of some {@code color} (red, green, or blue), and some
   * intensity as an integer in the range [0, 255].
   * <p><b><i>INVARIANT:</i></b> to enforce the <b><i>invariant</i></b> outlined in
   * {@link ChannelImpl}'s JavaDoc, if an {@code intensity} is given that is not in the range
   * [0,255], the intensity defaults to whatever number of {0, 255} it is closest to./</p>
   *
   * @param color     the color of this channel--either {@link EChannelType#RED}, {@link
   *                  EChannelType#GREEN}, or {@link EChannelType#BLUE}.
   * @param intensity the intensity of the light as an {@code int} in the range [0,255]. If such an
   *                  {@code int} is not supplied, the intensity defaults to whatever number of {0,
   *                  255} it is closest to.
   * @throws IllegalArgumentException if the given {@code color} is {@code null}.
   */
  public ChannelImpl(EChannelType color, int intensity)
      throws IllegalArgumentException {
    this.color = Utils.checkNotNull(color, "cannot construct a ChannelImpl with a null color");
    this.intensity = Utils.setIntBetween(intensity, 0, 255);
  }


  @Override
  public int getIntensity() {
    return this.intensity;
  }

  @Override
  public String toString() {
    return this.color.toString() + this.getIntensity();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IChannel)) {
      return false;
    }

    IChannel otherChannel = (ChannelImpl) o;
    return this.getIntensity() == otherChannel.getIntensity();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.color, this.intensity);
  }
}