package cs3500.model.pixel;

import cs3500.Utility;

/**
 * TODO
 */
public class IndexedPixel extends PixelImpl {

  private final int row;
  private final int col;
  private final IPixel px;

  /**
   * TODO
   *
   * @param r
   * @param g
   * @param b
   * @param row
   * @param col
   * @param px
   */
  public IndexedPixel(int r, int g, int b, int row, int col, IPixel px) {
    super(r,g,b);
    this.row = Utility.checkIntBetween(row, 0, Integer.MAX_VALUE);
    this.col = Utility.checkIntBetween(col, 0, Integer.MAX_VALUE);
    this.px = Utility.checkNotNull(px, "cannot create an indexed pixel with a null "
        + "pixel value");
  }

  /**
   * TODO
   *
   * @param row
   * @param col
   * @return
   */
  public double distanceTo(int row, int col) {
    return Math.sqrt(Math.pow(row - this.row, 2) + Math.pow(col - this.col, 2));
  }
}