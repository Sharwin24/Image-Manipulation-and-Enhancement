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
      int row = r.nextInt(this.getHeight());
      int col = r.nextInt(this.getWidth());

      seeds.add(new IndexedPixel(row, col, this.pixels.getElement(row, col)));
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
  public IImage downscale(int newHeight, int newWidth) {
    IMatrix<IPixel> pixels = getPixelArray();
    IMatrix<IPixel> newPixels = new MatrixImpl<>(new PixelImpl(0, 0, 0), newHeight,
        newWidth);
    int originalWidth = getWidth();
    int originalHeight = getHeight();
    for (int r = 0; r < newPixels.getHeight(); r++) {
      for (int c = 0; c < newPixels.getWidth(); c++) {
        double oldX = (c / (double) newWidth) * originalWidth;
        double oldY = (r / (double) newHeight) * originalHeight;
        if (oldX % 1 == 0 || oldY % 1 == 0) { // If either is an int
          newPixels.updateEntry(pixels.getElement((int) oldY, (int) oldX), r, c);
        } else {
          // floor and ceiling of (x,y)
          int floorX = (int) Math.floor(oldX);
          int floorY = (int) Math.floor(oldY);
          int ceilX = (int) Math.ceil(oldX);
          int ceilY = (int) Math.ceil(oldY);
          // Pixels ABCD:
          IPixel pixelA = pixels.getElement(floorY, floorX);
          IPixel pixelB = pixels.getElement(floorY, ceilX);
          IPixel pixelC = pixels.getElement(ceilY, floorX);
          IPixel pixelD = pixels.getElement(ceilY, ceilX);
          int red = 0;
          int green = 0;
          int blue = 0;
          for (EChannelType channel : EChannelType.values()) { // for all color channels
            double m = pixelB.getIntensity(channel) * (oldX - floorX)
                + pixelA.getIntensity(channel) * (ceilX - oldX);
            double n = pixelD.getIntensity(channel) * (oldX - floorX)
                + pixelC.getIntensity(channel) * (ceilX - oldX);
            double cp = n * (oldY - floorY) + m * (ceilY - oldY);
            switch (channel) {
              case RED:
                red = (int) cp;
                break;
              case GREEN:
                green = (int) cp;
                break;
              case BLUE:
                blue = (int) cp;
                break;
            }
          }
          IPixel newPixel = new PixelImpl(red, green, blue);

          newPixels.updateEntry(newPixel, r, c);
        }
      }
    }
    return new ImageImpl(newPixels);
  }

  @Override
  public BufferedImage getBufferedImage() throws IllegalArgumentException {
    BufferedImage outputImage;
    try {
      outputImage = new BufferedImage(this.pixels.getWidth(),
          this.pixels.getHeight(),
          BufferedImage.TYPE_INT_RGB);
    } catch (Exception e) {
      throw new IllegalArgumentException("Empty Image");
    }
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
   * Finds the closest seed to the pixel at the given location.
   *
   * @param row   the pixel's row position.
   * @param col   the pixel's column position.
   * @param seeds the list of seeds to find the closest one to.
   * @return the closest seed.
   */
  private IPixel closestPixelTo(int row, int col, List<IndexedPixel> seeds) {
    return Collections.min(seeds, // lambda moment
        ((px1, px2) -> (int) (px1.distanceTo(row, col) - px2.distanceTo(row, col)))).px;
  }

  /**
   * Returns an IPixel with an average value of the 2 given pixels.
   *
   * @param px1 the first pixel.
   * @param px2 the second pixel.
   * @return a new IPixel with an average color of the 2 given pixels.
   */
  private static IPixel avgPixels(IPixel px1, IPixel px2) {
    return new PixelImpl(
        avg(px1.getIntensity(EChannelType.RED), px2.getIntensity(EChannelType.RED)),
        avg(px1.getIntensity(EChannelType.GREEN), px2.getIntensity(EChannelType.GREEN)),
        avg(px1.getIntensity(EChannelType.BLUE), px2.getIntensity(EChannelType.BLUE)));
  }

  /**
   * Returns the average of the 2 given integers.
   *
   * @param n1 the first number.
   * @param n2 the second number.
   * @return an integer representing the average.
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