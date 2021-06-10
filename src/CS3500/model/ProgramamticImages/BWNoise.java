package CS3500.model.ProgramamticImages;

import CS3500.model.pixel.PixelImpl;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO
 */
public class BWNoise extends Noise {

  public BWNoise() {
    super(new ArrayList<>(Arrays.asList(
        PixelImpl.BLACK,
        PixelImpl.WHITE)));
  }
}
