package ca.nmode.hopcroft.graphs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedPseudograph;

/**
 * A {@link ca.nmode.hopcroft.graphs graph} representation of a {@link ca.nmode.hopcroft.machines finite-state machine}.
 * The vertices and {@link TransitionEdge edges} represent states and transitions, respectively. Every state diagram has
 * a single, immutable {@link #startVertex()}, which represents a start state. Optionally, state diagrams have
 * {@link #acceptVertexSet() accept vertices} that represent accept states.
 *
 * @param <V> the type of this state diagram's vertices
 * @param <L> the type of the labels of this state diagram's transition edges
 *
 * @author Naeem Model
 */
public final class StateDiagram<V, L> extends DirectedPseudograph<V, TransitionEdge<L>> {
    private static final long serialVersionUID = 5540687537628819680L;
    private V startVertex;
    private Set<V> acceptVertices;

    private StateDiagram(Class<TransitionEdge<L>> edgeClass) {
        super(edgeClass);
    }

    /**
     * Constructs a state diagram with the specified start vertex.
     * 
     * @param startVertex the start vertex of the new state diagram
     */
    public StateDiagram(V startVertex) {
        this(TransitionEdge.generified());
        if (startVertex == null)
            throw new NullPointerException("Cannot construct a state diagram whose start vertex is null.");
        addVertex(startVertex);
        this.startVertex = startVertex;
        acceptVertices = new HashSet<>();
    }

    /**
     * Removes the specified vertex from this state diagram, if present.
     * 
     * @throws IllegalArgumentException if the specified vertex is equal to this state diagram's start vertex
     * 
     * @return {@code true} if the specified vertex is in this state diagram, {@code false} otherwise
     */
    @Override
    public boolean removeVertex(V v) {
        if (startVertex.equals(v))
            throw new IllegalArgumentException("Cannot remove a state diagram's start vertex.");
        acceptVertices.remove(v);
        return super.removeVertex(v);
    }

    /**
     * Returns this state diagram's start vertex.
     * 
     * @return this state diagram's start vertex
     */
    public V startVertex() {
        return startVertex;
    }

    /**
     * Adds the specified vertex to this state diagram if not already present, and marks it as accepting.
     * 
     * @param v the vertex to add to this state diagram
     * 
     * @return {@code false} if the specified vertex is in this state diagram's set of accept vertices, {@code} true
     *         otherwise
     * 
     * @see #addVertex(Object)
     */
    public boolean addAcceptVertex(V v) {
        addVertex(v);
        return acceptVertices.add(v);
    }

    /**
     * Returns the set of accept vertices contained in this state diagram. The returned set is unmodifiable and attempts
     * to modify it result in an {@code UnsupportedOperationException}.
     * 
     * @return the set of accept vertices contained in this state diagram
     */
    public Set<V> acceptVertexSet() {
        return Collections.unmodifiableSet(acceptVertices);
    }
}
