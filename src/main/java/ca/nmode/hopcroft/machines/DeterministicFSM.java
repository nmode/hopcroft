package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A deterministic finite-state machine. Beginning at its {@link #startState() start state} and on receiving a sequence
 * of {@link #inputElements() input elements}, the machine reads an element and {@link #transitions() transitions} to
 * any one of its {@link #states() states} in an alternating manner until some halting condition is met. This process is
 * known as its {@link #computation(List) computation} on the input. If an element for which there are no possible
 * transitions from the machine's current state is read, it ends up in a {@code null} state and the computation halts.
 *
 * @param <S> the type of this deterministic finite-state machine's states
 * @param <I> the type of this deterministic finite-state machine's input elements
 * @param <K> the type of the keys of this deterministic finite-state machine's transition map
 * @param <V> the type of the values of this deterministic finite-state machine's transition map
 * @param <C> the type of this deterministic finite-state machine's computations
 * 
 * @author Naeem Model
 */
public interface DeterministicFSM<S, I, K, V, C>
        extends FiniteStateMachine<S, I, K, V, C, DeterministicFSM<S, I, K, V, C>> {
    /**
     * Returns this deterministic finite-state machine's unmodifiable set of states. The returned set neither is nor
     * contains {@code null}, is non-empty, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this deterministic finite-state machine's unmodifiable set of states
     */
    Set<S> states();

    /**
     * Returns this deterministic finite-state machine's unmodifiable set of input elements. The returned set neither is
     * nor contains {@code null}, and attempts to modify it result in an {@link UnsupportedOperationException}.
     * 
     * @return this deterministic finite-state machine's unmodifiable set of input elements
     */
    Set<I> inputElements();

    /**
     * Returns this deterministic finite-state machine's unmodifiable transition map. The returned map neither is nor
     * contains keys or values that are {@code null}, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this deterministic finite-state machine's unmodifiable transition map
     */
    Map<K, V> transitions();

    /**
     * Returns this deterministic finite-state machine's start state. The start state is an element of this finite-state
     * machine's set of states.
     * 
     * @return this deterministic finite-state machine's start state
     * 
     * @see #states()
     */
    S startState();

    /**
     * Returns this deterministic finite-state machine's {@link DeterministicFSM computation} on the specified input.
     * The returned computation is not {@code null}.
     * 
     * @param input the sequence of elements to compute this deterministic finite-state machine on
     * 
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return this deterministic finite-state machine's computation on the specified input
     * 
     * @see #inputElements()
     */
    C computation(List<I> input);

    /**
     * Returns the final state of this deterministic finite-state machine's computation on the specified input.
     * 
     * @param input the sequence of elements to compute this deterministic finite-state machine on
     * 
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return the final state of this deterministic finite-state machine's computation on the specified input
     * 
     * @see DeterministicFSM#states()
     * @see DeterministicFSM#inputElements()
     * @see DeterministicFSM#computation(List)
     */
    S classification(List<I> input);
}