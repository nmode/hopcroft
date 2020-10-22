package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/**
 * A nondeterministic finite-{@link State state} machine. Beginning at its {@link #startState() start state} and on
 * receiving a sequence of {@link #inputElements() input elements}, the machine optionally reads any number of elements
 * and {@link #transitions() transitions} to none or multiple of its {@link #states() states} until some halting
 * condition is met. This process is known as its {@link #computation(List) computation} on the input. When none or more
 * than one transitions are made, the computation <i>branches</i> into a parallel computation. On a given branch, if an
 * element for which there are no possible transitions from the machine's current state is read, the branch ends up in a
 * {@code null} state and halts. The entire computation halts if every branch halts.
 *
 * @param <S> the type of this nondeterministic finite-state machine's states
 * @param <I> the type of this nondeterministic finite-state machine's input elements
 * @param <K> the type of the keys of this nondeterministic finite-state machine's transition map
 * @param <V> the type of the values of this nondeterministic finite-state machine's transition map
 * @param <C> the type of this nondeterministic finite-state machine's computations
 * 
 * @author Naeem Model
 */
public interface NondeterministicFSM<S extends State, I, K, V, C>
        extends FiniteStateMachine<S, I, K, V, C, NondeterministicFSM<S, I, K, V, C>> {
    /**
     * Returns this nondeterministic finite-state machine's unmodifiable set of states. The returned set neither is nor
     * contains {@code null}, is non-empty, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state machine's unmodifiable set of states
     */
    Set<S> states();

    /**
     * Returns this nondeterministic finite-state machine's unmodifiable set of input elements. The returned set neither
     * is nor contains {@code null}, and attempts to modify it result in an {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state machine's unmodifiable set of input elements
     */
    Set<I> inputElements();

    /**
     * Returns this nondeterministic finite-state machine's unmodifiable transition map. The returned map neither is nor
     * contains keys or values that are {@code null}, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state machine's unmodifiable transition map
     */
    Map<K, V> transitions();

    /**
     * Returns this nondeterministic finite-state machine's start state. The start state is an element of this
     * finite-state machine's set of states.
     * 
     * @return this nondeterministic finite-state machine's start state
     * 
     * @see #states()
     */
    S startState();

    /**
     * Returns this nondeterministic finite-state machine's {@link NondeterministicFSM computation} on the specified
     * input. The returned computation is not {@code null}.
     * 
     * @param input the sequence of elements to compute this nondeterministic finite-state machine on
     * 
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return this nondeterministic finite-state machine's computation on the specified input
     * 
     * @see #inputElements()
     */
    C computation(List<I> input);
}