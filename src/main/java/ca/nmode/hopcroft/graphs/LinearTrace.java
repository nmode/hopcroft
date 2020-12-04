package ca.nmode.hopcroft.graphs;

import org.jgrapht.graph.SimpleDirectedGraph;

import ca.nmode.hopcroft.machines.DeterministicFSM;

/**
 * A graph representation of a {@link DeterministicFSM deterministic finite-state machine}'s computation. A linear
 * trace's {@link StateVertex vertex} is a container for any object, and so vertices corresponding to the same state can
 * be added. Furthermore, it can only be mutated such that the resulting graph is a path.
 *
 * @param <S> the type of the state associated with this linear trace's vertices
 * @param <L> the type of the labels of this linear trace's transition edges
 *
 * @author Naeem Model
 */
public final class LinearTrace<S, L> extends SimpleDirectedGraph<StateVertex<S>, TransitionEdge<L>> {
    private static final long serialVersionUID = 6311317638312914080L;
    private StateVertex<S> startVertex;

    private LinearTrace(Class<TransitionEdge<L>> edgeClass) {
        super(edgeClass);
    }

    /**
     * Constructs a linear trace with the specified start vertex.
     * 
     * @param startVertex the start vertex of the new linear trace
     */
    public LinearTrace(StateVertex<S> startVertex) {
        this(TransitionEdge.generified());
        if (startVertex == null)
            throw new NullPointerException("Cannot construct a linear trace whose start vertex is null.");
        addVertex(startVertex);
        this.startVertex = startVertex;
    }

    /**
     * Removes the specified vertex from this linear trace, if present.
     * 
     * @throws IllegalArgumentException if the specified vertex is equal to this linear trace's start vertex
     * 
     * @return {@code true} if the specified vertex is in this linear trace, {@code false} otherwise
     */
    @Override
    public boolean removeVertex(StateVertex<S> v) {
        if (startVertex == v)
            throw new IllegalArgumentException("Cannot remove a linear trace's start vertex.");
        return super.removeVertex(v);
    }

    /**
     * Adds the specified edge to this graph, going from the source vertex to the target vertex, if not already present.
     * 
     * @throws IllegalArgumentException if the source and target vertices are not contained in this linear trace; the
     *                                  source vertex has outdegree 1; the target vertex has indegree 1; or the target
     *                                  vertex is the start vertex
     * 
     * @return {@code true} if this linear trace does not contain the specified edge, {@code false} otherwise
     */
    @Override
    public boolean addEdge(StateVertex<S> sourceVertex, StateVertex<S> targetVertex, TransitionEdge<L> e) {
        if (outDegreeOf(sourceVertex) == 1)
            throw new IllegalArgumentException(
                    "Cannot add an edge, whose source vertex has an outdegree greater than zero, to a linear trace.");
        if (inDegreeOf(targetVertex) == 1)
            throw new IllegalArgumentException(
                    "Cannot add an edge, whose target vertex has an indegree greater than zero, to a linear trace.");
        if (startVertex == targetVertex)
            throw new IllegalArgumentException(
                    "Cannot add an edge, whose target vertex is the start vertex, to a linear trace.");
        return super.addEdge(sourceVertex, targetVertex, e);
    }

    /**
     * Returns this linear trace's start vertex.
     * 
     * @return this linear trace's start vertex
     */
    public StateVertex<S> startVertex() {
        return startVertex;
    }
}