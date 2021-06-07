package CS3500.model;

/**
 * <p>A {@link IChannel} of a {@link IPixel} represents the value, between 0 and 255 (inclusive)
 * of the amount of {@link EChannelType#RED}, {@link EChannelType#GREEN}, or {@link
 * EChannelType#BLUE} light that the pixel has. </p>
 */
public interface IChannel /*extends IPair<EChannelType, Integer>*/ {

  /**
   * Returns the intensity of the light of this Channel, as an integer in the range [0,255]
   * (inclusive).
   *
   * @return the intensity of the light of this Channel, as an integer in the range [0,255]
   *         (inclusive).
   */
  int getIntensity();
}
