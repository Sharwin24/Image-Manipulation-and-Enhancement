import static org.junit.Assert.assertEquals;

import cs3500.model.channel.EChannelType;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing the {@link IPixel} implementation.
 */
public class PixelTest {

  private IPixel redPixel;
  private IPixel greenPixel;
  private IPixel orangepixel;
  private IPixel maxPixel;
  private IPixel smallPixel;

  @Before
  public void init() {
    this.redPixel = new PixelImpl(255, 0, 0);
    this.greenPixel = new PixelImpl(0, 255, 0);
    this.orangepixel = new PixelImpl(255, 128, 0);
    this.maxPixel = new PixelImpl(300, 50, 256);
    this.smallPixel = new PixelImpl(-5, -1, 0);
  }

  // Constructor enforces [0,255] range for R, G, and B.
  @Test
  public void constructorEnforcesIntensityRange() {
    assertEquals(255, maxPixel.getIntensity(EChannelType.RED));
    assertEquals(0, smallPixel.getIntensity(EChannelType.GREEN));
  }

  // getIntensity for R, G, and B.
  @Test
  public void testGetIntensity() {
    assertEquals(255, orangepixel.getIntensity(EChannelType.RED));
    assertEquals(128, orangepixel.getIntensity(EChannelType.GREEN));
    assertEquals(0, orangepixel.getIntensity(EChannelType.BLUE));
  }

  // toString
  @Test
  public void testToString() {
    assertEquals("255 0 0", redPixel.toString());
    assertEquals("0 255 0", greenPixel.toString());
    assertEquals("255 50 255", maxPixel.toString());
    assertEquals("0 0 0", smallPixel.toString());
  }

  // Exceptions:
  // getIntensity when given an null channel.
  @Test(expected = IllegalArgumentException.class)
  public void getIntensityGivenNullChanel() {
    this.redPixel.getIntensity(null);
  }
}