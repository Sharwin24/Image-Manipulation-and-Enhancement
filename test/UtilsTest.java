import static org.junit.Assert.assertEquals;

import cs3500.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Tests for methods in the {@link Utils} class.
 */
public class UtilsTest {

  /**
   * Tests for the {@link Utils#checkNotNull(Object, String)} method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckNotNullOnNullInputThrowsIllegalArg() {
    Object imNull = null;
    Utils.checkNotNull(imNull, "message to be thrown");
  }

  @Test
  public void testCheckNotNullOnNullInputThrowsCorrectErrorMsg() {
    Object imNull = null;
    try {
      Utils.checkNotNull(imNull, "sPeCiFiC mEsSaGe");
    } catch (IllegalArgumentException e) {
      assertEquals("sPeCiFiC mEsSaGe", e.getMessage());
    }
  }

  @Test
  public void testCheckNotNullReturnsNotNullObject() {
    Object imNotNull = new String();
    assertEquals(new String(), Utils.checkNotNull(imNotNull, "irrelevant"));
  }

  /**
   * Tests for the {@link Utils#checkNotNullListContents(List, String)} method.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNotNullListContentsThrowsForNullList() {
    List imNullDabaDeeDabaDai = null;
    Utils.checkNotNullListContents(imNullDabaDeeDabaDai,
        "Yo listen up, here's the story\n"
            + "About a little guy that lives in a null world\n"
            + "And all day and all night\n"
            + "And everything he sees is just null\n"
            + "Like him, inside and outside\n"
            + "null his house with a null little window\n"
            + "And a null Corvette\n"
            + "And everything is null for him\n"
            + "And himself and everybody around\n"
            + "'Cause he ain't got nobody to listen (to listen, to listen, to listen)");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckNotNullListContentsThrowsForNullListContents() {
    List iHaveOneNullElements = new ArrayList<Integer>(Arrays.asList(1, 2, null, null, 3));
    Utils.checkNotNullListContents(iHaveOneNullElements, "whats the deal with airline "
        + "food?");
  }

  @Test
  public void testCheckNotNullListContentsDoesntThrowForEmptyList() {
    List imEmptyInside = new ArrayList();

    assertEquals(0,
        Utils.checkNotNullListContents(imEmptyInside, "irrelevant").size());

  }




  /**
   * Tests for the {@link Utils#checkIntBetween(int, int, int)} method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckIntBetweenThrowsWhenIntLessThanLowerBound() {
    int lowerBound = 0;
    int upperBound = 10;
    int toCheck = -1;

    Utils.checkIntBetween(toCheck, lowerBound, upperBound);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckIntBetweenThrowsWhenIntGreaterThanLowerBound() {
    int lowerBound = 0;
    int upperBound = 10;
    int toCheck = 11;

    Utils.checkIntBetween(toCheck, lowerBound, upperBound);
  }

  @Test
  public void testCheckIntBetweenReturnsWhenIntAtLowerBound() {
    int lowerBound = 0;
    int upperBound = 10;
    int toCheck = lowerBound;

    assertEquals(0,
        Utils.checkIntBetween(toCheck, lowerBound, upperBound));
  }

  @Test
  public void testCheckIntBetweenReturnsWhenIntAtUpperBound() {
    int lowerBound = 0;
    int upperBound = 10;
    int toCheck = upperBound;

    assertEquals(10,
        Utils.checkIntBetween(toCheck, lowerBound, upperBound));
  }

  @Test
  public void testCheckIntBetweenReturnsWhenIntClearlyInBounds() {
    int lowerBound = 0;
    int upperBound = 10;
    int toCheck = 5;

    assertEquals(5,
        Utils.checkIntBetween(toCheck, lowerBound, upperBound));
  }

  /**
   * Tests for the {@link Utils#println(String)} method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPrintlnWithNullStringThrowsIllegalArg() {
    Utils.println(null);
  }

  @Test
  public void testPrintlnEmptyString() {
    assertEquals("\n", Utils.println(""));
  }

  @Test
  public void testPrintlnNonEmptyString() {
    assertEquals("words...\n", Utils.println("words..."));
  }

  /**
   * Tests for the {@link Utils#paddedPrint(String)} method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testpaddedPrintWithNullStringThrowsIllegalArg() {
    Utils.paddedPrint(null);
  }

  @Test
  public void testpaddedPrintEmptyString() {
    assertEquals("\n\n", Utils.paddedPrint(""));
  }

  @Test
  public void testpaddedPrintNonEmptyString() {
    assertEquals("\nwords...\n", Utils.paddedPrint("words..."));
  }




}