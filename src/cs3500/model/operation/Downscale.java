package cs3500.model.operation;

import cs3500.model.IMultiLayerModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.channel.EChannelType;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.layer.ILayer;
import cs3500.model.layer.Layer;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;

/**
 * Class to represent the operation of Downscaling an image.
 */
public class Downscale {

  private IMultiLayerModel model;
  private int newHeight;
  private int newWidth;

  public Downscale(IMultiLayerModel model, int newHeight, int newWidth) {
    if (model == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    this.model = model;
    this.newHeight = newHeight;
    this.newWidth = newWidth;
  }

  public void apply() {
    for (ILayer layer : this.model.getLayers()) {
      this.applyToLayer(layer);
    }
  }

  private void applyToLayer(ILayer layer) {
    IImage image = layer.getModel().getImage();
    IMatrix<IPixel> pixels = image.getPixelArray();
    IMatrix<IPixel> newPixels = new MatrixImpl<>(new PixelImpl(0, 0, 0), this.newHeight,
        this.newWidth);
    int originalWidth = image.getWidth();
    int originalHeight = image.getHeight();
    for (int r = 0; r < pixels.getHeight(); r++) {
      for (int c = 0; c < pixels.getWidth(); c++) {
        double newX = (c / (double) originalWidth) * newWidth;
        double newY = (r / (double) originalHeight) * newHeight;
        if (newX % 1 == 0 || newY % 1 == 0) { // if newX and newY are ints
          newPixels.updateEntry(pixels.getElement(r, c), (int) newY, (int) newX);
        } else { // newX AND newY is floating point
          int floorX = (int) Math.floor(newX);
          int floorY = (int) Math.floor(newY);
          int ceilX = (int) Math.ceil(newX);
          int ceilY = (int) Math.ceil(newY);
          IPixel pixelA = pixels.getElement(floorY, floorX);
          IPixel pixelB = pixels.getElement(floorY, ceilX);
          IPixel pixelC = pixels.getElement(ceilY, floorX);
          IPixel pixelD = pixels.getElement(ceilY, ceilX);
          int red = 0;
          int green = 0;
          int blue = 0;
          for (EChannelType channel : EChannelType.values()) { // for all color channels
            double m = pixelB.getIntensity(channel) * (newX - floorX)
                + pixelA.getIntensity(channel) * (ceilX - newX);
            double n = pixelD.getIntensity(channel) * (newX - floorX)
                + pixelC.getIntensity(channel) * (ceilX - newX);
            double cp = n * (newY - floorY) + m * (ceilY - newY);
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
          newPixels.updateEntry(newPixel, 0, 0);  // STUB, DELETE
        }
      }
    }

  }


}