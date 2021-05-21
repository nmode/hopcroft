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
 * @param <O> the type of this nondeterministic finite-state machine's output elements
 * @param <K> the type of the keys of this nondeterministic finite-state machine's transition map
 * @param <V> the type of the values of this nondeterministic finite-state machine's transition map
 * @param <C> the type of this nondeterministic finite-state machine's computations
 *
 * @author Naeem Model
 */
public abstract class AbstractNFSM<S, I, O, K, V, C> extends AbstractFSM<S, I, O, K, V>
        implements NFSM<S, I, O, K, V, C> {
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
    AbstractNFSM(Set<S> states, Set<S> acceptStates, S startState, Set<I> inputElements, Set<O> outputElements,
            Map<K, V> transitions, Map<K, O> MealyTranslations, Map<S, O> MooreTranslations) {
        super(states, acceptStates, startState, inputElements, outputElements, transitions, MealyTranslations,
                MooreTranslations);
    }

    public AbstractNFSM(Set<S> states, S startState, Set<I> inputElements, Set<O> outputElements, Map<K, V> transitions,
            Map<K, O> MealyTranslations, Map<S, O> MooreTranslations) {
        this(states, Set.of(), startState, inputElements, outputElements, transitions, MealyTranslations,
                MooreTranslations);
    }

    public AbstractNFSM(Set<S> states, Set<S> acceptStates, S startState, Set<I> inputElements, Map<K, V> transitions) {
        this(states, acceptStates, startState, inputElements, Set.of(), transitions, Map.of(), Map.of());
    }

    public AbstractNFSM(Set<S> states, S startState, Set<I> inputElements, Map<K, V> transitions) {
        this(states, Set.of(), startState, inputElements, Set.of(), transitions, Map.of(), Map.of());
    }

    @Override
    public final Set<S> states() {
        return states;
    }

    @Override
    public Set<S> acceptStates() {
        return acceptStates;
    }

    @Override
    public final S startState() {
        return startState;
    }

    @Override
    public final Set<I> inputElements() {
        return inputElements;
    }

    @Override
    public final Set<O> outputElements() {
        return outputElements;
    }

    @Override
    public final Map<K, V> transitions() {
        return transitions;
    }

    @Override
    public final Map<K, O> MealyTranslations() {
        return MealyTranslations;
    }

    @Override
    public final Map<S, O> MooreTranslations() {
        return MooreTranslations;
    }
}
