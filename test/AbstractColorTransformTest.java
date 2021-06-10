import cs3500.model.operation.Greyscale;
import cs3500.model.operation.IOperation;
import cs3500.model.operation.Sepia;

/**
 * TODO
 */
public abstract class AbstractColorTransformTest {

  /**
   * TODO
   */
  protected abstract IOperation constructColorTransform();

  /**
   * TODO
   */
  public static class GreyscaleTest extends AbstractColorTransformTest {

    @Override
    protected IOperation constructColorTransform() {
      return new Greyscale();
    }
  }

  /**
   * TODO
   */
  public static class SepiaTest extends AbstractColorTransformTest {

    @Override
    protected IOperation constructColorTransform() {
      return new Sepia();
    }
  }
}
