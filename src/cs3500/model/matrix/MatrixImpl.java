package cs3500.model.matrix;

import java.util.List;

/**
 * A concrete implementation of a purely mathematical matrix that supports the operations defined in
 * {@link IMatrix}, to be used as a data structure that provides much more convenience in use than a
 * 2D list structure.
 *
 * @param <X> the type of entry in this array.
 */
public class MatrixImpl<X> extends AMatrix<X> {

  /**
   * Constructs an empty matrix.
   */
  public MatrixImpl() {
    super();
  }

  /**
   * Constructs a matrix with some entries given as a 2D list.
   *
   * @param entries the rows of this matrix.
   * @throws IllegalArgumentException if any of the elements in the rows are {@code null}, if the
   *                                  {@code entries} themselves are {@code null}, or if any row in
   *                                  the {@code entries} is not of equal size to the others, to
   *                                  prevent non-rectangular arrays.
   */
  public MatrixImpl(List<List<X>> entries)
      throws IllegalArgumentException {
    super(entries);
  }

  /**
   * Creates a {@link MatrixImpl} given one row, and the amount of times it should be copied, i.e.
   * the height of the matrix.
   *
   * @param oneRow    the row to be copied {@code numCopies} times.
   * @param numCopies the number of copies of the row {@code oneRow} in this {@link MatrixImpl}.
   * @throws IllegalArgumentException if {@code oneRow} is {@code null}, if any of the elements of
   *                                  {@code oneRow} are null, or if {@code numCopies} is negative.
   */
  public MatrixImpl(List<X> oneRow, int numCopies)
      throws IllegalArgumentException {
    super(oneRow, numCopies);
  }

  /**
   * Creates a new {@link MatrixImpl} of size {@code numRows} X {@code numCols}, filled with the
   * entry {@code uniformEntry} at every index.
   *
   * @param uniformEntry the entry to be placed at every index in the resultant matrix.
   * @param numRows      the number of rows in the resultant matrix.
   * @param numCols      the number of columns in the resultant matrix.
   * @throws IllegalArgumentException if {@code uniformEntry} is {@code null}, if {@code numRows} is
   *                                  negative, or if {@code numCols} is negative.
   */
  public MatrixImpl(X uniformEntry, int numRows, int numCols)
      throws IllegalArgumentException {
    super(uniformEntry, numRows, numCols);
  }

  @Override
  protected <Y> IMatrix<Y> factoryMatrix(List<List<Y>> rows) throws IllegalArgumentException {
    return new MatrixImpl<>(rows);
  }
}