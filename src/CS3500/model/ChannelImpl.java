package CS3500.model;

import CS3500.Utils;

/**
 * TODO: Finish javaDocs
 */
public class ChannelImpl implements IChannel {

  private final EChannelType color;
  private int intensity; // INVARIANT: should be in the range [0, 255]


  /**
   * TODO
   *
   * @param color
   * @param intensity
   * @throws IllegalArgumentException
   */
  public ChannelImpl(EChannelType color, int intensity)
      throws IllegalArgumentException {
    this.color = Utils.checkNotNull(color, "cannot construt a ChannelImpl with a null "
        + "color");
    this.intensity = Utils.checkIntBetween(intensity, 0, 255);
  }


  @Override
  public int getIntensity() { // Right here
    return this.intensity;
  }
}