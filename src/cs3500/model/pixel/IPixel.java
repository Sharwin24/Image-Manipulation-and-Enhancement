package cs3500.model.pixel;

import cs3500.model.channel.EChannelType;

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
   * Determines if the given object is equal to some {@link Object} {@code o}.
   *
   * @param o the object to check for equality with this {@link IPixel}.
   * @return whether the object is the same or not.
   */
  boolean equals(Object o);

  /**
   * Calculates the unique hashCode for this IPixel based on its contents (see implementations).
   *
   * @return an integer representing the hashCode.
   */
  int hashCode();

  /**
   * Returns a textual representation of this {@link IPixel} in the form {@code R G B}, where
   * {@code R} represents the red channel value of this pixel,
   * {@code G} represents the red channel value of this pixel,
   * and {@code B} represents the red channel value of this pixel.
   *
   * @return the string for this IPixel as described above.
   */
  String toString();

}