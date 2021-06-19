package cs3500.controller;

import cs3500.model.IMultiLayerModel;

/**
 * To be used to control the functionality of a {@link IMultiLayerModel}
 * with textual commands in an interactive format and also through a script of commands
 * pertaining to the following syntax:.
 *
 *<ul>
 *   <li>
 *     Each command goes on a line of its own, so only one command is allowed per line. If there are
 *     two commands on a line, the second is ignored.
 *   </li>
 *   <li>
 *     Each command follows this meta-structure:
 *     <i><code>[CMD] [arg0] [arg1] ... [argn]</code></i>,
 *     where <code>[CMD]</code> is the textual representation of a command,
 *     and where <code>[argi]</code> is the <code>i</code>th required argument of
 *     a function of arity <code>n</code>. Since different commands have different aritiesN (and
 *     some take no optional parameters), the commands take arguments as expressed in the following
 *     table:
 *   </li>
 *   <li>
 *     Invalid commands or parameters are skipped and the next line is processed.
 *   </li>
 *   <li>all commands are lowercase</li>
 *</ul>
 */
public interface IMultiLayerIMEController {

  /**
   * Allows the user to control the operations that a
   * {@link IMultiLayerModel} can perform through I/O.
   */
  void run(/*IMultiLayerModel mdl*/);
}