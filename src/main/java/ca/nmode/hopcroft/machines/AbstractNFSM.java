package ca.nmode.hopcroft.machines;

import java.util.Map;
import java.util.Set;

/**
 * A skeletal implementation of a {@link NFSM nondeterministic finite-state machine}. Extending classes need not
 * validate the set of {@link #states() states}, set of {@link #inputElements() input elements} and {@link #startState()
 * start state}. It is ensured that the {@link #transitions() transition map} neither is nor contains keys or values
 * that are {@code null}.
 * 
 * @param <S> the type of this nondeterministic finite-state machine's states
 * @param <I> the type of this nondeterministic finite-state machine's input elements
 * @param <K> the type of the keys of this nondeterministic finite-state machine's transition map
 * @param <V> the type of the values of this nondeterministic finite-state machine's transition map
 * @param <C> the type of this nondeterministic finite-state machine's computations
 *
 * @author Naeem Model
 */
public abstract class AbstractNFSM<S, I, K, V, C> extends AbstractFSM<S, I, K, V> implements NFSM<S, I, K, V, C> {
    /**
     * Constructs a nondeterministic finite-state machine given a set of states, set of input elements, transition map
     * and start state.
     * 
     * @param states        the set of states of the new nondeterministic finite-state machine
     * @param inputElements the set of input elements of the new nondeterministic finite-state machine
     * @param transitions   the transition map of the new nondeterministic finite-state machine
     * @param startState    the start state of the new nondeterministic finite-state machine
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements} or {@code transitions} is {@code null};
     *                                  {@code states} or {@code inputElements} contains {@code null}; or
     *                                  {@code transitions} contains {@code null} keys or values
     * @throws IllegalArgumentException if {@code states} is empty, or {@code startState} is not in {@code states}
     */
    public AbstractNFSM(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState) {
        super(states, inputElements, transitions, startState);
    }

    @Override
    public final Set<S> states() {
        return states;
    }

    @Override
    public final Set<I> inputElements() {
        return inputElements;
    }

    @Override
    public final Map<K, V> transitions() {
        return transitions;
    }

    @Override
    public final S startState() {
        return startState;
    }
}
