package ca.nmode.hopcroft.states;

/**
 * A {@link State} with a string identifier (name).
 *
 * @author Naeem Model
 */
public final class NamedState implements State {
    private String name;

    /**
     * Constructs a new named state with the specified name.
     * 
     * @param name the name of this named state
     * 
     * @throws NullPointerException     if {@code name} is {@code null}
     * @throws IllegalArgumentException if {@code name} is blank
     */
    public NamedState(String name) {
        // Ensure the specified name is neither null nor blank.
        if (name == null)
            throw new NullPointerException("Cannot construct a named state whose name is null.");
        if (name.isBlank())
            throw new IllegalArgumentException("Cannot construct a named state whose name is blank.");
        this.name = name;
    }

    /**
     * Determines whether this named state is equal to the specified object. Equality holds if {@code obj} is a
     * {@link NamedState} whose name is equal this one's.
     *
     * @return {@code true} if this named state is equal to {@code obj}, {@code false} otherwise
     * 
     * @see #toString()
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NamedState ? name.equals(((NamedState) obj).name) : false;
    }

    /**
     * Returns this named state's hash code value. Specifically, returns the hash code value of this named state's name.
     *
     * @return this named state's hash code value
     * 
     * @see #toString()
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns this named state's name. The returned string is neither {@code null} nor blank.
     * 
     * @return this named state's name
     */
    @Override
    public String toString() {
        return name;
    }
}