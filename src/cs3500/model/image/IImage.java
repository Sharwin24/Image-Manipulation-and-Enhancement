package cs3500.model.image;

import cs3500.model.channel.EChannelType;
import cs3500.model.matrix.IMatrix;
import cs3500.model.pixel.IPixel;

/**
 * An image to be manipulated or edited. Supports the operations to...
 * <ul>
 *   <li>
 *     {@link IImage#extractChannel(EChannelType)}: extract all of the light intensity values
 *     of some {@link EChannelType} in the image.
 *   </li>
 * </ul>
 */
public interface IImage {

  /**
   * Returns a {@link IMatrix} consisting of the intensity of some {@link EChannelType}--the channel
   * color--of each pixel in this {@link IImage}.
   *
   * @param channel the type of channel whose intensity should be returned in a Matrix
   * @return a {@link IMatrix} consisting of the intensity values of each pixel's specified channel.
   * @throws IllegalArgumentException if the supplied channel is {@code null}.
   */
  IMatrix<Integer> extractChannel(EChannelType channel)
      throws IllegalArgumentException;

  /**
   * Gets the Matrix of Pixels that Create this IImage.
   *
   * @return the {@link IMatrix} that represents the matrix of pixels.
   */
  IMatrix<IPixel> getPixelArray();

  /**
   * Returns a copy of this IImage.
   *
   * @return an {@link IImage} as a copy.
   */
  IImage copy();


}