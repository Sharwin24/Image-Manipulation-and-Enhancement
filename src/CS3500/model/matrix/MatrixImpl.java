package CS3500.model.matrix;

import java.util.List;

public class MatrixImpl<X> extends AMatrix<X> {

  public MatrixImpl() {
    super();
  }

  public MatrixImpl(List<List<X>> entries)
  throws IllegalArgumentException{
    super(entries);
  }

  public MatrixImpl(List<X> oneRow, int numCopies)
  throws IllegalArgumentException{
    super(oneRow, numCopies);
  }

  public MatrixImpl(X uniformEntry, int numRows, int numCols)
  throws IllegalArgumentException{
    super(uniformEntry, numRows, numCols);
  }

  @Override
  protected <Y> IMatrix<Y> factoryMatrix(List<List<Y>> rows) throws IllegalArgumentException {
    return new MatrixImpl<>(rows);
  }


}