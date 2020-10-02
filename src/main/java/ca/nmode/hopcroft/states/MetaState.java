package ca.nmode.hopcroft.states;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A {@link State} composed of substates.
 * 
 * @param <S> the type of this meta-state's substates
 *
 * @author Naeem Model
 */
public final class MetaState<S extends State> implements State {
    private Set<S> substates;

    /**
     * Constructs a new meta-state of the specified set of substates.
     * 
     * @param substates the substates of the new meta-state
     * 
     * @throws NullPointerException if {@code substates} is or contains {@code null}
     */
    public MetaState(Set<S> substates) {
        // Ensure the specified set of substates neither is nor contains null.
        if (substates == null)
            throw new NullPointerException("Cannot construct a meta-state whose set of substates is null.");
        this.substates = Collections.unmodifiableSet(new HashSet<>(substates));
        if (this.substates.contains(null))
            throw new NullPointerException("Cannot construct a meta-state whose set of substates contains null.");
    }

    /**
     * Returns this meta-state's unmodifiable set of substates. The returned set neither is nor contains {@code null},
     * and attempts to modify it result in an {@link UnsupportedOperationException}.
     * 
     * @return this meta-state's unmodifiable set of substates
     */
    public Set<S> substates() {
        return substates;
    }

    /**
     * Determines whether this meta-state is equal to the specified object. Equality holds if {@code obj} is a
     * {@link MetaState} whose set of substates equals this one's.
     * 
     * @return {@code true} if this meta-state is equal to {@code obj}, {@code false} otherwise
     * 
     * @see #substates()
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof MetaState<?> ? substates.equals(((MetaState<?>) obj).substates) : false;
    }

    /**
     * Returns this meta-state's hash code value. Specifically, returns the hash code value of this meta-state's set of
     * substates.
     * 
     * @return this meta-state's hash code value
     * 
     * @see #substates()
     */
    @Override
    public int hashCode() {
        return substates.hashCode();
    }

    /**
     * Returns this meta-state's string representation. Specifically, returns the string representation of this
     * meta-state's set of substates.
     *
     * @return this meta-state's string representation
     * 
     * @see #substates()
     */
    @Override
    public String toString() {
        return substates.toString();
    }
}