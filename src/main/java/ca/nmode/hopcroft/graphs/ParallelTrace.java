package ca.nmode.hopcroft.graphs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedMultigraph;

import ca.nmode.hopcroft.machines.NFSM;

/**
 * A {@link ca.nmode.hopcroft.graphs graph} representation of a {@link NFSM nondeterministic finite-state machine}'s
 * computation. A parallel trace's {@link StateVertex vertex} is a container for any object, and so vertices
 * corresponding to the same state can be added. More generally, it is a tree.
 * 
 * @param <S> the type of the states associated with this parallel trace's vertices
 * @param <L> the type of the labels of this parallel trace's transition edges
 *
 * @author Naeem Model
 */
public final class ParallelTrace<S, L> extends DirectedMultigraph<StateVertex<S>, TransitionEdge<L>> {
    private static final long serialVersionUID = 9086645988134404927L;
    private StateVertex<S> startVertex;
    private Set<StateVertex<S>> acceptVertices;

    private ParallelTrace(Class<TransitionEdge<L>> edgeClass) {
        super(edgeClass);
    }

    /**
     * Constructs a parallel trace with the specified start vertex.
     * 
     * @param startVertex the start vertex of the new parallel trace
     */
    public ParallelTrace(StateVertex<S> startVertex) {
        this(TransitionEdge.generified());
        if (startVertex == null)
            throw new NullPointerException("Cannot construct a parallel trace whose start vertex is null.");
        addVertex(startVertex);
        this.startVertex = startVertex;
        acceptVertices = new HashSet<>();
    }

    /**
     * Removes the specified vertex from this parallel trace, if present.
     * 
     * @throws IllegalArgumentException if the specified vertex is equal to this parallel trace's start vertex
     * 
     * @return {@code true} if the specified vertex is in this parallel trace, {@code false} otherwise
     */
    @Override
    public boolean removeVertex(StateVertex<S> v) {
        if (startVertex == v)
            throw new IllegalArgumentException("Cannot remove a parallel trace's start vertex.");
        acceptVertices.remove(v);
        return super.removeVertex(v);
    }

    /**
     * Adds the specified edge to this graph, going from the source vertex to the target vertex, if not already present.
     * 
     * @throws NullPointerException     if the specified edge is {@code null}
     * @throws IllegalArgumentException if the source or target vertices are not contained in this parallel trace; the
     *                                  target vertex is the start vertex; or the target vertex has indegree 1
     * 
     * @return {@code true} if this parallel trace does not contain the specified edge, {@code false} otherwise
     */
    @Override
    public TransitionEdge<L> addEdge(StateVertex<S> sourceVertex, StateVertex<S> targetVertex) {
        if (startVertex == targetVertex)
            throw new IllegalArgumentException(
                    "Cannot add an edge, whose target vertex is the start vertex, to a parallel trace.");
        if (inDegreeOf(targetVertex) == 1)
            throw new IllegalArgumentException(
                    "Cannot add an edge, whose target vertex has an indegree greater than zero, to a parallel trace.");
        return super.addEdge(sourceVertex, targetVertex);
    }

    /**
     * Returns this parallel trace's start vertex.
     * 
     * @return this parallel trace's start vertex
     */
    public StateVertex<S> startVertex() {
        return startVertex;
    }

    /**
     * Adds the specified vertex to this parallel trace if not already present, and marks it as accepting.
     * 
     * @param v the vertex to add to this parallel trace
     * 
     * @return {@code false} if the specified vertex is in this parallel trace's set of accept vertices, {@code} true
     *         otherwise
     * 
     * @see #addVertex(Object)
     */
    public boolean addAcceptVertex(StateVertex<S> v) {
        addVertex(v);
        return acceptVertices.add(v);
    }

    /**
     * Returns the set of accept vertices contained in this parallel trace. The returned set is unmodifiable and
     * attempts to modify it result in an {@code UnsupportedOperationException}.
     * 
     * @return the set of accept vertices contained in this parallel trace
     */
    public Set<StateVertex<S>> acceptVertexSet() {
        return Collections.unmodifiableSet(acceptVertices);
    }
}
