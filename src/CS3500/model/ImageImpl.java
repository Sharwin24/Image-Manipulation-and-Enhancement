package CS3500.model;

import CS3500.Utils;
import java.util.List;

public class ImageImpl implements IImage {
  private final IMatrix<IPixel> pixels;

  /**
   * TODO
   *
   * @param pixels
   * @throws IllegalArgumentException
   */
  public ImageImpl(IMatrix pixels)
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
