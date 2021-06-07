package CS3500.model;

import CS3500.Utils;
import java.util.List;

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

  /**
   * TODO
   *
   * @param pixelRows
   * @throws IllegalArgumentException
   */
  public ImageImpl(List<IPixel>... pixelRows)
      throws IllegalArgumentException {
    this.pixels = new AMatrix<>(Utils.checkNotNull(pixelRows,
        "cannot construct an Image with null pixel rows"));
  }


  @Override
  public IMatrix<Integer> extractChannel(EChannelType channel) throws IllegalArgumentException {
    IMatrix<IPixel> toMap = this.pixels.copy();
    return toMap.map(x -> x.getIntensity(channel));
  }
}
