package CS3500.model.image;

import CS3500.Utils;
import CS3500.model.channel.EChannelType;
import CS3500.model.matrix.IMatrix;
import CS3500.model.pixel.IPixel;

// TODO: might want to just have this extend AMatrix<IPixel>. Issue is that this can never return a
// concrete IMatrix subtype without access to factory method. Constructors also become super easy

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


  @Override
  public IMatrix<Double> extractChannel(EChannelType channel) throws IllegalArgumentException {
    IMatrix<IPixel> toMap = this.pixels.copy();
    return toMap.map(x -> x.getIntensity(channel));
  }

  @Override
  public IMatrix<IPixel> getPixelArray() throws IllegalArgumentException {
    return this.pixels;
  }

  @Override
  public IImage copy() {
    IMatrix<IPixel> pixelMatrixCopy;
    pixelMatrixCopy = this.pixels.copy();
    return new ImageImpl(pixelMatrixCopy);
  }
}