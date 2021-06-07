package CS3500.model;

import java.util.List;
import java.util.function.BiFunction;

// INVARIANT: has odd dimensions
public class Kernel extends AMatrix<Integer> {

  @Override
  protected <Y> IMatrix<Y> factoryMatrix(List<List<Y>> rows) throws IllegalArgumentException {
    return null;
  }

}
