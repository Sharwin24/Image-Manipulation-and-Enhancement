package CS3500.model.matrix;

import CS3500.model.matrix.AMatrix;
import CS3500.model.matrix.IMatrix;
import java.util.List;

// INVARIANT: has odd dimensions
public class Kernel extends AMatrix<Integer> {

  @Override
  protected <Y> IMatrix<Y> factoryMatrix(List<List<Y>> rows) throws IllegalArgumentException {
    return null;
  }

}