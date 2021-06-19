<u><b>How to use the IME Controller</b></u>


In this controller, inputs are processed from the mentioned {@link Readable} with the following
 syntax:
 <p> Each command follows this meta-structure:
 <i><code>[CMD] [arg1] [arg2] ... [argn]</code></i>,
 where <code>[CMD]</code> is the textual representation of a command, and where
 <code>[argi]</code> is the <code>i</code>th required argument of a function of arity
 <code>n</code>. Additionally, some commands take an optional parameter, denoted with the syntax
 <code>(opt)</code>, where the use of parentheses distinguish that this parameter is optional and
 not <code>[required]</code>. Furthermore, this controller takes advantage of {@link IIMECommand}
 function objects associated with each textual command via a {@link Map} in order to easily
 support this functionality, and provide developers with an easy path to add/change the supported
 commands. Since different commands have different arities (and some take no optional paramters),
 the commands take arguments as expressed in the following table. Consider this a <i>guide</i> to
 using the commands, too</p>
 <p><b>How to read the guide:</b></p> the <i>textual command</i> shows
 what word leads off a command, and the <i>parameters</i> shows what parameters should be
 specified, where:
    <ul>
      <li>
        <code>[Type]</code> denotes a required parameter of type <code>Type</code>,
        for example, <code>[String]</code> represents a required parameter
        that is a String.
      </li>
      <li>
        <code>(Type)</code> denotes an optional parameter of type <code>Type</code>,
        for example, for parameters <code>[Integer] (Real) [Boolean]</code>,
        a valid command combination of parameters would be <code>0 12.3 true</code>,
        or 0 true, since the <code>(Real)</code> optional parameter is, well, optional.
      </li>
      <li>
        <code>("literal")</code> and <code>["literal"]</code> denote
        an optional or required (resp.) parameter that is the literal value
        <code>literal</code>, as denoted by the quotation marks. For example,
        for parameters <code>[String] (Integer) ("5") ["hello"]</code>,
        valid combinations of parameters would be <code>Foo 2 5 hello</code>,
        <code>bar 5 hello</code>, or <code>BAZ hello</code>
      </li>
      <li>
        <code>[{t1, t2, ..., tn}]</code> and
        <code>({t1, t2, ..., tm}</code>
        denote a parameter that can be one of the literals <code>ti</code>,
        where <code>1 <= i <= n,m</code>, and <code>n != m || n == m</code>, in
        a required or optional parameter (resp.). For example, for parameters
        <code>[Character] ({1, 2, 3}) [{Foo, Bar, Baz, Quux}]</code>,
        a valid combination of parameters is <code>A 2 Quux</code>,
        <code>u Baz</code>, but not <code>f 4 Hello</code>, since
        "4" and "Hello" are not enumerated in
        <code>({1, 2, 3})</code> and <code>[{Foo, Bar, Baz, Quux}]</code>, (
        resp.)
      </li>
    </ul>

   <ul>
     <ul>
       <li>
       <b>Textual command:</b> <code>apply</code>
       </li>
       <ul>
         <li>
           <b>Arity:</b> n-ary
         </li>
         <li>
           <b>Parameters:</b>
           ({"sepia", "greyscale", "sharpen", "blur"})
           ({"sepia", "greyscale", "sharpen", "blur"})
           ({"sepia", "greyscale", "sharpen", "blur"}) ...
         </li>
         <li><b>Description:</b> applies <code>n</code>
         {@link IOperation}s to the current image to manipulate it,
         where <code>n</code> is a non-negative integer.</li>
       </ul>

       <li>
         <b>Textual command:</b> <code>current</code>
         </li>
         <ul>
           <li>
             <b>Arity:</b> unary
           </li>
           <li>
             <b>Parameters:</b>
             [Integer]
           </li>
           <li><b>Description:</b> sets the current working layer
           to the layer at the given index (indexed from 0, from left
           to right with respect to the {@link java.util.List} that the layer
           is stored in.</li>
         </ul>


         <li>
           <b>Textual command:</b> <code>new</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> nullary
             </li>
             <li>
               <b>Parameters:</b> none
           </li>
             <li><b>Description:</b> creates a new layer to store and manipulate an image
             </li>
           </ul>


 <li>
           <b>Textual command:</b> <code>delete</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> unary
             </li>
             <li>
               <b>Parameters:</b>
               <code>[Integer]</code>
             </li>
             <li><b>Description:</b> deletes the
             layer at the given index (indexed from 0, from left
             to right with respect to the {@link java.util.List} that the layer
             is stored in.)</li>
           </ul>



 <li>
           <b>Textual command:</b> <code>export</code>
           </li>
           <ul>
            <li>
               <b>Arity:</b> binary, with one optional parameter
             </li>
             <li>
               <b>Parameters:</b>
               <code>[{"PPM", "PNG", "JPEG"}] ("layers") [String]</code>
             </li>
             <li><b>Description:</b> exports image(s) to the file format
             specified by the first parameter. If the optional parameter
             <code>("layers")</code> is included, all layers of the multilayer
             image are exported, otherwise only the current layer is exported.
             the second required parameter denotes the name of the file that is to be exported,
             such as "myImage".</li>
           </ul>



 <li>
           <b>Textual command:</b> <code>import</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> binary, with one optional parameter
             </li>
             <li>
               <b>Parameters:</b>
                 <code>[{"PPM", "PNG", "JPEG"}] ("layers") [String]</code>
               </li>
               <li><b>Description:</b> imports image(s) of the file format
               specified by the first parameter. If the optional parameter
               <code>("layers")</code> is included, all layers of the multilayer
               image are imported from a file containing all of the paths
               of the images to be loaded.
               The second required parameter denotes the name of the path to the image(s)
               that will be imported, relative to the current working directory,
               such as "res/myImage.png".</li>
           </ul>



 <li>
           <b>Textual command:</b> <code>programmatic</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> quaternary
             </li>
             <li>
               <b>Parameters:</b>
               <code>[{"checkerboard", "bwnoise", "rainbownoise", "purenoise"}]
               [Integer] [Integer] [Integer]</code>
             </li>
             <li><b>Creates a programmatic image as specified by the literal in the first argument,
             with a width, height, and unit size in pixels specified by the second, third, and
             fourth arguments (resp.) on the current layer.</li>
           </ul>


 <li>
           <b>Textual command:</b> <code>redo</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> nullary
             </li>
             <li>
               <b>Parameters:</b> none
             </li>
             <li><b>Description:</b> restores the last state of the multilayer
             model before an undo command was executed, if possible.</li>
           </ul>


 <li>
           <b>Textual command:</b> <code>undo</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> nullary
             </li>
             <li>
               <b>Parameters:</b> none
             </li>
             <li><b>Description:</b> undoes the most recent change to the
             multilayer model, if possible.</li>
           </ul>

           <li>
             <b>Textual command:</b> <code>visibility</code>
             </li>
             <ul>
               <li>
                 <b>Arity:</b> unary
               </li>
               <li>
                 <b>Parameters:</b>
                 <code>[Integer]</code>
               </li>
               <li><b>Description:</b> toggles the visibility of the layer
               indicated by the given argument, indexed from 0, left to right
               (with respect to the {@link java.util.List} that the layers of the model
               are stored in.</li>
             </ul>

             <li>
             <b>Textual command:</b> <code>swap</code>
             </li>
             <ul>
               <li>
                 <b>Arity:</b> binary
               </li>
               <li>
                 <b>Parameters:</b>
                 <code>[Integer] [Integer]</code>
               </li>
               <li><b>Description:</b> swaps the layers at indices given by the two arguments,
               indexed from 0, from left to right (with respect to the
               {@link java.util.List} that the layers are stored in.</li>
             </ul>

             <li>
             <b>Textual command:</b> <code>save</code>
             </li>
             <ul>
               <li>
                 <b>Arity:</b> nullary
               </li>
               <li>
                 <b>Parameters:</b> none
               </li>
               <li><b>Description:</b> saves the most recent version of the model
               to its history, meaning that an undo command will do nothing.</li>
            </ul>




     </ul>
   </ul>