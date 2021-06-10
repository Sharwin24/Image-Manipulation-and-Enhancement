import CS3500.model.IIMEModel;
import CS3500.model.matrix.AMatrix;
import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;

/**
 * Tests for methods in the {@link CS3500.model.matrix.AMatrix} class.
 */
public abstract class AbstractMatrixTest {

  private static final List<List<Integer>> m3x3Entries = new ArrayList<>(Arrays.asList(
      new ArrayList(Arrays.asList(1, 2, 3)),
      new ArrayList(Arrays.asList(4, 5, 6)),
      new ArrayList(Arrays.asList(7, 8, 9))
  ));

  private IMatrix m3x3;

  @Before
  public void setUp() {
    this.m3x3 = this.constructMatrix(m3x3Entries);
  }

  protected abstract <X> IMatrix<X> constructMatrix(List<List<X>> entries);

  public static class MatrixImplTest extends AbstractMatrixTest {
    protected <X> IMatrix constructMatrix(List<List<X>> entries) {
      return new MatrixImpl(entries);
    }
  }
}
