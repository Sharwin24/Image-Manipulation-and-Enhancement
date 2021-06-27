import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.Utility;
import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IMultiLayerModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.channel.ChannelImpl;
import cs3500.model.channel.EChannelType;
import cs3500.model.channel.IChannel;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.model.fileformat.PPMFile;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.layer.ILayer;
import cs3500.model.layer.Layer;
import cs3500.model.matrix.AMatrix;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.IFilter;
import cs3500.model.operation.IOperation;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.MyFilter;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import cs3500.model.programmaticimages.Checkerboard;
import cs3500.model.programmaticimages.RainbowNoise;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.Before;
import org.junit.Test;

/**
 * Every test class for the Image Manipulation and Enhancement program, consolidated into one class,
 * in order to appease the almighty handin server. Praise be the handin server, bringer of life,
 * creator of all that is good and holy. May the handin server's mighty hand grace our presence,
 * such that one day we may find eternal peace and style. Amen.
 */
public class ConsolidatedTests {


  /**
   * Abstract class to test Color Transforms.
   */
  public abstract static class AbstractColorTransformTest {

    private IOperation op;
    private IOperation zeros;
    private IImage testImage;

    @Before
    public void init() {
      this.op = constructColorTransform();
      this.zeros = new MyFilter();
      this.testImage = new ImageImpl(new MatrixImpl<>(new PixelImpl(0, 100, 0), 10, 10));
    }

    // Color Transforming an image changes all channels.
    @Test
    public void testFilter() {
      IImage filtered = this.op.apply(this.testImage);
      assertNotEquals(filtered.getPixelArray(), testImage.getPixelArray());
    }

    // Color transform of zeros matrix results in black image.
    @Test
    public void filterWithZeros() {
      IImage zeroImage = this.zeros.apply(this.testImage);
      for (int i = 0; i < this.testImage.getPixelArray().getHeight(); i++) {
        for (int j = 0; j < this.testImage.getPixelArray().getWidth(); j++) {
          assertEquals(new PixelImpl(0, 0, 0), zeroImage.getPixelArray().getElement(i, j));
        }
      }
    }

    // Exceptions:
    // Color Transform when given null image
    @Test(expected = IllegalArgumentException.class)
    public void filterWithNull() {
      this.op.apply(null);
    }

    /**
     * Constructs a ColorTransform for testing.
     */
    protected abstract IOperation constructColorTransform();

    /**
     * Static class for testing Greyscale.
     */
    public static class GreyscaleTest extends AbstractColorTransformTest {

      @Override
      protected IOperation constructColorTransform() {
        return new Greyscale();
      }
    }

    /**
     * Static class for testing Sepia.
     */
    public static class SepiaTest extends AbstractColorTransformTest {

      @Override
      protected IOperation constructColorTransform() {
        return new Sepia();
      }
    }
  }


  /**
   * Class for Testing FileFormatMethods.
   */
  public abstract static class AbstractFileFormatTest {

    private IFileFormat format;

    @Before
    public void init() {
      this.format = this.constructFileFormat();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImportImageThrowsWhenGivenNullRelativePath() {
      this.format.importImage(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImportImageThrowsWhenGivenEmptyRelativePath() {
      this.format.importImage("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImportImageThrowsWhenRelativePathDoesntEndInPPM() {
      this.format.importImage("blablabla.bike");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImportImageThrowsWhenPathNotFound() {
      this.format.importImage("cant find this path");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImportImageThrowsWhenPPMCorrupted() {
      this.format.importImage("res/CorruptedFile.ppm");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImportImageThrowsWhenPPMNotOfTypeP3() {
      this.format.importImage("res/NotP3.ppm");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImportImageThrowsWhenPPMEmpty() {
      this.format.importImage("res/EmptyFile.ppm");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImportImageThrowsWhenNotGivenPPM() {
      this.format.importImage("res/NotAPPM.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExportImageThrowsForNullPathName() {
      this.format.exportImage(null, new ImageImpl(
          new MatrixImpl<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExportImageThrowsForNullImage() {
      this.format.exportImage("nullImage->", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExportImageThrowsForEmptyPathName() {
      this.format.exportImage("", new ImageImpl(
          new MatrixImpl<>()));
    }

    @Test
    public void testExportImageCreatesFile() {
      IMatrix<IPixel> pxs = new MatrixImpl<>(new PixelImpl(255, 0, 0),
          3, 3);
      assertTrue(this.format.exportImage("res/testCreateFile",
          new ImageImpl(pxs)).exists());
    }

    protected abstract IFileFormat constructFileFormat();

    /**
     * Class for Constructing a PPM to test with.
     */
    public class PPMTest extends AbstractFileFormatTest {

      @Override
      protected IFileFormat constructFileFormat() {
        return new PPMFile();
      }

      @Test
      public void testImportImageSuccessfulReturnsImageWithSameDimensions() {
        IImage imported = new PPMFile().importImage("res/Simple8x8.ppm");
        assertEquals(8, imported.getPixelArray().getHeight());
        assertEquals(8, imported.getPixelArray().getWidth());
      }

      @Test
      public void testImportImageSuccessfulReturnsImageWithPixels() {
        IImage imported = new PPMFile().importImage("res/Simple8x8.ppm");
        assertEquals(new PixelImpl(255, 0, 0),
            imported.getPixelArray().getElement(0, 0));
      }
    }

    /**
     * Class for Constructing a PNG to test with.
     */
    public class PNGTest extends AbstractFileFormatTest {

      @Override
      protected IFileFormat constructFileFormat() {
        return new PNGFile();
      }
    }

    /**
     * Class for Constructing a JPEG to test with.
     */
    public static class JPEGTest extends AbstractFileFormatTest {

      @Override
      protected IFileFormat constructFileFormat() {
        return new JPEGFile();
      }
    }
  }


  /**
   * Abstract class for testing all {@link IFilter} methods and behavior.
   */
  public abstract static class AbstractFilterTest {

    private IOperation op;
    private IOperation zeros;
    private IImage redImage;
    private IImage testImage;

    /**
     * Creates a 3x3 Matrix of red pixels.
     *
     * @return an Image of a red square.
     */
    private IMatrix<IPixel> redSquare() {
      return new MatrixImpl<>(new PixelImpl(100, 0, 0), 3, 3);
    }

    /**
     * Creates a 3x3 Matrix of pixels.
     *
     * @return an Image of a square.
     */
    private IMatrix<IPixel> testSquare() {
      return new MatrixImpl<>(new PixelImpl(1, 2, 3), 3, 3);
    }

    @Before
    public void init() {
      this.op = this.constructFilter();
      this.zeros = new MyFilter();
      this.redImage = new ImageImpl(this.redSquare());
      this.testImage = new ImageImpl(this.testSquare());
    }

    // Filtering with a kernel of zeros makes entire array zeros.
    @Test
    public void filterWithZeros() {
      IImage zeroImage = this.zeros.apply(this.redImage);
      for (int i = 0; i < this.redImage.getPixelArray().getHeight(); i++) {
        for (int j = 0; j < this.redImage.getPixelArray().getWidth(); j++) {
          assertEquals(new PixelImpl(0, 0, 0), zeroImage.getPixelArray().getElement(i, j));
        }
      }
    }

    // Filtering an image makes changes to all channels of an image.
    @Test
    public void testFilter() {
      IImage filtered = this.op.apply(this.testImage);
      assertNotEquals(filtered.getPixelArray(), testImage.getPixelArray());
    }

    // Kernel size larger than image modifies image correctly
    @Test
    public void filterWithLargeKernel() {
      IImage smallImage = new ImageImpl(new MatrixImpl<>(new PixelImpl(0, 1, 0), 2, 3));
      IImage output = this.op.apply(smallImage);
      assertNotEquals(output, smallImage);
    }

    // Exceptions:
    // Filtering with null image given
    @Test(expected = IllegalArgumentException.class)
    public void filterWithNull() {
      this.op.apply(null);
    }

    /**
     * Constructs an IOperation.
     *
     * @return an IOperation.
     */
    protected abstract IOperation constructFilter();

    /**
     * Class for testing the Sharpening Filter.
     */
    public static class SharpeningTest extends AbstractFilterTest {

      @Override
      protected IOperation constructFilter() {
        return new Sharpening();
      }
    }

    /**
     * Class for testing the Blur Filter.
     */
    public static class BlurTest extends AbstractFilterTest {

      @Override
      protected IOperation constructFilter() {
        return new ImageBlur();
      }
    }

  }


  /**
   * Tests for methods in the {@link cs3500.model.matrix.AMatrix} class.
   */
  public abstract static class AbstractMatrixTest {

    private IMatrix<Integer> m3x3Ints;
    private IMatrix<Character> m3x2chars;
    private IMatrix<String> m5x1Strings;
    private IMatrix emptyMatrix;

    @Before
    public void setUp() {
      this.m3x3Ints = this.constructMatrix(new ArrayList<>(Arrays.asList(
          new ArrayList<>(Arrays.asList(1, 2, 3)),
          new ArrayList<>(Arrays.asList(4, 5, 6)),
          new ArrayList<>(Arrays.asList(7, 8, 9))
      )));

      this.m3x2chars = this.constructMatrix(new ArrayList<>(Arrays.asList(
          new ArrayList<>(Arrays.asList('a', 'b')),
          new ArrayList<>(Arrays.asList('c', 'd')),
          new ArrayList<>(Arrays.asList('e', 'f'))
      )));

      this.m5x1Strings = this.constructMatrix(new ArrayList<>(Arrays.asList(
          new ArrayList<>(Arrays.asList("zero")),
          new ArrayList<>(Arrays.asList("one")),
          new ArrayList<>(Arrays.asList("two")),
          new ArrayList<>(Arrays.asList("three")),
          new ArrayList<>(Arrays.asList("four"))
      )));

      this.emptyMatrix = this.constructMatrix(new ArrayList<>());
    }

    /**
     * To construct an {@link IMatrix} of any concrete subtype that extends {@link
     * cs3500.model.matrix.AMatrix}.
     *
     * @param entries the entries of this matrix.
     * @param <X>     the type of entries in this matrix.
     * @return a matrix of a concrete subtype of {@link cs3500.model.matrix.AMatrix} filled with the
     *         given entries.
     */
    protected abstract <X> IMatrix<X> constructMatrix(List<List<X>> entries);

    /**
     * Tests specific to methods in the {@link MatrixImpl} class.
     */
    public static class MatrixImplTest extends AbstractMatrixTest {

      @Override
      protected <X> IMatrix constructMatrix(List<List<X>> entries) {
        return new MatrixImpl(entries);
      }

      /**
       * Tests for the {@link cs3500.model.matrix.MatrixImpl#MatrixImpl(java.util.List)}
       * constructor.
       */
      @Test(expected = IllegalArgumentException.class)
      public void test2DListConstructorThrowsForNull2DList() {
        new MatrixImpl<>(null);
      }

      @Test(expected = IllegalArgumentException.class)
      public void test2DListConstructorThrowsForNullListMember() {
        new MatrixImpl<>(new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(1, 2, null))
        )));
      }

      @Test
      public void test2DListConstructorCreatesMatrixWithCorrectDimensions() {
        IMatrix m2x3 = new MatrixImpl(new ArrayList<>(Arrays.asList(
            new ArrayList(Arrays.asList(1, 2, 3)),
            new ArrayList<>(Arrays.asList(4, 5, 6)))));
        assertEquals(2, m2x3.getHeight());
        assertEquals(3, m2x3.getWidth());
      }

      /**
       * Tests for the {@link MatrixImpl#MatrixImpl(List, int)} constructor.
       */
      @Test(expected = IllegalArgumentException.class)
      public void testListConstructorThrowsForNullList() {
        new MatrixImpl<>(null, 3);
      }

      @Test(expected = IllegalArgumentException.class)
      public void testListConstructorThrowsForNegativeCopies() {
        new MatrixImpl<>(null, 3);
      }

      @Test(expected = IllegalArgumentException.class)
      public void testListConstructorThrowsForNullRows() {
        new MatrixImpl<>(new ArrayList<>(Arrays.asList(new ArrayList(), null)), 3);
      }

      @Test(expected = IllegalArgumentException.class)
      public void testListConstructorThrowsForNullEntries() {
        new MatrixImpl<>(new ArrayList<>(Arrays.asList(new ArrayList(Arrays.asList(null, 2)))), 3);
      }

      @Test
      public void testListConstructorCreatesMatrixWithCorrectDimensions() {
        IMatrix m2x3 = new MatrixImpl(new ArrayList<>(Arrays.asList(1, 2, 3)), 2);
        assertEquals(2, m2x3.getHeight());
        assertEquals(3, m2x3.getWidth());
      }

      /**
       * Tests for the {@link MatrixImpl#MatrixImpl(Object, int, int)} constructor.
       */
      @Test(expected = IllegalArgumentException.class)
      public void testOneEntryConstructorThrowsForNullEntry() {
        new MatrixImpl<>(null, 4, 5);
      }

      @Test(expected = IllegalArgumentException.class)
      public void testOneEntryConstructorThrowsForNegativeRows() {
        new MatrixImpl<>(2, -1, 5);
      }

      @Test(expected = IllegalArgumentException.class)
      public void testOneEntryConstructorThrowsForNegativeCols() {
        new MatrixImpl<>(2, 4, -2);
      }

      @Test
      public void testOneEntryConstructorCreatesMatrixWithCorrectDimensions() {
        IMatrix m2x3 = new MatrixImpl(1, 2, 3);
        assertEquals(2, m2x3.getHeight());
        assertEquals(3, m2x3.getWidth());
      }
    }

    /**
     * Tests for the {@link AMatrix#getElement(int, int)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testgetElementThrowsWhenRowInvalid() {
      m3x2chars.getElement(3, 2);
    }

    @Test
    public void testgetElementReturnsValidElement() {
      assertEquals((Integer) 3, m3x3Ints.getElement(0, 2)); // safe cast
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetElementThrowsWhenColInvalid() {
      m5x1Strings.getElement(4, -2);
    }


    /**
     * Tests for the {@link AMatrix#getWidth()} method.
     */


    @Test
    public void testGetWidth3x3() {
      assertEquals(3, m3x3Ints.getWidth());
    }

    @Test
    public void testGetWidth5x1() {
      assertEquals(1, m5x1Strings.getWidth());
    }

    @Test
    public void testGetWidthEmpty() {
      assertEquals(0, emptyMatrix.getWidth());
    }


    /**
     * Tests for the {@link AMatrix#getHeight()} method.
     */

    @Test
    public void testGetHeight3x3() {
      assertEquals(3, m3x3Ints.getHeight());
    }

    @Test
    public void testGetHeight5x1() {
      assertEquals(5, m5x1Strings.getHeight());

    }

    @Test
    public void testGetHeightEmpty() {
      assertEquals(0, emptyMatrix.getHeight());
    }


    /**
     * Tests for the {@link AMatrix#fillWith(Object)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFillWithThrowsWhenFillerNull() {
      m5x1Strings.fillWith("hi");
    }

    @Test
    public void testFillWith3x3With1() {
      m3x3Ints.fillWith(1);

      // checks that each entry is 1
      assertTrue(m3x3Ints.map(entry -> entry == 1).reduceToVal(((b1, b2) -> b1 && b2), true));
    }

    @Test
    public void testFillWithDoesNothingForEmpty() {
      IMatrix newEmptyMatrix = emptyMatrix.copy();
      newEmptyMatrix.fillWith(new Object());
      assertEquals(newEmptyMatrix, emptyMatrix);
    }


    @Test
    public void testFillWithHi5x1() {
      m5x1Strings.fillWith("hi");

      // checks that each entry is "hi"
      assertTrue(
          m5x1Strings.map(entry -> entry.equals("hi")).reduceToVal(((b1, b2) -> b1 && b2), true));
    }

    @Test
    public void testFillWithX3x2() {
      m3x2chars.fillWith('X');

      // checks that each entry is 'X'
      assertTrue(m3x2chars.map(entry -> entry == 'X').reduceToVal(((b1, b2) -> b1 && b2), true));
    }

    @Test
    public void testFillWithPreservesDimensions() {
      int wBefore = m3x2chars.getWidth();
      int hBefore = m3x2chars.getHeight();

      m3x2chars.fillWith('i');

      int wAfter = m3x2chars.getWidth();
      int hAfter = m3x2chars.getHeight();

      assertEquals(hAfter, hBefore);
      assertEquals(wAfter, wBefore);
    }


    /**
     * Tests for the {@link AMatrix#updateEntry(Object, int, int)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEntryThrowsWhenEntryNull() {
      m3x2chars.updateEntry(null, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEntryThrowsWhenRowInvalid() {
      m5x1Strings.updateEntry(":(", -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEntryThrowsWhenColInvalid() {
      m5x1Strings.updateEntry(":(", 0, 1);
    }

    @Test
    public void testUpdateEntryUpdatesEntryWhenPassedValidArguments() {
      int row = 0;
      int col = 0;

      int entryBefore = m3x3Ints.getElement(row, col);
      assertEquals(0, entryBefore);

      m3x3Ints.fillWith(999);

      int entryAfter = m3x3Ints.getElement(row, col);

      assertNotEquals(entryAfter, entryBefore);
      assertEquals(999, entryAfter);
    }

    @Test
    public void testUpdateEntryPreservesMatrixDimensions() {
      int hBefore = m3x2chars.getHeight();
      int wBefore = m3x2chars.getWidth();

      m3x2chars.updateEntry('g', 0, 1);

      int hAfter = m3x2chars.getHeight();
      int wAfter = m3x2chars.getWidth();

      assertEquals(hAfter, hBefore);
      assertEquals(wAfter, wBefore);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEntryThrowsForEmptyMatrix() {
      emptyMatrix.updateEntry(new Object(), 0, 0);
    }


    /**
     * Tests for the {@link AMatrix#elementWiseOperation(BiFunction, IMatrix)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testElementWiseOperationThrowsWhenOperationNull() {
      m3x2chars.elementWiseOperation(null, m3x2chars);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testElementWiseOperationThrowsWhenMatrixNull() {
      m5x1Strings.elementWiseOperation(((str1, str2) -> str1 + str2), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testElementWiseOperationThrowsWhenDimensionsDontMatch() {
      IMatrix<String> m1x1Strings = new MatrixImpl<>("x", 1, 1);
      m5x1Strings.elementWiseOperation(((str1, str2) -> str1.repeat(3).concat(str2)), m1x1Strings);
    }


    @Test
    public void testElementWiseOperationWorksWithValidParametersSimpleBinOp() {
      IMatrix<Integer> otherM3x3Ints = new MatrixImpl(10, 3, 3);
      IMatrix<Integer> addedMatrices = m3x3Ints
          .elementWiseOperation(((m, n) -> m + n), otherM3x3Ints);
      for (int i = 0; i < addedMatrices.getHeight(); i++) {
        for (int j = 0; j < addedMatrices.getWidth(); j++) {
          assertTrue(
              addedMatrices.getElement(i, j) == m3x3Ints.getElement(i, j) + (Integer) otherM3x3Ints
                  .getElement(i, j));
        }
      }
    }

    @Test
    public void testElementWiseOperationPreservesMatrixDimensions() {
      IMatrix<Integer> otherM3x3Ints = new MatrixImpl(10, 3, 3);
      IMatrix<Integer> addedMatrices = m3x3Ints
          .elementWiseOperation(((m, n) -> m + n), otherM3x3Ints);
      assertEquals(addedMatrices.getHeight(), m3x3Ints.getHeight());
      assertEquals(addedMatrices.getWidth(), m3x3Ints.getWidth());
    }


    /**
     * Tests for the {@link AMatrix#map(Function)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMapThrowsWhenPassedNullOperation() {
      m5x1Strings.map(null);
    }

    @Test
    public void testMapSimpleUnaryOp() {
      IMatrix<Integer> mappedMatrix = m5x1Strings.map((str -> str.length()));
      for (int i = 0; i < mappedMatrix.getHeight(); i++) {
        for (int j = 0; j < mappedMatrix.getWidth(); j++) {
          assertEquals(mappedMatrix.getElement(i, j),
              (Integer) m5x1Strings.getElement(i, j).length()); // safe cast
        }
      }
    }

    @Test
    public void testMapComplexUnaryOp() {
      IMatrix<StringBuilder> mappedMatrix = m3x3Ints.map(
          (n -> new StringBuilder((Integer.toString(n)).repeat(3)).append(" ")));
      for (int i = 0; i < mappedMatrix.getHeight(); i++) {
        for (int j = 0; j < mappedMatrix.getWidth(); j++) {
          assertEquals(mappedMatrix.getElement(i, j),
              new StringBuilder(m3x3Ints.getElement(i, j).toString().repeat(3))
                  .append(" ")); // safe cast
        }
      }
    }

    /**
     * Tests for the {@link AMatrix#copy()} method.
     */

    @Test
    public void testCopyReturnsEqualMatrix() {
      assertEquals(m3x3Ints.copy(), m3x3Ints);
    }

    @Test
    public void testCopyReturnsDeepCopyNotReference() {
      assertFalse(m3x3Ints.copy() == m3x3Ints);
    }


    /**
     * Tests for the {@link AMatrix#getElement(int, int)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReduceToValThrowsWhenOperationNull() {
      m5x1Strings.reduceToVal(null, "x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReduceToValThrowsWhenBaseNull() {
      m5x1Strings.reduceToVal(((str1, str2) -> str1 + str2), null);
    }

    @Test
    public void testReduceToValSimpleBinOp() {
      assertEquals("zeroonetwothreefour",
          m5x1Strings.reduceToVal(((str1, str2) -> str1 + str2), ""));
    }

    @Test
    public void testReduceToValComplexBinOp() {
      assertEquals((Integer) 674034244, // safe cast
          m3x3Ints.reduceToVal(((m, n) -> (m + m * n) + (n + m * n) * m * n), 1));
    }


    /**
     * Tests for the {@link AMatrix#equals(Object)} method.
     */

    @Test
    public void testEqualsReturnsFalseForNonMatrixObject() {
      assertFalse(m3x3Ints.equals(new Object()));
    }

    @Test
    public void testEqualsReturnsFalseForMatrixOfSameDimensionsDifferentValues() {
      assertFalse(new MatrixImpl<Double>(2.5, 2, 2).equals(
          new MatrixImpl<Double>(5.2, 2, 2)
      ));
    }

    @Test
    public void testEqualsReturnsFalseForMatrixOfSameValuesDifferentDimensions() {
      IMatrix<String> m1x5Strings = new MatrixImpl<>(new ArrayList<>(Arrays.asList(new ArrayList<>(
          Arrays.asList("zero", "one", "two", "three", "four")))));
      assertFalse(m5x1Strings.equals(m1x5Strings));
    }

    @Test
    public void testEqualsIsReflexive() {
      assertTrue(m5x1Strings.equals(m5x1Strings));
    }

    @Test
    public void testEqualsIsSymmetric() {
      IMatrix<Character> copyOfM3x2Chars = m3x2chars.copy();
      assertTrue(m3x2chars.equals(copyOfM3x2Chars)
          && copyOfM3x2Chars.equals(m3x2chars));
    }

    @Test
    public void testEqualsIsTransitive() {
      IMatrix<Character> copyOfM3x2Chars = m3x2chars.copy();
      IMatrix<Character> copyOfCopyOfM3x2Chars = m3x2chars.copy();
      assertTrue(m3x2chars.equals(copyOfM3x2Chars)
          && copyOfM3x2Chars.equals(copyOfCopyOfM3x2Chars)
          && m3x2chars.equals(copyOfCopyOfM3x2Chars));
    }


    /**
     * Tests for the {@link AMatrix#hashCode()} method.
     */
    @Test
    public void testHashCodeFollowsEquals() {
      assertTrue(m3x2chars.equals(m3x2chars.copy())
          && m3x2chars.hashCode() == m3x2chars.copy().hashCode());
    }

    @Test
    public void testHashCodeNotEqualsForDifferentMatrices() {
      assertFalse(m3x2chars.hashCode() == m5x1Strings.hashCode());
    }


    /**
     * Tests for the {@link AMatrix#getElement(int, int)} method.
     */

    @Test
    public void testToStringM3x2Chars() {
      assertEquals("", m3x2chars.toString());
    }

    @Test
    public void testToStringM3x3Ints() {
      assertEquals("", m3x3Ints.toString());
    }

    @Test
    public void testToStringM5x1Strings() {
      assertEquals("", m5x1Strings);
    }

    @Test
    public void testToStringEmptyMatrix() {
      assertEquals("", new MatrixImpl<>().toString());
    }
  }


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


  /**
   * Tests for methods in the {@link EChannelType} class.
   */
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


  /**
   * Class for testing the Image implementation.
   */
  public class ImageTest {

    private IImage redImage;
    private IImage blankImage;

    /**
     * Creates a 3x3 Matrix of red pixels.
     *
     * @return an Image of a red square.
     */
    private IMatrix<IPixel> redSquare() {
      return new MatrixImpl<>(new PixelImpl(100, 0, 0), 3, 3);
    }

    /**
     * Creates a 3x3 Matrix of black pixels.
     *
     * @return an Image of a black square.
     */
    private IMatrix<IPixel> blackSquare() {
      return new MatrixImpl<>(new PixelImpl(0, 0, 0), 3, 3);
    }

    @Before
    public void init() {
      this.redImage = new ImageImpl(redSquare());
      IImage blackImage = new ImageImpl(blackSquare());
      this.blankImage = new ImageImpl(new MatrixImpl<>());
    }

    // extractChannel
    @Test
    public void testExtractChannel() {
      IMatrix<Integer> expected = new MatrixImpl<>(100, 3, 3);
      assertEquals(expected, redImage.extractChannel(EChannelType.RED));
    }

    // getPixelArray
    @Test
    public void testGetPixelArray() {
      assertEquals(3, redImage.getPixelArray().getWidth());
      assertEquals(3, redImage.getPixelArray().getHeight());
      assertEquals(new PixelImpl(100, 0, 0), redImage.getPixelArray().getElement(2, 2));
    }

    @Test
    public void blankImagePixelArray() {
      assertEquals(0, blankImage.getPixelArray().getWidth());
      assertEquals(0, blankImage.getPixelArray().getHeight());
    }

    // copy returns a deep copy
    @Test
    public void testDeepCopy() {
      assertNotEquals(this.redImage.copy().hashCode(),
          new ImageImpl(new MatrixImpl<>(new PixelImpl(100, 0, 0), 3, 3)).hashCode());
    }

    // Exceptions:
    // Constructor Exception for invalid Imatrix
    @Test(expected = IllegalArgumentException.class)
    public void imageConstructorGivenNullMatrix() {
      IImage image = new ImageImpl(new MatrixImpl<>(null, 0, 0));
    }
  }


  /**
   * Class for tests for the Layer implementation.
   */
  public class LayerTest {

    private ILayer layer;
    private IImage image;

    @Before
    public void init() {
      this.layer = new Layer();
      this.image = new ImageImpl(new MatrixImpl<>(new PixelImpl(255, 255, 255), 10, 10));
    }

    // isInvisible and toggleInvisible
    @Test
    public void testInvisible() {
      assertFalse(this.layer.isInvisible());
      this.layer.toggleInvisible();
      assertTrue(this.layer.isInvisible());
    }

    // getModel
    @Test
    public void testGetModel() {
      IStateTrackingIMEModel model = new StateTrackingIMEModelImpl();
      model.load(this.image);
      this.layer.modelLoad(this.image);
      assertEquals(model.getImage(), this.layer.getModel().getImage());
    }

    // getLayerHeight and Width
    @Test
    public void testGetHeightAndWidth() {
      assertEquals(-1, this.layer.getLayerHeight());
      assertEquals(-1, this.layer.getLayerWidth());
      this.layer.modelLoad(image);
      assertEquals(10, this.layer.getLayerHeight());
      assertEquals(10, this.layer.getLayerWidth());
    }

    // toString
    @Test
    public void testToString() {
      assertEquals(" | Visibility: true", this.layer.toString());
      this.layer.toggleInvisible();
      assertEquals(" | Visibility: false", this.layer.toString());
    }

    // copy
    @Test
    public void copyReturnsAnotherLayer() {
      this.layer.modelLoad(this.image);
      ILayer layerCopy = this.layer.copy();
      assertNotEquals(layerCopy, this.layer);
      assertNotEquals(layerCopy.getModel(), this.layer.getModel());
      assertEquals(layerCopy.getLayerHeight(), this.layer.getLayerHeight());
      assertEquals(layerCopy.getLayerWidth(), this.layer.getLayerWidth());
    }
  }


  /**
   * Tests for methods in the {@link cs3500.controller.MultiLayerIMEControllerImpl} class.
   */
  public class MultiLayerIMEControllerImplTest {

    /**
     * Tests for the "undo" command.
     */
    @Test
    public void testUndoWhenCantUndo() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "Failed to undo: Nothing to Undo\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "Failed to undo: Nothing to Undo\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "Failed to undo: Nothing to Undo\n", "undo \n undo \n undo"
      ));
    }

    @Test
    public void testUndoWhenCanUndo() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "created a new programmatic image of a purenoise\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "applying operations: [Sepia]\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "successfully undone\n"
              + "\n"
              + " | Visibility: true\n",
          "programmatic purenoise 123 123 12\n apply sepia\n undo"
      ));
    }

    @Test
    public void testUndoAfterBadInput() {
      assertTrue( // ignored input
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n",
              "bla undo"
          )
      );
    }

    /**
     * Tests for the "redo" command.
     */
    @Test
    public void testRedoWhenCantRedo() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "Failed to redo: Nothing to Redo\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "Failed to redo: Nothing to Redo\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "Failed to redo: Nothing to Redo\n", "redo \n redo \n redo"
      ));
    }

    @Test
    public void testRedoWhenCanRedo() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "created a new programmatic image of a purenoise\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "applying operations: [Sepia]\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "successfully undone\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "successfully redone\n"
              + "\n"
              + " | Visibility: true\n",
          "programmatic purenoise 123 123 12\n apply sepia\n undo\n redo"
      ));
    }

    @Test
    public void testRedoAfterBadInput() {
      assertTrue( // ignored input
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n",
              "bla redo"
          )
      );
    }


    /**
     * Tests for the "save" command.
     */
    @Test
    public void testSave() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "saved current image to image history\n"
                  + "\n"
                  + " | Visibility: true\n",
              "save"
          )
      );
    }

    @Test
    public void testSaveAfterBadInputIgnored() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n",
              "bla save"
          )
      );
    }


    /**
     * Tests for the "import" command.
     */
    @Test
    public void testImportLayersNoFileFound() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "",
              "import JPEG layers notAFile.txt"
          )
      );
    }

    @Test
    public void testImportLayersFileFound() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "importing from JPEG file layers...\n" // FIXME
                  + "\n"
                  + "failed to import from JPEG file at path layers: Failed to read Image\n",
              "import JPEG layers res/exampleExportedLayersFile.txt"
          )
      );
    }


    @Test
    public void testImportJPEGCommandSuccessfulUsedPathName() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n",
          "import JPEG res/rover.jpg" // exists
      ));
    }


    @Test
    public void testImportPPMCommandSuccessfulUsedPathName() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from PPM file res/rover.ppm...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n",
          "import PPM res/rover.ppm" // exists
      ));
    }


    @Test
    public void testImportPNGCommandSuccessfulUsedPathName() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from PNG file res/rover.png...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n",
          "import PNG res/rover.png" // exists
      ));
    }


    @Test
    public void testImportCommandUnsuccessfulDidntUsedPathName() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file rover.jpg...\n"
              + "\n"
              + "failed to import from JPEG file at path rover.jpg: Failed to read Image\n"
              + "\n"
              + " | Visibility: true\n",
          "import JPEG rover.jpg" // does not exist
      ));
    }

    @Test
    public void testImportCommandUnsuccessfulForgotExtension() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover...\n"
              + "\n"
              + "failed to import from JPEG file at path res/rover: Failed to read Image\n",
          "import JPEG res/rover"
      ));
    }

    @Test
    public void testImportCommandUnsuccessfulMismatchedFileType() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from PNG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n",
          "import PNG res/rover.jpg"
      ));
    }

    @Test
    public void testImportCommandUnsuccessfulNonExistentFileType() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "invalid file format: \"FOO\"\n"
              + "\n"
              + " | Visibility: true\n",
          "import FOO res/rover.jpg"
      ));
    }


    @Test
    public void testImportCommandUnsuccessfulNoFileFormatGiven() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n",
          "import res/rover.jpg"
      ));
    }

    @Test
    public void testImportCommandUnsuccessfulNoPathGiven() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + " | Visibility: true\n",
          "import JPEG"
      ));
    }


    @Test
    public void testImportCommandSuccessfulUsedPathName() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n",
          "import JPEG res/rover.jpg"
      ));
    }


    /**
     * Tests for the "export" command.
     */
    @Test
    public void testExportJPEGCommandSuccessful() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "exporting to JPEG file res/rover...\n"
              + "\n"
              + "should've exported by now\n",
          "import JPEG res/rover.jpg\n export JPEG res/rover"
      ));
    }

    @Test
    public void testExportAllLayers() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "created a new programmatic image of a purenoise\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "setting the current working layer to layer #1\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "created a new programmatic image of a bwnoise\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "exporting to PNG file layers...\n"
                  + "\n"
                  + "should've exported by now\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n",
              "new \n programmatic purenoise 30 30 1 \n current 1 \n programmatic "
                  + "bwnoise 30 30 1 \n export PNG layers"
          )
      );
    }

    @Test
    public void testExportPPMCommandSuccessful() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "exporting to PPM file res/rover...\n"
              + "\n"
              + "should've exported by now\n"
              + "\n"
              + " | Visibility: true\n",
          "import JPEG res/rover.jpg\n export PPM res/rover"
      ));
    }


    @Test
    public void testExportPNGCommandSuccessful() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "exporting to PNG file res/rover...\n"
              + "\n"
              + "should've exported by now\n",
          "import JPEG res/rover.jpg\n export PNG res/rover"
      ));
    }

    @Test
    public void testExportCommandSuccessfulWhenForgotExtension() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "exporting to JPEG file res/rover...\n"
              + "\n"
              + "should've exported by now\n",
          "import JPEG res/rover.jpg\n export JPEG res/rover"
      ));
    }

    @Test
    public void testExportCommandUnsuccessfulNonExistentFileType() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "invalid file format: \"FOO\"\n"
              + "\n"
              + " | Visibility: true\n",
          "import JPEG res/rover.jpg\n export FOO res/rover"
      ));
    }


    @Test
    public void testExportCommandUnsuccessfulNoFileFormatGiven() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n",
          "import JPEG res/rover.jpg\n export res/rover"
      ));
    }

    @Test
    public void testExportCommandUnsuccessfulNoPathGiven() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "importing from JPEG file res/rover.jpg...\n"
              + "\n"
              + "successfully imported!\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + " | Visibility: true\n",
          "import JPEG res/rover.jpg\n export JPEG"
      ));
    }


    /**
     * Tests for the "apply" command.
     */
    @Test
    public void testApplyAllCommandsSuccessfully() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "importing from JPEG file res/rover.jpg...\n"
                  + "\n"
                  + "successfully imported!\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "applying operations: [Sepia, Greyscale, ImageBlur, Sharpening]\n"
                  + "\n"
                  + " | Visibility: true\n",
              "import JPEG res/rover.jpg\n apply sepia greyscale blur sharpen"
          )
      );
    }

    @Test
    public void testApplyAllCommandsSuccessfullySkippingInvalidCommands() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "importing from JPEG file res/rover.jpg...\n"
                  + "\n"
                  + "successfully imported!\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "did not recognize operation \"foo\"\n"
                  + "\n"
                  + "did not recognize operation \"bar\"\n"
                  + "\n"
                  + "did not recognize operation \"baz\"\n"
                  + "\n"
                  + "did not recognize operation \"quux\"\n"
                  + "\n"
                  + "applying operations: [Sepia, Greyscale, ImageBlur, Sharpening]\n"
                  + "\n"
                  + " | Visibility: true\n",
              "import JPEG res/rover.jpg\n apply foo sepia bar greyscale baz blur quux "
                  + "sharpen"
          )
      );
    }

    @Test
    public void testApplyAllCommandsSuccessfullyButStopsWhenSeesRepeatApplyOnSameLine() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "importing from JPEG file res/rover.jpg...\n"
                  + "\n"
                  + "successfully imported!\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "did not recognize operation \"apply\"\n"
                  + "\n"
                  + "applying operations: [Sepia, Greyscale, ImageBlur, Sharpening]\n",
              "import JPEG res/rover.jpg\n apply sepia greyscale apply blur sharpen"
          )
      );
    }

    @Test
    public void testApplyAllCommandsSuccessfullyButContinuesWhenSeesRepeatApplyOnNextLine() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "importing from JPEG file res/rover.jpg...\n"
                  + "\n"
                  + "successfully imported!\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "applying operations: [Sepia, Greyscale]\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "applying operations: [ImageBlur, Sharpening]\n"
                  + "\n"
                  + " | Visibility: true\n",
              "import JPEG res/rover.jpg\n apply sepia greyscale\n apply blur sharpen"
          )
      );
    }

    @Test
    public void testApplyNoArgs() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "importing from JPEG file res/rover.jpg...\n"
                  + "\n"
                  + "successfully imported!\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "applying operations: []\n"
                  + "\n"
                  + "could not apply operations: Invalid operations\n",
              "import JPEG res/rover.jpg\n apply"
          )
      );
    }

    @Test
    public void testApplyInvalidArgs() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "importing from JPEG file res/rover.jpg...\n"
                  + "\n"
                  + "successfully imported!\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "did not recognize operation \"asdjfo;uiasedhr\"\n"
                  + "\n"
                  + "did not recognize operation \"234123423\"\n"
                  + "\n"
                  + "applying operations: []\n"
                  + "\n"
                  + "could not apply operations: Invalid operations\n"
                  + "\n"
                  + " | Visibility: true\n",
              "import JPEG res/rover.jpg\n apply asdjfo;uiasedhr  234123423"
          )
      );
    }


    /**
     * Tests for the "visibility" command.
     */
    @Test
    public void testToggleVisibilityBadInput() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "could not toggle visibility of layer \"asdfg\": layers must be an integer in "
                  + "valid range of the number of present layers\n"
                  + "\n"
                  + " | Visibility: true\n",
              "visibility asdfg"
          )
      );
    }

    @Test
    public void testToggleVisibilityBadLayerNumber() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "toggling visibility of layer -1\n"
                  + "\n"
                  + "could not toggle visibility of layer -1: Layer Index out of bounds\n"
                  + "\n"
                  + " | Visibility: true\n",
              "visibility -1"
          )
      );
    }

    @Test
    public void testToggleVisibilityValidLayer() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "toggling visibility of layer 0\n"
                  + "\n"
                  + "toggled!\n"
                  + "\n"
                  + " | Visibility: false\n",
              "visibility 0"
          )
      );
    }

    /**
     * Tests for the "current" command.
     */
    @Test
    public void testCurrentInvalidLayerName() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "invalid layer number: klasdfj: provide a number\n",
              "current klasdfj"
          )
      );
    }

    @Test
    public void testCurrentCurrentLayerName() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "setting the current working layer to layer #0\n"
                  + "\n"
                  + " | Visibility: true\n",
              "current 0"
          )
      );
    }

    @Test
    public void testCurrentInvalidLayerNumber() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "setting the current working layer to layer #8\n"
                  + "\n"
                  + "invalid layer number: 8, out of bounds for layers 0-0\n"
                  + "\n"
                  + " | Visibility: true\n",
              "current 8"
          )
      );
    }


    /**
     * Tests for the "delete" command.
     */

    @Test
    public void testDeleteInvalidLayer() {
      assertTrue(this.utilityTestViewOutputFromController(
          "\n"
              + "Welcome to Image Manipulation and Enhancement! \n"
              + "Please consult the USEME file for information on how to specify commands\n"
              + "\n"
              + " | Visibility: true\n"
              + "\n"
              + "Illegal index bla. Must be an integer in the inclusive range [0,1]\n",
          "delete bla"
      ));
    }

    @Test
    public void testDeleteOnlyLayer() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "deleting layer 0\n"
                  + "\n"
                  + "could not delete layer 0: Cannot delete last layer\n"
                  + "\n"
                  + " | Visibility: true\n",
              "delete 0"
          )
      );
    }

    @Test
    public void testDeleteOtherLayer() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "deleting layer 1\n",
              "new \n delete 1"
          )
      );
    }

    @Test
    public void testDeleteInvalidLayerNumber() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "deleting layer 1000\n"
                  + "\n"
                  + "could not delete layer 1000: Layer Index out of bounds\n"
                  + "\n"
                  + " | Visibility: true\n",
              "delete 1000"
          )
      );
    }


    /**
     * Tests for the "swap" command.
     */
    @Test
    public void testSwapLayerWithItself() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "swapping layers 1 and 1\n"
                  + "\n"
                  + "could not swap layers at indices 1 and 1: Layer Indexes invalid\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n",
              "new \n swap 1 1"
          )
      );
    }

    @Test
    public void testSwapLayerWithOther() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "swapping layers 0 and 1\n"
                  + "\n"
                  + "swapped!\n",
              "new \n swap 0 1"
          )
      );
    }

    @Test
    public void testSwapLayersOnlyOneInput() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + " | Visibility: true\n",
              "swap 0"
          )
      );
    }

    @Test
    public void testSwapLayerTooManyInputsThirdOnwardGetsIgnored() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "swapping layers 0 and 1\n"
                  + "\n"
                  + "swapped!\n",
              "new \n swap 0 1 1 1 0 10 100 "
          )
      );
    }

    /**
     * Tests for the "new" command.
     */
    @Test
    public void testNewLayerBeforeDeleting() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n",
              "new"
          )
      );
    }

    @Test
    public void testNewLayerAfterDeleting() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 2\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "deleting layer 1\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 2\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n",
              "new \n new \n delete 1 \n new"
          )
      );
    }


    /**
     * Tests for the "programmtic" command.
     */
    @Test
    public void testProgrammaticCheckerboardValidInputs() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "created a new programmatic image of a checkerboard\n"
                  + "\n"
                  + " | Visibility: true\n",
              "programmatic checkerboard 100 100 10"
          )
      );
    }

    @Test
    public void testProgrammaticPureNoiseValidInputs() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "created a new programmatic image of a purenoise\n"
                  + "\n"
                  + " | Visibility: true\n",
              "programmatic purenoise 12 12 1"
          )
      );
    }

    @Test
    public void testProgrammaticBWNoiseValidInputs() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "created a new programmatic image of a bwnoise\n"
                  + "\n"
                  + " | Visibility: true\n",
              "programmatic bwnoise 90 1000 12"
          )
      );
    }

    @Test
    public void testProgrammaticRainbowNoiseValidInputs() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "created a new programmatic image of a rainbownoise\n"
                  + "\n"
                  + " | Visibility: true\n",
              "programmatic rainbownoise 123 123 12"
          )
      );
    }

    @Test
    public void testProgrammaticImageCommandNotEnoughParameters() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "invalid arguments passed to programmatic command, try again\n"
                  + "\n"
                  + " | Visibility: true\n",
              "programmatic rainbownoise 12"
          )
      );
    }

    /**
     * Testing an entire script.
     */
    @Test
    public void testExampleScript1() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 2\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "setting the current working layer to layer #2\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "created a new programmatic image of a bwnoise\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "setting the current working layer to layer #1\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "importing from PPM file res/rover.ppm...\n"
                  + "\n"
                  + "successfully imported!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "deleting layer 0\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "applying operations: [Sepia, Sharpening, Greyscale, ImageBlur, Sharpening]\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "exporting to JPEG file layers...\n"
                  + "\n"
                  + "should've exported by now\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "successfully undone\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "successfully redone\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "Failed to redo: Nothing to Redo\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "swapping layers 0 and 1\n"
                  + "\n"
                  + "swapped!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "deleting layer 0\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "deleting layer 0\n"
                  + "\n"
                  + "could not delete layer 0: Cannot delete last layer\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "importing from JPEG file layers...\n"
                  + "\n"
                  + "failed to import from JPEG file at path layers: Failed to read Image\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "deleting layer 0\n"
                  + "\n"
                  + "could not delete layer 0: Cannot delete last layer\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "deleting layer 0\n"
                  + "\n"
                  + "could not delete layer 0: Cannot delete last layer\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "invalid arguments passed to programmatic command, try again\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "setting the current working layer to layer #1\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "invalid arguments passed to programmatic command, try again\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 2\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "setting the current working layer to layer #2\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "invalid arguments passed to programmatic command, try again\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "saved current image to image history\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "toggling visibility of layer 1\n"
                  + "\n"
                  + "toggled!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: false | Visibility: true\n"
                  + "\n"
                  + "setting the current working layer to layer #0\n"
                  + "\n"
                  + " | Visibility: true | Visibility: false | Visibility: true\n"
                  + "\n"
                  + "exporting to PNG file res/finalImage-Rover...\n"
                  + "\n"
                  + "should've exported by now\n"
                  + "\n"
                  + " | Visibility: true | Visibility: false | Visibility: true\n",
              "new \n"
                  + "new \n"
                  + "current 2 \n"
                  + "programmatic bwnoise 474 270 20\n"
                  + "current 1\n"
                  + "import PPM res/rover.ppm\n"
                  + "delete 0\n"
                  + "apply sepia sharpen greyscale blur sharpen\n"
                  + "export JPEG layers\n"
                  + "undo\n"
                  + "redo\n"
                  + "redo\n"
                  + "swap 0 1\n"
                  + "delete 0\n"
                  + "delete 0\n"
                  + "import JPEG layers res/exampleLayers\n"
                  + "delete 0\n"
                  + "delete 0\n"
                  + "programmatic rainbownoise 100 100 10\n"
                  + "new\n"
                  + "current 1\n"
                  + "programmatic purenoise 100 100 10\n"
                  + "new\n"
                  + "current 2\n"
                  + "programmatic checkerboard 100 100 10\n"
                  + "save\n"
                  + "visibility 1\n"
                  + "current 0\n"
                  + "export PNG res/finalImage-Rover\n"
          )
      );
    }

    @Test
    public void testExampleScript2Bogus() {
      assertTrue(
          this.utilityTestViewOutputFromController(
              "\n"
                  + "Welcome to Image Manipulation and Enhancement! \n"
                  + "Please consult the USEME file for information on how to specify commands\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "deleting layer 100\n"
                  + "\n"
                  + "could not delete layer 100: Layer Index out of bounds\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "setting the current working layer to layer #12\n"
                  + "\n"
                  + "invalid layer number: 12, out of bounds for layers 0-0\n"
                  + "\n"
                  + " | Visibility: true\n"
                  + "\n"
                  + "adding new layer at index 1\n"
                  + "\n"
                  + "added!\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "Illegal index anms. Must be an integer in the inclusive range [0,2]\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "did not recognize operation \"dog\"\n"
                  + "\n"
                  + "did not recognize operation \"cat\"\n"
                  + "\n"
                  + "applying operations: [Sepia]\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "did not recognize operation \"banana\"\n"
                  + "\n"
                  + "applying operations: []\n"
                  + "\n"
                  + "could not apply operations: Invalid operations\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "saved current image to image history\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "deleting layer -2\n"
                  + "\n"
                  + "could not delete layer -2: Layer Index out of bounds\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "invalid file format: \"TXT\"\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "invalid file format: \"jpeg\"\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "exporting to PPM file directoryDoesntExist...\n"
                  + "\n"
                  + "should've exported by now\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "swapping layers 0 and 2\n"
                  + "\n"
                  + "could not swap layers at indices 0 and 0: Layer Indexes invalid\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "could not toggle visibility of layer \"kkkkkkk\": layers must be an integer in"
                  + " valid range of the number of present layers\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "successfully undone\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "successfully redone\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "invalid arguments passed to programmatic command, try again\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n"
                  + "\n"
                  + "created a new programmatic image of a purenoise\n"
                  + "\n"
                  + " | Visibility: true | Visibility: true\n",
              "delete 100\n"
                  + "current 12\n"
                  + "new foo\n"
                  + "delete anms\n"
                  + "apply dog cat sepia\n"
                  + "apply banana\n"
                  + "save -2\n"
                  + "delete -2\n"
                  + "export TXT notGonnaWork.txt\n"
                  + "import jpeg layers notGonnaWorkShouldveBeenCapitalized\n"
                  + "export PPM directoryDoesntExist\n"
                  + "swap 0 2\n"
                  + "visibility kkkkkkk\n"
                  + "undo undo\n"
                  + "redo redo redo redo\n"
                  + "notACommand\n"
                  + "programmatic programmatic checkerboard 400 400 12\n"
                  + "programmatic purenoise 12 12 312 213 1231 2312 123\n"
          )
      );
    }


    /**
     * Utility method to help abstract testing for the correct feedback being logged by the
     * controller and mock.
     *
     * @param expected    The expected I/O output messages.
     * @param inputToTest mock input from the controller.
     * @return whether the expected output is the real output.
     */
    private boolean utilityTestViewOutputFromController(String expected, String inputToTest) {
      Utility.checkNotNull(expected, "expected message cannot be null");
      Utility.checkNotNull(inputToTest, "checked message cannot be null");
      Appendable out = new StringBuilder();
      Readable in = new StringReader(inputToTest);

      IMultiLayerIMEController ctrlr =
          MultiLayerIMEControllerImpl.controllerBuilder().model(new MultiLayerModelImpl())
              .appendable(out).readable(in).buildController();

      ctrlr.run();

      assertEquals(expected, out.toString());

      return expected.equals(out.toString());
    }
  }


  /**
   * Class for testing the Multilayer model implementation.
   */
  public class MultiLayerModelTest {

    private IMultiLayerModel model;
    private IImage image;
    private IImage diffColorImage;

    @Before
    public void init() {
      this.model = new MultiLayerModelImpl();
      this.image = new ImageImpl(new MatrixImpl<>(new PixelImpl(255, 255, 255), 10, 10));
      this.diffColorImage = new ImageImpl(new MatrixImpl<>(new PixelImpl(100, 100, 100), 10, 10));
    }

    // applyOperations
    @Test
    public void testOperations() {
      this.model.load(image);
      this.model.applyOperations(new ImageBlur());
      assertNotEquals(this.image, this.model.getImage());
    }

    // load
    @Test
    public void testLoad() {
      this.model.load(this.image);
      assertEquals(10, this.model.getImage().getHeight());
      assertEquals(10, this.model.getImage().getWidth());
      assertEquals(this.image, this.model.getImage());
    }

    // setProgramImage
    @Test
    public void testProgramImage() {
      this.model.load(this.image);
      this.model.addLayer();
      this.model.setProgrammaticImage(new Checkerboard(), 10, 10, 1);
      IImage checkerBoard = new Checkerboard().createProgramImage(10, 10, 1);
      assertEquals(checkerBoard, this.model.getImage());
    }

    // getImage
    @Test
    public void testGetImage() {
      this.model.load(this.image);
      assertEquals(this.image, this.model.getImage());
    }

    // toggleInvisible
    @Test
    public void testInvisible() {
      this.model.load(this.image);
      this.model.toggleInvisible(0);
      assertTrue(this.model.getLayers().get(0).isInvisible());
    }

    // setCurrentLayer
    @Test
    public void testSetCurrentLayer() {
      this.model.addLayer();
      assertEquals(2, this.model.getLayers().size());
      this.model.load(diffColorImage);
      assertEquals(diffColorImage, this.model.getImage());
      this.model.setCurrentLayer(1);
      this.model.load(this.image);
      assertEquals(this.image, this.model.getImage());
    }

    // addLayer and deleteLayer
    @Test
    public void testAddLayer() {
      this.model.load(this.image);
      this.model.addLayer();
      this.model.addLayer();
      assertEquals(3, this.model.getLayers().size());
      this.model.deleteLayer(0);
      assertEquals(2, this.model.getLayers().size());
    }

    // swapLayers
    @Test
    public void testSwapLayers() {
      this.model.load(this.image);
      this.model.addLayer();
      this.model.setCurrentLayer(1);
      this.model.load(diffColorImage);
      this.model.setCurrentLayer(0);
      this.model.swapLayers(0, 1);
      assertEquals(this.diffColorImage.getPixelArray(), this.model.getImage().getPixelArray());
      this.model.setCurrentLayer(1);
      assertEquals(this.image.getPixelArray(), this.model.getImage().getPixelArray());

    }

    // getLayers
    @Test
    public void getLayersShouldReturnList() {
      assertEquals(1, model.getLayers().size());
      model.addLayer();
      model.addLayer();
      model.addLayer();
      assertEquals(4, model.getLayers().size());
      model.deleteLayer(3);
      assertEquals(3, model.getLayers().size());
    }

    // undo and redo
    @Test
    public void testUndoAndRedo() {
      this.model.load(image);
      this.model.applyOperations(new Greyscale());
      this.model.undo();
      assertEquals(this.image, this.model.getImage());
      this.model.redo();
      IImage greyScaleImage = new Greyscale().apply(this.image);
      assertEquals(greyScaleImage, this.model.getImage());
    }

    // save
    @Test
    public void testSave() {
      this.model.load(image);
      this.model.save();
      assertEquals(this.image, this.model.getImage());
    }

    // copy
    @Test
    public void testCopy() {
      this.model.load(image);
      this.model.addLayer();
      this.model.addLayer();
      IMultiLayerModel modelCopy = (IMultiLayerModel) this.model.copy();
      assertEquals(this.model.getImage(), modelCopy.getImage());
      assertEquals(this.model.getLayers().size(), modelCopy.getLayers().size());
    }

    // Exceptions
    // applyOperations gets invalid operation
    @Test(expected = IllegalArgumentException.class)
    public void applyOpGivenNull() {
      this.model.load(image);
      this.model.applyOperations(null);
    }

    // load gets null image
    @Test(expected = IllegalArgumentException.class)
    public void loadGivenNull() {
      this.model.load(null);
    }

    // setProgramImage gets invalid image or different size image on another layer
    @Test(expected = IllegalArgumentException.class)
    public void setProgramImageGivenNull() {
      this.model.setProgrammaticImage(null, 10, 10, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setProgramImageGivenInvalidSize() {
      this.model.load(image);
      this.model.addLayer();
      this.model.setCurrentLayer(1);
      this.model.setProgrammaticImage(new Checkerboard(), 12, 12, 1);
    }

    // toggleInvisible is given invalid layerIndex
    @Test(expected = IllegalArgumentException.class)
    public void toggleInvisibleGivenInvalidIndex() {
      this.model.toggleInvisible(1);
    }

    // setCurrentLayer is given invalid layerIndex
    @Test(expected = IllegalArgumentException.class)
    public void setCurrentLayerGivenInvalidIndex() {
      this.model.setCurrentLayer(1);
    }

    // deleteLayer is given invalid layerIndex
    @Test(expected = IllegalArgumentException.class)
    public void deleteLayerGivenInvalidIndex() {
      this.model.deleteLayer(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteOnlyLayer() {
      this.model.deleteLayer(0);
    }

    // swapLayers is given invalid layerIndexes or same index
    @Test(expected = IllegalArgumentException.class)
    public void swapLayersGivenInvalidIndex() {
      this.model.load(image);
      this.model.addLayer();
      this.model.setCurrentLayer(1);
      this.model.load(this.diffColorImage);
      this.model.swapLayers(0, 2);
    }

    // undo fails
    @Test(expected = IllegalArgumentException.class)
    public void undoNotPossible() {
      this.model.load(this.image);
      this.model.undo();
    }

    // redo fails
    @Test(expected = IllegalArgumentException.class)
    public void redoNotPossible() {
      this.model.load(this.image);
      this.model.redo();
    }

  }


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


  /**
   * Tests for methods in the {@link cs3500.model.StateTrackingIMEModelImpl} class.
   */
  public class StateTrackingIMEModelImplTest {

    private final IImage simple3x3 =
        new ImageImpl(new MatrixImpl<>(new ArrayList<>(Arrays.asList(
            PixelImpl.RED,
            PixelImpl.GREEN,
            PixelImpl.BLUE)), 3));


    /**
     * Tests for the {@link StateTrackingIMEModelImpl#StateTrackingIMEModelImpl()} method.
     */
    @Test
    public void testNullaryConstructorSetsEmptyImage() {
      assertEquals(new ImageImpl(new MatrixImpl<>()),
          new StateTrackingIMEModelImpl().getImage());
    }

    /**
     * Tests for the
     * {@link StateTrackingIMEModelImpl#StateTrackingIMEModelImpl(cs3500.model.image.IImage)}
     * constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testImageConstructorThrowsWhenPassedNullImage() {
      new StateTrackingIMEModelImpl(null);
    }

    /**
     * Tests for the {@link StateTrackingIMEModelImpl#applyOperations(IOperation...)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testApplyOperationsThrowsWhenOperationsNull() {
      new StateTrackingIMEModelImpl().applyOperations(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyOperationsThrowsWhenOperationsHasNull() {
      new StateTrackingIMEModelImpl().applyOperations(new ImageBlur(), null);
    }

    @Test
    public void testApplyOperationsActuallyChangesImage() {
      IStateTrackingIMEModel mdl = new StateTrackingIMEModelImpl(simple3x3);
      IImage oldImage = mdl.getImage().copy();
      mdl.applyOperations(new ImageBlur(), new Sepia());
      IImage newImage = mdl.getImage().copy();

      assertNotEquals(newImage, oldImage);
    }

    /**
     * Tests for the {@link StateTrackingIMEModelImpl#undo()} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUndoThrowsWhenNothingToUndo() {
      IStateTrackingIMEModel m = new StateTrackingIMEModelImpl();
      m.undo();
    }

    @Test
    public void testUndoUndoesOperation() {
      IStateTrackingIMEModel m =
          new StateTrackingIMEModelImpl();
      m.setProgrammaticImage(new RainbowNoise(), 10, 10, 1);
      IImage originalImage = m.getImage();
      m.applyOperations(new Sepia());
      m.undo();
      IImage undoneImage = m.getImage();

      assertEquals(undoneImage, originalImage);
    }

    @Test
    public void testUndoTwice() {
      IStateTrackingIMEModel m =
          new StateTrackingIMEModelImpl();
      m.setProgrammaticImage(new RainbowNoise(), 10, 10, 1);
      IImage originalImage = m.getImage();
      m.applyOperations(new Sepia(), new Sharpening());
      m.undo();
      m.undo();
      IImage undoneImage = m.getImage();

      assertEquals(undoneImage, originalImage);
    }

    /**
     * Tests for the {@link StateTrackingIMEModelImpl#redo()} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRedoThrowsWhenNothingToRedo() {
      IStateTrackingIMEModel m = new StateTrackingIMEModelImpl();
      m.redo();
    }

    @Test
    public void testRedoRedoesOperation() {
      IStateTrackingIMEModel m =
          new StateTrackingIMEModelImpl();
      m.setProgrammaticImage(new RainbowNoise(), 10, 10, 1);
      m.applyOperations(new Sepia());
      IImage originalImage = m.getImage();
      m.undo();
      m.redo();
      IImage redoneImage = m.getImage();

      assertEquals(redoneImage, originalImage);
    }

    @Test
    public void testRedoTwice() {
      IStateTrackingIMEModel m =
          new StateTrackingIMEModelImpl();
      m.setProgrammaticImage(new RainbowNoise(), 10, 10, 1);
      IImage originalImage = m.getImage();
      m.applyOperations(new Sepia(), new Sharpening());
      m.undo();
      m.undo();
      m.redo();
      m.redo();
      IImage redoneImage = m.getImage();

      assertEquals(redoneImage, originalImage);
    }

    /**
     * Tests for the {@link StateTrackingIMEModelImpl#save()} method.
     */
    @Test
    public void testSaveSavesNewCopy() {
      IStateTrackingIMEModel m = new StateTrackingIMEModelImpl();
      m.setProgrammaticImage(new Checkerboard(), 10, 10, 2);
      IImage oldImage = m.getImage();
      m.save();
      IImage savedImage = m.getImage();

      assertEquals(oldImage, savedImage);
      assertFalse(oldImage == savedImage);
    }

    /**
     * Tests for the {@link StateTrackingIMEModelImpl#getImage()} method.
     */
    @Test
    public void testgetImageReturnsEqualCopyNotReference() {
      IStateTrackingIMEModel m =
          new StateTrackingIMEModelImpl();
      IImage emptyImage = new ImageImpl(new MatrixImpl<>());
      assertEquals(emptyImage, m.getImage());
      assertFalse(emptyImage == m.getImage());
    }

    /**
     * Helper to appease the almighty style checker.
     *
     * @return true, to appease the almighty style checker.
     */
    private boolean testDelegateHelp() {
      return true;
    }


  

    /**
     * Tests for the {@link StateTrackingIMEModelImpl#setProgrammaticImage(IProgramImage, int, int,
     * int)} method.
     */
    @Test
    public void testSetProgrammaticImageChangesImage() {
      IStateTrackingIMEModel m = new StateTrackingIMEModelImpl();
      m.setProgrammaticImage(new Checkerboard(), 2, 2, 1);
      assertEquals(new ImageImpl(
              new MatrixImpl<>(
                  new ArrayList<>(
                      Arrays.asList(
                          new ArrayList<>(
                              Arrays.asList(
                                  PixelImpl.BLACK,
                                  PixelImpl.WHITE)),
                          new ArrayList<>(
                              Arrays.asList(
                                  PixelImpl.WHITE,
                                  PixelImpl.BLACK)))))),
          m.getImage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetProgrammaticImageThrowsWhenGivenNullProgrammaticImage() {
      IStateTrackingIMEModel m = new StateTrackingIMEModelImpl();
      m.setProgrammaticImage(null, 2, 2, 2);
    }
  }


  /**
   * Tests for methods in the {@link Utility} class.
   */
  public class UtilityTest {

    /**
     * Tests for the {@link Utility#checkNotNull(Object, String)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNotNullOnNullInputThrowsIllegalArg() {
      Object imNull = null;
      Utility.checkNotNull(imNull, "message to be thrown");
    }

    @Test
    public void testCheckNotNullOnNullInputThrowsCorrectErrorMsg() {
      Object imNull = null;
      try {
        Utility.checkNotNull(imNull, "sPeCiFiC mEsSaGe");
      } catch (IllegalArgumentException e) {
        assertEquals("sPeCiFiC mEsSaGe", e.getMessage());
      }
    }

    @Test
    public void testCheckNotNullReturnsNotNullObject() {
      Object imNotNull = new String();
      assertEquals(new String(), Utility.checkNotNull(imNotNull, "irrelevant"));
    }

    /**
     * Tests for the {@link Utility#checkNotNullListContents(List, String)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotNullListContentsThrowsForNullList() {
      List imNullDabaDeeDabaDai = null;
      Utility.checkNotNullListContents(imNullDabaDeeDabaDai,
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

    @Test(expected = IllegalArgumentException.class)
    public void testCheckNotNullListContentsThrowsForNullListContents() {
      List iHaveOneNullElements = new ArrayList<Integer>(Arrays.asList(1, 2, null, null, 3));
      Utility.checkNotNullListContents(iHaveOneNullElements, "whats the deal with airline "
          + "food?");
    }

    @Test
    public void testCheckNotNullListContentsDoesntThrowForEmptyList() {
      List imEmptyInside = new ArrayList();

      assertEquals(0,
          Utility.checkNotNullListContents(imEmptyInside, "irrelevant").size());

    }


    /**
     * Tests for the {@link Utility#checkIntBetween(int, int, int)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckIntBetweenThrowsWhenIntLessThanLowerBound() {
      int lowerBound = 0;
      int upperBound = 10;
      int toCheck = -1;

      Utility.checkIntBetween(toCheck, lowerBound, upperBound);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckIntBetweenThrowsWhenIntGreaterThanLowerBound() {
      int lowerBound = 0;
      int upperBound = 10;
      int toCheck = 11;

      Utility.checkIntBetween(toCheck, lowerBound, upperBound);
    }

    @Test
    public void testCheckIntBetweenReturnsWhenIntAtLowerBound() {
      int lowerBound = 0;
      int upperBound = 10;
      int toCheck = lowerBound;

      assertEquals(0,
          Utility.checkIntBetween(toCheck, lowerBound, upperBound));
    }

    @Test
    public void testCheckIntBetweenReturnsWhenIntAtUpperBound() {
      int lowerBound = 0;
      int upperBound = 10;
      int toCheck = upperBound;

      assertEquals(10,
          Utility.checkIntBetween(toCheck, lowerBound, upperBound));
    }

    @Test
    public void testCheckIntBetweenReturnsWhenIntClearlyInBounds() {
      int lowerBound = 0;
      int upperBound = 10;
      int toCheck = 5;

      assertEquals(5,
          Utility.checkIntBetween(toCheck, lowerBound, upperBound));
    }

    /**
     * Tests for the {@link Utility#println(String)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPrintlnWithNullStringThrowsIllegalArg() {
      Utility.println(null);
    }

    @Test
    public void testPrintlnEmptyString() {
      assertEquals("\n", Utility.println(""));
    }

    @Test
    public void testPrintlnNonEmptyString() {
      assertEquals("words...\n", Utility.println("words..."));
    }

    /**
     * Tests for the {@link Utility#paddedPrint(String)} method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testpaddedPrintWithNullStringThrowsIllegalArg() {
      Utility.paddedPrint(null);
    }

    @Test
    public void testpaddedPrintEmptyString() {
      assertEquals("\n\n", Utility.paddedPrint(""));
    }

    @Test
    public void testpaddedPrintNonEmptyString() {
      assertEquals("\nwords...\n", Utility.paddedPrint("words..."));
    }


  }


}
