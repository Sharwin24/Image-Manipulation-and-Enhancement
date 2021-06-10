/**
 * TODO
 */
public abstract class AbstractFilterTest {

  /**
   * TODO
   * @return
   */
  protected abstract cs3500.model.operation.IOperation constructFilter();

  /**
   * TODO
   */
  public static class SharpeningTest extends AbstractFilterTest {

    @Override
    protected cs3500.model.operation.IOperation constructFilter() {
      return (cs3500.model.operation.IOperation) new cs3500.model.operation.Sharpening();
    }
  }

  /**
   * TODO
   */
  public static class BlurTest extends AbstractFilterTest {

    @Override
    protected cs3500.model.operation.IOperation constructFilter() {
      return (cs3500.model.operation.IOperation) new cs3500.model.operation.ImageBlur();
    }
  }

}
