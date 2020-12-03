package ca.nmode.hopcroft.graphs;

/**
 * A vertex of a {@link StateDiagram state diagram}, whose element is a state. In general, it serves as an encapsulator
 * for any object, as well as {@code null}.
 * 
 * @param <S> the type of this vertex's state element
 *
 * @author Naeem Model
 */
public final class StateVertex<S> {
    private S state;

    /**
     * Constructs a vertex whose element is the specified state.
     * 
     * @param state the state element of the new vertex
     */
    public StateVertex(S state) {
        this.state = state;
    }

    /**
     * Returns this vertex's state element
     * 
     * @return this vertex's state element
     */
    public S state() {
        return state;
    }

    /**
     * Returns the string representation of this vertex. It is the string representation of its state element, or
     * {@code "null"} if the state is {@null}.
     *
     * @return the string representation of this vertex
     */
    @Override
    public String toString() {
        return state == null ? "null" : state.toString();
    }
}