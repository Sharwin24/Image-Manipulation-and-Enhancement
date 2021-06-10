package CS3500.model.ProgrammaticImages;

import CS3500.Utils;
import CS3500.model.image.IImage;
import CS3500.model.image.ImageImpl;
import CS3500.model.pixel.IPixel;
import CS3500.model.pixel.PixelImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PureNoise implements IProgramImage<IImage> {

  @Override
  public IImage createProgramImage(int widthPx, int heightPx, int unitPx)
      throws IllegalArgumentException {
    Utils.checkIntBetween(widthPx, 0, Integer.MAX_VALUE);
    Utils.checkIntBetween(heightPx, 0, Integer.MAX_VALUE);

    List<List<IPixel>> pixels = new ArrayList<>();
    for (int i = 0; i < heightPx; i++) {
      List<IPixel> thisRow = new ArrayList<>();
      for (int j = 0; j < widthPx; j++) {
        Random rand = new Random();
        thisRow.add(
            new PixelImpl(
                rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
      }
      pixels.add(thisRow);
    }

    return new ImageImpl(pixels);
  }

}