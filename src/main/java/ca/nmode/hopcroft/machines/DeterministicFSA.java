package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/**
 * A deterministic finite-{@link State state} acceptor. It is a {@link DeterministicFSM deterministic finite-state
 * machine} which {@link #accepts(List) accepts} an input if its computation ends in an {@link #acceptStates() accept
 * state}, and rejects otherwise.
 *
 * @param <S> the type of this deterministic finite-state acceptor's states
 * @param <I> the type of this deterministic finite-state acceptor's input elements
 * @param <K> the type of the keys of this deterministic finite-state acceptor's transition map
 * @param <V> the type of the values of this deterministic finite-state acceptor's transition map
 * @param <C> the type of this deterministic finite-state acceptor's computations
 * 
 * @author Naeem Model
 */
public interface DeterministicFSA<S extends State, I, K, V, C> extends DeterministicFSM<S, I, K, V, C> {
    /**
     * Returns this deterministic finite-state acceptor's unmodifiable set of accept states. The returned set is a
     * subset of this deterministic finite-state acceptor's set of states, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this deterministic finite-state acceptor's unmodifiable set of accept states
     * 
     * @see #states()
     */
    Set<S> acceptStates();

    /**
     * Returns {@code true} if the final state of this deterministic finite-state acceptor's computation on the
     * specified input is in its set of accept states, {@code false} otherwise.
     * 
     * @param input the sequence of elements to compute this deterministic finite-state acceptor on
     * 
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return {@code true} if the final state of this deterministic finite-state acceptor's computation on the
     *         specified input is in its set of accept states, {@code false} otherwise
     * 
     * @see #compute(List)
     * @see #acceptStates()
     */
    boolean accepts(List<I> input);

    /**
     * Returns {@code true} if every input in the specified set is accepted by this deterministic finite-state acceptor,
     * {@code false} otherwise.
     * 
     * @param inputs the set of sequences of elements to compute this deterministic finite-state acceptor on
     * 
     * @throws NullPointerException if {@code inputs} is or contains {@code null}
     * 
     * @return {@code true} if every input in the specified set is accepted by this deterministic finite-state acceptor,
     *         {@code false} otherwise
     * 
     * @see #compute(List)
     * @see #accepts(List)
     */
    boolean recognizes(Set<List<I>> inputs);
}