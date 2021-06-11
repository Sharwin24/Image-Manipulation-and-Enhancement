import static org.junit.Assert.assertEquals;

import cs3500.model.channel.EChannelType;
import org.junit.Before;
import org.junit.Test;

public class EChannelTypeTest {

  private EChannelType redChannel;
  private EChannelType greenChannel;
  private EChannelType blueChannel;

  @Before
  public void init() {
    this.redChannel = EChannelType.RED;
    this.greenChannel = EChannelType.GREEN;
    this.blueChannel = EChannelType.BLUE;
  }

  // toString
  @Test
  public void testToString() {
    assertEquals("R", redChannel.toString());
    assertEquals("G", greenChannel.toString());
    assertEquals("B", blueChannel.toString());
  }
}
