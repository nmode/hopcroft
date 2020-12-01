package ca.nmode.hopcroft.machines;

import java.util.Map;
import java.util.Set;

/**
 * A skeletal implementation of a {@link NondeterministicFSA nondeterministic finite-state acceptor}. Extending classes
 * need not validate the set of {@link #states() states}, set of {@link #inputElements() input elements},
 * {@link #startState() start state} and set of {@link #acceptStates() accept states}. It is ensured that the
 * {@link #transitions() transition map} neither is nor contains keys or values that are {@code null}.
 * 
 * @param <S> the type of this nondeterministic finite-state acceptor's states
 * @param <I> the type of this nondeterministic finite-state acceptor's input elements
 * @param <K> the type of the keys of this nondeterministic finite-state acceptor's transition map
 * @param <V> the type of the values of this nondeterministic finite-state acceptor's transition map
 * @param <C> the type of this nondeterministic finite-state acceptor's computations
 *
 * @author Naeem Model
 */
public abstract class AbstractNFSA<S, I, K, V, C> extends AbstractNFSM<S, I, K, V, C>
        implements NondeterministicFSA<S, I, K, V, C> {
    final Set<S> acceptStates;

    /**
     * Constructs a nondeterministic finite-state acceptor given a set of states, set of input elements, transition map,
     * start state and set of accept states.
     * 
     * @param states        the set of states of the new nondeterministic finite-state acceptor
     * @param inputElements the set of input elements of the new nondeterministic finite-state acceptor
     * @param transitions   the transition map of the new nondeterministic finite-state acceptor
     * @param startState    the start state of the new nondeterministic finite-state acceptor
     * @param acceptStates  the set of accept states of the new nondeterministic finite-state acceptor
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements}, {@code transitions} or
     *                                  {@code acceptStates} is {@code null}; {@code states} or {@code inputElements}
     *                                  contains {@code null}; or {@code transitions} contains {@code null} keys or
     *                                  values
     * @throws IllegalArgumentException if {@code states} is empty, {@code startState} is not in {@code states}, or
     *                                  {@code acceptStates} is not a subset of {@code states}
     */
    public AbstractNFSA(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState, Set<S> acceptStates) {
        super(states, inputElements, transitions, startState);
        this.acceptStates = verifyAcceptStates(states, acceptStates);
    }

    @Override
    public final Set<S> acceptStates() {
        return acceptStates;
    }
}