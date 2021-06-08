package CS3500.model.image;

import CS3500.model.channel.EChannelType;
import CS3500.model.matrix.IAlignableMatrix;
import CS3500.model.matrix.IMatrix;

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
   * Returns a {@link IMatrix} consisting of the intensity of some {@link EChannelType}--the
   * channel color--of each pixel in this {@link IImage}.
   * @param channel the type of channel whose intensity should be returned in a Matrix
   * @return a {@link IMatrix} consisting of the intensity values of each pixel's specified channel.
   * @throws IllegalArgumentException if the supplied channel is {@code null}.
   */
<<<<<<< HEAD
  IAlignableMatrix<Integer> extractChannel(EChannelType channel)
=======
  IMatrix<Double> extractChannel(EChannelType channel)
>>>>>>> 2e392ac36d22a51c4c1265fd7c9f7cd6832aa24a
      throws IllegalArgumentException;

  /**
   * Todo
   * @return
   * @throws IllegalArgumentException
   */
  IMatrix<Double> getPixelArray() throws IllegalArgumentException;

  /**
   * Todo
   * @return
   */
  IImage copy();



}