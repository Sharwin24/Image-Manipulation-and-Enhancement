import static org.junit.Assert.assertEquals;

import cs3500.model.channel.ChannelImpl;
import cs3500.model.channel.EChannelType;
import cs3500.model.channel.IChannel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link IChannel} and {@link ChannelImpl}.
 */
public class ChannelTest {

  private IChannel redChannel;
  private IChannel greenChannel;
  private IChannel blueChannel;

  @Before
  public void init() {
    redChannel = new ChannelImpl(EChannelType.RED, 255);
    greenChannel = new ChannelImpl(EChannelType.GREEN, 0);
    blueChannel = new ChannelImpl(EChannelType.BLUE, 120);
  }

  // get intensity
  @Test
  public void testGetIntensity() {
    assertEquals(255, redChannel.getIntensity());
    assertEquals(0, greenChannel.getIntensity());
    assertEquals(120, blueChannel.getIntensity());
  }

  // constructor enforces [0,255] range for intensity
  @Test
  public void constructorEnforcesIntensityRange() {
    IChannel highChannel = new ChannelImpl(EChannelType.RED, 300);
    assertEquals(255, highChannel.getIntensity());
    IChannel lowChannel = new ChannelImpl(EChannelType.GREEN, -45);
    assertEquals(0, lowChannel.getIntensity());
  }

  // toString as expected
  @Test
  public void testToString() {
    assertEquals("R255", redChannel.toString());
    assertEquals("G0", greenChannel.toString());
    assertEquals("B120", blueChannel.toString());
  }

  // Exceptions:
  // Constructor exception if color is null
  @Test(expected = IllegalArgumentException.class)
  public void constructorGivenNull() {
    IChannel channel = new ChannelImpl(null, 45);
  }
}