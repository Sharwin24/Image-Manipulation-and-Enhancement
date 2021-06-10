import cs3500.model.operation.IOperation;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sharpening;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO
 */
public abstract class AbstractFilterTest {

  private IOperation op;

  @Before
  public void init() {
    IOperation op = this.constructFilter();
  }


  /**
   * TODO
   *
   * @return
   */
  protected abstract IOperation constructFilter();

  /**
   * TODO
   */
  public static class SharpeningTest extends AbstractFilterTest {

    @Override
    protected IOperation constructFilter() {
      return new Sharpening();
    }
  }

  /**
   * TODO
   */
  public static class BlurTest extends AbstractFilterTest {

    @Override
    protected IOperation constructFilter() {
      return new ImageBlur();
    }
  }

}