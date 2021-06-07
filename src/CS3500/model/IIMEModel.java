package CS3500.model;

/**
 * TODO: JavaDoc comments
 *
 * @param <Z>
 */
public interface IIMEModel<Z> {

  void filter(Kernel kernel, IImage image, IChannel channel, int imageRow, int imageCol);

}
