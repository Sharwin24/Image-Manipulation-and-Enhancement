package cs3500.model.image;

import cs3500.Utility;
import cs3500.model.channel.EChannelType;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
    this.pixels = Utility.checkNotNull(pixels, "cannot construct an Image with a "
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

  @Override
  public IImage mosaic(int numSeeds) {
    Utility.checkIntBetween(numSeeds, 0, this.getHeight()
        * this.getWidth());

    List<IndexedPixel> seeds = new ArrayList<>();
    Random r = new Random();

    for (int i = 0; i < numSeeds; i++) {
      int col = r.nextInt(this.getHeight());
      int row = r.nextInt(this.getWidth());

      seeds.add(new IndexedPixel(col, row, this.pixels.getElement(col, row)));
    }

    List<List<IPixel>> newPixels = new ArrayList<>();
    for (int i = 0; i < this.getHeight(); i++) {
      List<IPixel> thisRow = new ArrayList<>();
      for (int j = 0; j < this.getWidth(); j++) {
        IPixel pxToMosaic = this.pixels.getElement(i, j);
        IPixel closestSeed = this.closestPixelTo(i, j, seeds);

        thisRow.add(avgPixels(pxToMosaic, closestSeed));

      }
      newPixels.add(thisRow);
    }

    return new ImageImpl(newPixels);
  }

  @Override
  public BufferedImage getBufferedImage() {
    BufferedImage outputImage = new BufferedImage(this.pixels.getWidth(), this.pixels.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    for (int r = 0; r < this.pixels.getHeight(); r++) {
      for (int c = 0; c < this.pixels.getWidth(); c++) {
        // Get RGB values
        int red = this.pixels.getElement(r, c).getIntensity(EChannelType.RED);
        int green = this.pixels.getElement(r, c).getIntensity(EChannelType.GREEN);
        int blue = this.pixels.getElement(r, c).getIntensity(EChannelType.BLUE);
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        outputImage.setRGB(c, r, rgb);
      }
    }
    return outputImage;
  }

  /**
   * Todo:
   * @param row
   * @param col
   * @param seeds
   * @return
   */
  private IPixel closestPixelTo(int row, int col, List<IndexedPixel> seeds) {
    return Collections.min(seeds,
        ((px1, px2) -> (int) (px1.distanceTo(row, col) - px2.distanceTo(row, col)))).px;
  }

  /**
   * TODO
   *
   * @param px1
   * @param px2
   * @return
   */
  private static IPixel avgPixels(IPixel px1, IPixel px2) {
    return new PixelImpl(
        avg(px1.getIntensity(EChannelType.RED), px2.getIntensity(EChannelType.RED)),
        avg(px1.getIntensity(EChannelType.GREEN), px2.getIntensity(EChannelType.GREEN)),
        avg(px1.getIntensity(EChannelType.BLUE), px2.getIntensity(EChannelType.BLUE)));
  }

  /**
   * TODO
   *
   * @param n1
   * @param n2
   * @return
   */
  private static int avg(int n1, int n2) {
    return (n1 + n2) / 2;
  }


  /**
   * Utility class to keep track of a pixel's position.
   */
  private class IndexedPixel {

    private final int row;
    private final int col;
    private final IPixel px;

    public IndexedPixel(int row, int col, IPixel px) {
      this.row = Utility.checkIntBetween(row, 0, Integer.MAX_VALUE);
      this.col = Utility.checkIntBetween(col, 0, Integer.MAX_VALUE);
      this.px = Utility.checkNotNull(px, "cannot create an indexed pixel with a null "
          + "pixel value");
    }

    public double distanceTo(int row, int col) {
      return Math.sqrt(Math.pow(row - this.row, 2) + Math.pow(col - this.col, 2));
    }
  }
}