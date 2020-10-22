package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/**
 * A nondeterministic finite-{@link State state} classifier. It is a {@link NondeterministicFSM nondeterministic
 * finite-state machine} which {@link #classification(List) classifies} an input into the final state of every branch of
 * its {@link NondeterministicFSM#computation(List) computation}.
 * 
 * @param <S> the type of this nondeterministic finite-state classifier's states
 * @param <I> the type of this nondeterministic finite-state classifier's input elements
 * @param <K> the type of the keys of this nondeterministic finite-state classifier's transition map
 * @param <V> the type of the values of this nondeterministic finite-state classifier's transition map
 * @param <C> the type of this nondeterministic finite-state classifier's computations
 *
 * @author Naeem Model
 */
public interface NondeterministicFSC<S extends State, I, K, V, C> extends NondeterministicFSM<S, I, K, V, C> {
    /**
     * Returns a set containing the final state of every branch of this nondeterministic finite-state classifier's
     * computation on the specified input.
     * 
     * @param input the sequence of elements to compute this nondeterministic finite-state classifier on
     * 
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return a set containing the final state of every branch of this nondeterministic finite-state classifier's
     *         computation on the specified input
     * 
     * @see NondeterministicFSM#states()
     * @see NondeterministicFSM#inputElements()
     * @see NondeterministicFSM#computation(List)
     */
    Set<S> classification(List<I> input);
}