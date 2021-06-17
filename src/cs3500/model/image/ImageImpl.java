package cs3500.model.image;

import cs3500.Utils;
import cs3500.model.channel.EChannelType;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import java.util.List;
import java.util.Objects;

/**
 * An image, represented by a {@link IMatrix} of pixels.
 */
public class ImageImpl implements IImage {

  private final IMatrix<IPixel> pixels;

  /**
   * Creates a new {@link ImageImpl} based on the {@code pixels} that comprise it.
   *
   * @param pixels an {@link IMatrix} of {@link IPixel}s comprising the image.
   * @throws IllegalArgumentException if the given {@link IMatrix} is {@code null}.
   */
  public ImageImpl(IMatrix<IPixel> pixels)
      throws IllegalArgumentException {
    this.pixels = Utils.checkNotNull(pixels, "cannot construct an Image with a "
        + "null matrix of pixels");
  }

  /**
   * Constructs an Image object when given a List of Lists to represent a matrix.
   *
   * @param pixels the 2D ArrayList representing the Matrix.
   * @throws IllegalArgumentException if the given array is null.
   */
  public ImageImpl(List<List<IPixel>> pixels)
      throws IllegalArgumentException {
    this(new MatrixImpl<>(pixels));
  }


  @Override
  public IMatrix<Integer> extractChannel(EChannelType channel) throws IllegalArgumentException {
    IMatrix<IPixel> toMap = this.pixels.copy();
    return toMap.map(x -> x.getIntensity(channel));
  }

  @Override
  public IMatrix<IPixel> getPixelArray() {
    return this.pixels;
  }

  @Override
  public IImage copy() {
    IMatrix<IPixel> pixelMatrixCopy;
    pixelMatrixCopy = this.pixels.copy();
    return new ImageImpl(pixelMatrixCopy);
  }

  @Override
  public int getWidth() {
    return this.pixels.getWidth();
  }

  @Override
  public int getHeight() {
    return this.pixels.getHeight();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IImage)) {
      return false;
    }
    IImage otherImage = (ImageImpl) o;
    return this.pixels.equals(otherImage.getPixelArray());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pixels);
  }
}