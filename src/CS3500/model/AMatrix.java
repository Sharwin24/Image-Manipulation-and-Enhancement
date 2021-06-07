package CS3500.model;

import CS3500.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AMatrix<X> implements IAlignableMatrix<X> {

  protected final List<List<X>> entries;

  // INVARIANT: the dimensions of the Lists used to store the matrix entries
  //            are always identical to the dimensions of the physical matrix itself
  //            being represented by this object. Therefore a AMatrix cannot contain
  //            null/Optional entries
  public AMatrix() {
    this.entries = new ArrayList<>();
  }

  /**
   * TODO: JavaDoc and maybe add support for a constructor that takes in a List<List<X>>, not sure
   *       if that's the same as List<X>... varargs
   * @param listEntries
   */
  public AMatrix(List<X>... listEntries) {
    // check that each row and entry are not null
    for (List<X> rowOfEntries : listEntries) {
      Utils.checkNotNull(listEntries, "cannot construct a Matrix with a null row");
      for (X entry : rowOfEntries) {
        Utils.checkNotNull(entry, "cannot construct a Matrix with a null entry");
      }
    }

    if (!this.checkAllRowsSameSize()) {
      throw new IllegalArgumentException("cannot create a Matrix without all "
          + "rows having equal size to ensure dimensions exist");
    }

    // initialize a list for entries to be placed in
    this.entries = new ArrayList<>();

    for (List<X> row : listEntries) {
      this.entries.add(row);
    }
  }


  /**
   * TODO
   *
   * @param listEntries
   * @return
   * @throws IllegalArgumentException
   */
  protected static boolean checkAllRowsSameSize(List... listEntries)
      throws IllegalArgumentException {

    Utils.checkNotNull(listEntries, "cannot verify all rows same size for null"
        + " list entries");

    // fast check
    if (listEntries.length == 0) {
      return true; // trivially all rows are same size when there are none
    }

    boolean allRowsSameSize = true;

    for (int row = 0; row < listEntries.length - 1; row++) {
      allRowsSameSize &= listEntries[row].equals(listEntries[row + 1]);
    }

    return allRowsSameSize;
  }

  @Override
  public X getElement(int row, int col)
      throws IllegalArgumentException {
    this.checkIndicesInBounds(row, col);

    return entries.get(row).get(col);

  }

  /**
   * TODO
   *
   * @param row
   * @param col
   * @throws IllegalArgumentException
   */
  private void checkIndicesInBounds(int row, int col)
    throws IllegalArgumentException {
    if (row > this.getWidth() || row < 0) {
      throw new IllegalArgumentException("row " + row + " out of bounds for width " +
          this.getWidth() + " in " + this.getWidth() + "x" + this.getHeight() + " matrix");
    }
    if (col > this.getHeight() || col < 0) {
      throw new IllegalArgumentException("column " + col + " out of bounds for height " +
          this.getHeight() + " in " + this.getWidth() + "x" + this.getHeight() + " matrix");
    }

  }

  @Override
  public int getWidth() {
    if (entries.size() == 0) {
      return 0;
    }

    // guaranteed to exist
    return entries.get(0).size();
  }

  @Override
  public int getHeight() {
    if (entries.size() == 0) {
      return 0;
    }

    // guaranteed to exist
    return entries.size();
  }

  @Override
  public void fillWith(X entry)
      throws IllegalArgumentException {
    Utils.checkNotNull(entry, "cannot fill a matrix with a null entry");

    if (entries.isEmpty()) {
      throw new IllegalArgumentException("cannot fill an empty matrix with an entry");
    }

    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        this.updateEntry(entry, row, col);
      }
    }
  }

  @Override
  public void updateEntry(X newEntry, int row, int col)
      throws IllegalArgumentException {
    this.checkIndicesInBounds(row, col);

    this.entries.get(row).add(col, newEntry);
  }

  @Override
  public void rowWiseOperation(BiFunction<X, X, X> binaryOperation, IMatrix<X> toCombine)
      throws IllegalArgumentException {
    this.checkEqualDimensions(toCombine);

    for (int rowNum = 0; rowNum < this.getHeight(); rowNum++) {
      for (int colNum = 0; colNum < this.getWidth(); colNum++) {
        this.updateEntry(
            binaryOperation.apply(
                this.getElement(rowNum, colNum), toCombine.getElement(rowNum, colNum)),
            rowNum,
            colNum);
      }
    }

  }

  @Override
  public <Y> IMatrix<Y> map(Function<X, Y> unaryOperation) {
    List<List<Y>> newRows = new ArrayList<>();

    for (List<X> row : this.entries) {
      List<Y> newRow = new ArrayList<>();
      for (X entry : row) {
        newRow.add(unaryOperation.apply(entry));
      }
      newRows.add(newRow);
    }

    return new AMatrix<>((List<Y>) newRows); // TODO check this cast
  }

  @Override
  public IMatrix copy() {
    List<List<X>> rows = new ArrayList<>();

    for (List<X> row : this.entries) {
      rows.add(row);
    }

    return new AMatrix(rows);
  }

  /**
   * TODO
   *
   * @param toCheck
   */
  private void checkEqualDimensions(IMatrix<X> toCheck) {
    if (this.getWidth() != toCheck.getWidth()
    || this.getHeight() != toCheck.getHeight()) {
      throw new IllegalArgumentException("cannot operate on two matrices with different dimensions,"
          + " however the IAlignableMatrix interface supports this!");
    }
  }

  @Override
  public X sumAll(BiFunction<X, X, X> operation, X base) {
    X summed = base;

    for (int rowNum = 0; rowNum < this.getHeight(); rowNum++) {
      for (int colNum = 0; colNum < this.getWidth(); colNum++) {
        summed = operation.apply(summed, this.getElement(rowNum, colNum));
      }
    }

    return summed;
  }


  @Override
  public boolean equals(Object o) {
    // fast check
    if (this == o) {
      return true;
    }

    // check for instance
    if ( !(o instanceof IMatrix) ) {
      return false;
    }

    // safe cast
    IMatrix otherMatrix = (IMatrix) o;

    boolean allSameEntries = true;

    for (int rowNum = 0; rowNum < this.getHeight(); rowNum++) {
      for (int colNum = 0; colNum < this.getWidth(); colNum++) {
        allSameEntries &=
            this.getElement(rowNum, colNum).equals(otherMatrix.getElement(rowNum, colNum));
      }
    }

    return allSameEntries;

  }

  @Override
  public int hashCode() {
    int hashValue = 0;

    for (int rowNum = 0; rowNum < this.getHeight(); rowNum++) {
      for (int colNum = 0; colNum < this.getWidth(); colNum++) {
        hashValue += Objects.hashCode(this.getElement(rowNum, colNum));
      }
    }

    return hashValue;
  }

  @Override
  public String toString() {

    String renderedMatrix = "";

    for (int rowNum = 0; rowNum < this.getHeight(); rowNum++) {
      renderedMatrix += "R" + rowNum + ":";
      for (int colNum = 0; colNum < this.getWidth(); colNum++) {
        renderedMatrix += " " + this.getElement(rowNum, colNum).toString();
      }
    }

    return renderedMatrix;
  }


  // TODO
  @Override
  public void alignedRowWiseOperation(BiFunction<X, X, X> binaryOperation, IMatrix<X> toCombine) {

  }
}
