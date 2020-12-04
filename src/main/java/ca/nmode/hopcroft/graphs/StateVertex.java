package ca.nmode.hopcroft.graphs;

/**
 * A vertex of a {@link StateDiagram state diagram} corresponding to a state. In general, it serves as a container for
 * any object, as well as {@code null}.
 * 
 * @param <S> the type of this vertex's corresponding state
 *
 * @author Naeem Model
 */
public final class StateVertex<S> {
    private S state;

    /**
     * Constructs a vertex corresponding to the specified state.
     * 
     * @param state the state corresponding to the new vertex
     */
    public StateVertex(S state) {
        this.state = state;
    }

    /**
     * Returns the state corresponding to this vertex.
     * 
     * @return the state corresponding to this vertex
     */
    public S state() {
        return state;
    }

    /**
     * Returns the string representation of this vertex. It is the string representation of its corresponding state, or
     * {@code "null"} if the state is {@code null}.
     *
     * @return the string representation of this vertex
     */
    @Override
    public String toString() {
        return state == null ? "null" : state.toString();
    }
}