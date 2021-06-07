package CS3500.model.matrix;

// TODO: add new methods

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A generic matrix of values. Provides greater flexibility than just using a 2D array list to store
 * a grid of values, and supports the ability to:
 * <ul>
 *   <li>
 *     Query a cell for its value
 *   </li>
 *   <li>
 *     Add two matrices together
 *   </li>
 *   <li>
 *     Element-wise multiply two matrices together
 *   </li>
 *   <li>
 *     Sum all of the values in that matrix
 *   </li>
 *   <li>
 *     Custom, deep equals method and suitable hashCode
 *   </li>
 *   <li>
 *     toString
 *   </li>
 * </ul>
 */
public interface IMatrix<X> { // TODO: JavaDocs

  /**
   * Returns the matrix entry located at the specified logical coordinates, indexed at 0.
   *
   * @param row the row of this matrix that the desired entry is located at, indexed from 0, top to
   *            bottom.
   * @param col the column of this matrix that the desired entry is located at, indexed from 0, left
   *            to right.
   * @return the entry of this matrix located at the specified logical coordinates, indexed from 0.
   * @throws IllegalArgumentException if the given coordinates are out of bounds for the dimensions
   *                                  of this matrix.
   */
  X getElement(int row, int col)
      throws IllegalArgumentException;

  /**
   * Returns the width of this matrix.
   *
   * @return the width of this matrix
   */
  int getWidth();

  /**
   * Returns the height of this matrix.
   *
   * @return the height of this matrix
   */
  int getHeight();

  /**
   * Fills all of the indices of this (pre existing) matrix with a given entry.
   *
   * @param entry the entry to fill every index of this matrix with.
   * @throws IllegalArgumentException if the given entry is {@code null}.
   */
  void fillWith(X entry)
      throws IllegalArgumentException;

  /**
   * Changes the entry at index ({@code row},{@code col}), in logical coordinates, indexed from 0,
   * to the new value {@code newEntry}.
   * @param newEntry the new entry to be added at the given index
   * @param row the index of the row to add the new value to, indexed from 0, top to bottom.
   * @param col the index of the column to add the new value to, indexed from 0, from left to right.
   * @throws IllegalArgumentException if the {@code newEntry} is {@code null}, or
   *         if the specified index is out of bounds for the dimensions of this matrix.
   */
  void updateEntry(X newEntry, int row, int col)
      throws IllegalArgumentException;
//
//  void add(IMatrix<X> toAdd)
//      throws IllegalArgumentException;;
//
//  void rowWiseMultiply(IMatrix<X> toMultiply)
//      throws IllegalArgumentException;

  /**
   * Applies a {@link BiFunction}--a binary operation to two matrices, {@code this} matrixm and
   * the matrix {@code toCombine}, and produces a new matrix with the results. Note that in the case
   * that the {@code binaryOperation}, say <i>*</i>, is not abelian,
   * the abstract row-wise operation is computed in the order {@code this * toCombine}, applying the
   * operation first to the entry of {@code this} matrix, then the supplied one.
   *
   * @param binaryOperation the operation to apply to two elements of the same index in both
   *                        matrices.
   * @param toCombine the matrix to "combine" with {@code this} one under the supplied binary
   *                  operation.
   * @throws IllegalArgumentException if the two matrices are not of the same dimensions.
   */
  IMatrix<X> elementWiseOperation(BiFunction<X, X, X> binaryOperation, IMatrix<X> toCombine)
      throws IllegalArgumentException;

  /**
   * For all entries {@code a} in this matrix, this method applies some unary operator {@code f}
   * to produce a new matrix such that every entry is {@code f(a)}, where {@code f} is a
   * well-defined function with signature {@code F: X -> Y}.
   * @param unaryOperation the operation to be applied to each entry in this matrix.
   * @param <Y> the type of value comprising entries in the resultant matrix.
   * @return the new matrix, where each value of the original matrix has been transformed by the
   *         unary function {@code unaryOperation}.
   */
  <Y> IMatrix<Y> map(Function<X, Y> unaryOperation);

  /**
   * Returns a copy of this matrix.
   * @return a copy of this matrix.
   */
  IMatrix<X> copy();

  /**
   * Reduces a whole matrix to some value by working through all of the values in the matrix two at
   * a time, starting with the {@code base} and the first entry in the matrix
   * (at logical index (0,0)), combining them using the binary operation {@code binaryOperation},
   * then inspecting the next two entries, and recursively doing the same. Once the first element to
   * combine has reached the end of a row, the next--and second element to combine is the first
   * (leftmost) element of the next (below) row.
   *
   * @param operation the {@link BiFunction} representing a well-defined binary operation to combine
   *                  each pair of values with (recursively)
   * @param base the first, base case, element to start the process of combining (as defined above)
   *             with.
   * @return the combined value as defined thoroughly and verbosely above.
   */
  X reduceToVal(BiFunction<X, X, X> operation, X base);

  /**
   * Is {@code this} matrix equal to the object {@code o}? I.e. if {@code o} is a matrix, does it
   * have the same entries at the same indices (and therefore same dimensions) as {@code this}
   * matrix?
   * @param o The {@link Object} to be checked for equality with this matrix as defined verbosely
   *          above.
   * @return the answer to the question posed at the beginning of this JavaDoc.
   */
  boolean equals(Object o);

  /**
   * Returns a hash code to identify the structural identity of this matrix and to compare for
   * equality.
   * @return the hash code unique to the structural identity of this matrix as defined above.
   */
  int hashCode();

  /**
   * Returns a visual representation of this matrix, in the format
   * [a00][w][a01][w][a02][w]...[a0c][n]
   * [a10][w][a11][w][a12][w]...[a1c][n]
   * [a20][w][a21][w][a22][w]...[a2c][n]
   * ...
   * [ar0][w][ar1][w][ar2][w]...[arc][n],
   * where
   * <ul>
   *   <li>
   *     [aij] represents the entry at logical index (i,j) in this matrix, indexed from 0.
   *   </li>
   *   <li>
   *     the matrix is of dimensions {@code cxr}
   *   </li>
   *   <li>
   *     [w] represents a single whitespace character
   *   </li>
   *   <li>
   *     [n] represents a newline
   *   </li>
   * </ul>
   *
   * @return this matrix formatted as described above.
   */
  String toString();

}