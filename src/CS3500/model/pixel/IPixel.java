package CS3500.model.pixel;

import CS3500.model.channel.EChannelType;

/**
 * To represent a pixel in an image. Supports the operations to...
 * <ul>
 *   <li>
 *     {@link IPixel#getIntensity(EChannelType)}: get the intensity of a channel--an integer between
 *     0 and 255--based on that channel's color.
 *   </li>
 * </ul>
 */
public interface IPixel {

  /**
   * Returns the intensity--as an double in the range [0,255]--of a channel of this {@link IPixel}
   * based on a {@link EChannelType} representing the color of that channel.
   *
   * @param type the color of the channel to get the intensity of--one of red, green, or blue.
   * @return the intensity of the specified channel in this pixel.
   * @throws IllegalArgumentException if the given {@code type} is {@code null}.
   */
  int getIntensity(EChannelType type)
      throws IllegalArgumentException;

  /**
   * Determines if the given object is equal to this IPixel.
   *
   * @param o the object to check.
   * @return a boolean whether the object is the same or not.
   */
  boolean equals(Object o);

  /**
   * Calculates the hashCode for this IPixel.
   *
   * @return an integer representing the hashCode.
   */
  int hashCode();

  /**
   * Gets a string for the textual representation of an IPixel.
   *
   * @return the string for this IPixel.
   */
  String toString();

}