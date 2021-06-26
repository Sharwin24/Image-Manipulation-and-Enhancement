package cs3500.model.pixel;

import cs3500.Utility;

/**
 * Class for a {@link IPixel} with a coordinate.
 */
public class IndexedPixel extends PixelImpl {

  private final int row;
  private final int col;
  private final IPixel px;

  /**
   * Constructs an Indexed pixel given RGB, row, column, and the IPixel.
   *
   * @param r the red channel's intensity.
   * @param g the green channel's intensity.
   * @param b the blue channel's intensity.
   * @param row the row of the pixel.
   * @param col the column of the pixel.
   * @param px the IPixel.
   */
  public IndexedPixel(int r, int g, int b, int row, int col, IPixel px) {
    super(r,g,b);
    this.row = Utility.checkIntBetween(row, 0, Integer.MAX_VALUE);
    this.col = Utility.checkIntBetween(col, 0, Integer.MAX_VALUE);
    this.px = Utility.checkNotNull(px, "cannot create an indexed pixel with a null "
        + "pixel value");
  }

  /**
   * Returns the Euclidean distance between the given position and this pixel.
   *
   * @param row the row position to find the distance to.
   * @param col the col position to find the distance to.
   * @return the distance as a double.
   */
  public double distanceTo(int row, int col) {
    return Math.sqrt(Math.pow(row - this.row, 2) + Math.pow(col - this.col, 2));
  }
}