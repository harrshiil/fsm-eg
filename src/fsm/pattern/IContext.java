package fsm.pattern;

import java.util.Collection;

/**
 * Represents represents a context,
 * which consists of a set of name-to-object bindings.
 *
 * @author Mike Yushchenko
 */
public interface IContext {

    /**
     * Binds a name to an object.
     *
     * @param name the name to bind; may not be empty
     * @param obj  object to bind; possibly null
     */
    void bind(String name, Object obj);


    /**
     * Closes this context. This method releases this context's resources immediately,
     * instead of waiting for them to be released automatically by the garbage collector.
     * This method is idempotent: invoking it on a context that has already been closed
     * has no effect. Invoking any other method on a closed context is not allowed,
     * and results in undefined behavior.
     */
    void close();

    /**
     * Retrieves the named object.
     *
     * @param name the name of object to lookup
     * @return the object bound to name
     */
    Object lookup(String name);

    /**
     * Unbinds the named object.
     *
     * @param name the name to unbind; may not be empty
     */
    void unbind(String name);

    /**
     * Checks if there is an object in the context regardless of the value
     *
     * @param name the name of object to validate
     * @return TRUE if there is an entry in the context
     */
    Boolean isPresent(String name);

    /**
     * Checks if the object exists in the context and if the value is null
     *
     * @param name the name of object to validate
     * @return TRUE if the object doesn't exist or if its null or Optional.Empty
     */
    Boolean isEmpty(String name);

    /**
     * Checks if the object exists in the context and if the value is null
     *
     * @param name the name of object to validate
     * @return TRUE if the object exist and the value is not null or Optional.Empty
     */
    Boolean hasValue(String name);

    Collection<String> getNames();

}
