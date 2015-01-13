

package graphics;

/**
 *
 * @author David
 */
public class RenderException extends Exception {

    /**
     * Creates a new instance of <code>RenderException</code> without detail
     * message.
    */
    public RenderException() {
    }

    /**
     * Constructs an instance of <code>RenderException</code> with the specified
     * detail message.
    *
     * @param msg the detail message.
    */
    public RenderException(String msg) {
        super(msg);
    }
}

