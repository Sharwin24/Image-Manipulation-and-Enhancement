package cs3500.controller;

import cs3500.model.IMultiLayerModel;

/**
 * To be used to control the functionality of a {@link IMultiLayerModel}
 * with textual commands in an interactive format and also through a script of commands
 * pertaining to the following syntax:
 *
 *<ul>
 *   <li>
 *     Each command goes on a line of its own, so only one command is allowed per line. If there are
 *     two commands on a line, the second is ignored.
 *   </li>
 *   <li>
 *     Each command follows this meta-structure:
 *     <i><code>[CMD] (opt1) (opt2) ... (optm) [req1] [req2] ... [reqn]</code></i>,
 *     where <code>[CMD]</code> is the textual representation of a command,
 *     and <code>(opti)</code> is the <code>i</code>th optional argument of
 *     of a function whose optional arguments have an arity of <code>m</code>,
 *     and where <code>[reqj]</code> is the <code>j</code>th required argument of
 *     a function of arity <code>n</code>. Since different commands have different arities (and
 *     some take no optional paramters), the commands take arguments as expressed in the following
 *     table:
 *   </li>
 *   <li>
 *     Invalid commands or parameters are skipped and the next line is processed.
 *   </li>
 *   <li>all commands and arguments are lowercase</li>
 *</ul>
 * @param <Z> TODO, maybe delete
 */
public interface IMultiLayerIMEController<Z> {

  /**
   * Allows the user to control the operations that a
   * {@link IMultiLayerModel} can perform through I/O
   * @param mdl
   * @throws IllegalArgumentException
   */
  void run(IMultiLayerModel<Z> mdl)
      throws IllegalArgumentException;
}
