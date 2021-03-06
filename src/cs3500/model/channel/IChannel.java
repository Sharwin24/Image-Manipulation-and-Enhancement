package cs3500.model.channel;

import cs3500.model.pixel.IPixel;

/**
 * <p>A {@link IChannel} of a {@link IPixel} represents the value, between 0 and 255 (inclusive)
 * of the amount of {@link EChannelType#RED}, {@link EChannelType#GREEN}, or {@link
 * EChannelType#BLUE} light that the pixel has. </p>
 */
public interface IChannel {

  /**
   * Returns the intensity of the light of this Channel, as an int in the range [0,255]
   * (inclusive).
   *
   * @return the intensity of the light of this Channel, as an int in the range [0,255] (inclusive).
   */
  int getIntensity();

  /**
   * Determines if the given object is the same channel or not.
   *
   * @param o the object to check.
   * @return a boolean whether the given object is the same channel or not.
   */
  boolean equals(Object o);

  /**
   * Hashes the Channel.
   *
   * @return an integer representing the hash.
   */
  int hashCode();

  /**
   * Gets the textual representation of the channel.
   *
   * @return a String for the textual representation.
   */
  String toString();
}
