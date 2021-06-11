import cs3500.model.matrix.AMatrix;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for methods in the {@link cs3500.model.matrix.AMatrix} class.
 */
public abstract class AbstractMatrixTest {

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
   * given entries.
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
     * Tests for the {@link cs3500.model.matrix.MatrixImpl#MatrixImpl(java.util.List)} constructor.
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




//
//  /**
//   * Tests for the {@link AMatrix#getElement(int, int)} method.
//   */
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  /**
//   * Tests for the {@link AMatrix#getElement(int, int)} method.
//   */
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  /**
//   * Tests for the {@link AMatrix#getElement(int, int)} method.
//   */
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFooThrowsWhen() {
//
//  }
//
//  @Test
//  public void testFoo() {
//
//  }


}


