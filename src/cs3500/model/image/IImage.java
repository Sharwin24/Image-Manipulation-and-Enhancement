package cs3500.model.image;

import cs3500.model.channel.EChannelType;
import cs3500.model.matrix.IMatrix;
import cs3500.model.pixel.IPixel;

/**
 * An image to be manipulated or edited. Supports the operations to
 * extract a channel of some color, copy the image, and observer methods
 * to return the width and height
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

  /**
   * Gets the width or number of columns in the image.
   *
   * @return an integer representing the number of columns.
   */
  int getWidth();

  /**
   * Gets the height or number of rows in the image.
   *
   * @return an integer representing the number of rows.
   */
  int getHeight();

  /**
   * TODO: maybe move this
   */
  IImage mosaic(int numSeeds);

}