package CS3500.model.ProgramamticImages;

import CS3500.model.pixel.PixelImpl;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A {@link Noise} image consisting of only the colors {@link PixelImpl#BLACK} and
 * {@link PixelImpl#WHITE}.
 */
public class BWNoise extends Noise {

  /**
   * Initialize the colors that this {@link Noise} image can select from to just black and white.
   */
  public BWNoise() {
    super(new ArrayList<>(Arrays.asList(
        PixelImpl.BLACK,
        PixelImpl.WHITE)));
  }
}
