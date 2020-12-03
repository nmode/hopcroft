package ca.nmode.hopcroft.graphs;

import java.util.Objects;

import org.jgrapht.graph.DefaultEdge;

/**
 * An labeled edge of a {@link StateDiagram state diagram}. It represents a finite-state machine's transition, where the
 * source and target {@link StateVertex vertices} correspond to the state before and after the transition, respectively,
 * with its label corresponding to additional inputs or outputs.
 * 
 * @param <L> the type of this transition edge's label
 *
 * @author Naeem Model
 */
public final class TransitionEdge<L> extends DefaultEdge {
    private static final long serialVersionUID = 1031826645375149535L;
    private L label;

    /**
     * Constructs a new transition edge with the specified label.
     * 
     * @param label the label of the new transition edge
     */
    public TransitionEdge(L label) {
        this.label = label;
    }

    /**
     * Returns the label of this transition edge.
     * 
     * @return the label of this transition edge
     */
    public L label() {
        return label;
    }

    /* Returns an instance of this class parametrized with the label type. */
    @SuppressWarnings("unchecked")
    static <T> Class<T> generified() {
        return (Class<T>) TransitionEdge.class;
    }

    /**
     * Determines whether the specified object is equal to this transition edge. This is the case when {@code obj} is
     * also a transition edge whose source and target vertices, as well as its label, are equal to this one's.
     * 
     * @return {@code true} if {@obj} is equal to this transition edge, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof TransitionEdge<?> && Objects.equals(label, ((TransitionEdge<?>) obj).label)
                && Objects.equals(getSource(), ((TransitionEdge<?>) obj).getSource())
                && Objects.equals(getTarget(), ((TransitionEdge<?>) obj).getTarget());
    }

    /**
     * Returns the hash code value of this transition edge. The returned value is obtained by calling
     * {@link Objects#hash(Object...)} with its source and target vertices, as well as its label.
     * 
     * @return the hash code value of this transition edge
     */
    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getTarget(), label);
    }

    /**
     * Returns the string representation of this transition edge. It is the string representation of its label, or
     * {@code "null"} if the label is {@code null}.
     */
    @Override
    public String toString() {
        return label == null ? "null" : label.toString();
    }
}