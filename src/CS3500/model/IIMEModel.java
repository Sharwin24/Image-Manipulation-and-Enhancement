package CS3500.model;

import CS3500.model.channel.IChannel;
import CS3500.model.image.IImage;
import CS3500.model.matrix.Kernel;

/**
 * TODO: JavaDoc comments
 *
 * @param <Z>
 */
public interface IIMEModel<Z> {

  void filter(Kernel kernel, IImage image, IChannel channel, int imageRow, int imageCol);

}