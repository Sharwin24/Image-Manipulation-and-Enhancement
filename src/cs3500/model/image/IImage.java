package cs3500.model.image;

import cs3500.model.channel.EChannelType;
import cs3500.model.matrix.IMatrix;
import cs3500.model.pixel.IPixel;
import java.awt.image.BufferedImage;

/**
 * An image to be manipulated or edited. Supports the operations to extract a channel of some color,
 * copy the image, and observer methods to return the width and height
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
   * Generates a Mosaic of this image with the given number of seeds.
   * @param numSeeds the number of seeds to generate the Mosaic image.
   */
  IImage mosaic(int numSeeds);

  /**
   * Downscales the model's image to the new height and new width.
   * @param newHeight the new height of the downscaled image.
   * @param newWidth the new width of the downscaled image.
   * @throws IllegalArgumentException if the new size is larger or the same as the original.
   */
  IImage downscale(int newHeight, int newWidth);

  /**
   * Returns this image as a {@link BufferedImage}.
   *
   * @return a {@link BufferedImage} representing this image.
   * @throws IllegalArgumentException if the image is empty.
   */
  BufferedImage getBufferedImage() throws IllegalArgumentException;

}