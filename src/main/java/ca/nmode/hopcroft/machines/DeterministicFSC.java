package ca.nmode.hopcroft.machines;

import java.util.List;

import ca.nmode.hopcroft.states.State;

/**
 * A deterministic finite-{@link State state} classifier. It is a {@link DeterministicFSM deterministic finite-state
 * machine} which {@link #classify(List) classifies} an input into the final state of its
 * {@link DeterministicFSM#compute(List) computation}.
 * 
 * @param <S> the type of this deterministic finite-state classifier's states
 * @param <I> the type of this deterministic finite-state classifier's input elements
 * @param <K> the type of the keys of this deterministic finite-state classifier's transition map
 * @param <V> the type of the values of this deterministic finite-state classifier's transition map
 * @param <C> the type of this deterministic finite-state classifier's computations
 *
 * @author Naeem Model
 */
public interface DeterministicFSC<S extends State, I, K, V, C> extends DeterministicFSM<S, I, K, V, C> {
    /**
     * Returns the final state of this deterministic finite-state classifier's computation on the specified input.
     * 
     * @param input the sequence of elements to compute this deterministic finite-state classifier on
     * 
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return the final state of this deterministic finite-state classifier's computation on the specified input
     * 
     * @see DeterministicFSM#compute(List)
     * @see DeterministicFSM#states()
     * @see DeterministicFSM#inputElements()
     */
    S classify(List<I> input);
}