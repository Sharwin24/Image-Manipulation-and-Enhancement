import cs3500.model.operation.Greyscale;
import cs3500.model.operation.IOperation;
import cs3500.model.operation.Sepia;

/**
 *
 */
public abstract class AbstractColorTransformTest {

  /**
   *
   */
  protected abstract IOperation constructColorTransform();

  /**
   *
   */
  public static class GreyscaleTest extends AbstractColorTransformTest {

    @Override
    protected IOperation constructColorTransform() {
      return new Greyscale();
    }
  }

  /**
   *
   */
  public static class SepiaTest extends AbstractColorTransformTest {

    @Override
    protected IOperation constructColorTransform() {
      return new Sepia();
    }
  }
}