package ca.nmode.hopcroft.graphs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.SimpleDirectedGraph;

import ca.nmode.hopcroft.machines.DFSM;
import ca.nmode.hopcroft.machines.NFSM;

/**
 * A {@link ca.nmode.hopcroft.graphs graph} representation of a {@link DFSM deterministic finite-state machine}'s
 * computation, or a single path of a {@link NFSM nondeterministic finite-state machine}'s computation. A serial trace's
 * {@link StateVertex vertex} is a container for any object, and so vertices corresponding to the same state can be
 * added. More generally, it is a directed path.
 *
 * @param <S> the type of the states associated with this serial trace's vertices
 * @param <L> the type of the labels of this serial trace's transition edges
 *
 * @author Naeem Model
 */
public final class SerialTrace<S, L> extends SimpleDirectedGraph<StateVertex<S>, TransitionEdge<L>> {
    private static final long serialVersionUID = 6311317638312914080L;
    private StateVertex<S> startVertex;
    private Set<StateVertex<S>> acceptVertices;

    private SerialTrace(Class<TransitionEdge<L>> edgeClass) {
        super(edgeClass);
    }

    /**
     * Constructs a serial trace with the specified start vertex.
     * 
     * @param startVertex the start vertex of the new serial trace
     */
    public SerialTrace(StateVertex<S> startVertex) {
        this(TransitionEdge.generified());
        if (startVertex == null)
            throw new NullPointerException("Cannot construct a serial trace whose start vertex is null.");
        addVertex(startVertex);
        this.startVertex = startVertex;
        acceptVertices = new HashSet<>();
    }

    /**
     * Removes the specified vertex from this serial trace, if present.
     * 
     * @throws IllegalArgumentException if the specified vertex is equal to this serial trace's start vertex
     * 
     * @return {@code true} if the specified vertex is in this serial trace, {@code false} otherwise
     */
    @Override
    public boolean removeVertex(StateVertex<S> v) {
        if (startVertex == v)
            throw new IllegalArgumentException("Cannot remove a serial trace's start vertex.");
        acceptVertices.remove(v);
        return super.removeVertex(v);
    }

    /**
     * Adds the specified edge to this serial trace, going from the source vertex to the target vertex, if not already
     * present.
     * 
     * @throws NullPointerException     if the specified edge is {@code null}
     * @throws IllegalArgumentException if the source or target vertices are not contained in this serial trace; the
     *                                  target vertex is the start vertex; the source vertex has outdegree 1; or the
     *                                  target vertex has indegree 1
     * 
     * @return {@code true} if this serial trace does not contain the specified edge, {@code false} otherwise
     */
    @Override
    public boolean addEdge(StateVertex<S> sourceVertex, StateVertex<S> targetVertex, TransitionEdge<L> e) {
        if (startVertex == targetVertex)
            throw new IllegalArgumentException(
                    "Cannot add an edge, whose target vertex is the start vertex, to a serial trace.");
        if (outDegreeOf(sourceVertex) == 1)
            throw new IllegalArgumentException(
                    "Cannot add an edge, whose source vertex has an outdegree greater than zero, to a serial trace.");
        if (inDegreeOf(targetVertex) == 1)
            throw new IllegalArgumentException(
                    "Cannot add an edge, whose target vertex has an indegree greater than zero, to a serial trace.");
        return super.addEdge(sourceVertex, targetVertex, e);
    }

    /**
     * Returns this serial trace's start vertex.
     * 
     * @return this serial trace's start vertex
     */
    public StateVertex<S> startVertex() {
        return startVertex;
    }

    /**
     * Adds the specified vertex to this serial trace if not already present, and marks it as accepting.
     * 
     * @param v the vertex to add to this serial trace
     * 
     * @return {@code false} if the specified vertex is in this serial trace's set of accept vertices, {@code} true
     *         otherwise
     * 
     * @see #addVertex(Object)
     */
    public boolean addAcceptVertex(StateVertex<S> v) {
        addVertex(v);
        return acceptVertices.add(v);
    }

    /**
     * Returns the set of accept vertices contained in this serial trace. The returned set is unmodifiable and attempts
     * to modify it result in an {@code UnsupportedOperationException}.
     * 
     * @return the set of accept vertices contained in this serial trace
     */
    public Set<StateVertex<S>> acceptVertexSet() {
        return Collections.unmodifiableSet(acceptVertices);
    }
}
