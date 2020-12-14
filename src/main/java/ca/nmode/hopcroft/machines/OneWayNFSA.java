package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A one-way {@link NFSA nondeterministic finite-state acceptor}. During a {@link #compute(List) computation}, it reads
 * the elements in an input sequentially, from left to right, and halts once a {@link DFSM#transitions() transition} on
 * the last element is taken, provided it has not halted before then.
 *
 * @param <S> the type of this one-way nondeterministic finite-state acceptor's states
 * @param <I> the type of this one-way nondeterministic finite-state acceptor's input elements
 *
 * @author Naeem Model
 */
public class OneWayNFSA<S, I> extends AbstractNFSA<S, I, Entry<S, I>, Set<S>, List<Entry<Entry<Set<S>, I>, Set<S>>>> {
    /**
     * Constructs a one-way nondeterministic finite-state acceptor given a set of states, set of input elements,
     * transition map, start state and set of accept states.
     * 
     * @param states        the set of states of the new one-way nondeterministic finite-state acceptor
     * @param inputElements the set of input elements of the new one-way nondeterministic finite-state acceptor
     * @param transitions   the transition map of the new one-way nondeterministic finite-state acceptor
     * @param startState    the start state of the new one-way nondeterministic finite-state acceptor
     * @param acceptStates  the set of accept states of the new one-way nondeterministic finite-state acceptor
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements}, {@code transitions} or
     *                                  {@code acceptStates} is {@code null}; {@code states} or {@code inputElements}
     *                                  contains {@code null}; or {@code transitions} contains {@code null} keys or
     *                                  values
     * @throws IllegalArgumentException if {@code states} is empty; {@code startState} is not in {@code states};
     *                                  {@code acceptStates} is not a subset of {@code states}; {@code transitions}'
     *                                  contains keys whose state or element is not in {@code states} and
     *                                  {@code inputElements}, respectively; or {@code transitions} contains values that
     *                                  are not subsets of {@code states}
     */
    public OneWayNFSA(Set<S> states, Set<I> inputElements, Map<Entry<S, I>, Set<S>> transitions, S startState,
            Set<S> acceptStates) {
        super(states, inputElements, transitions, startState, acceptStates);
        OneWayNFSMs.verifyTransitions(acceptStates, inputElements, transitions);
    }

    /**
     * Constructs a one-way nondeterministic finite-state acceptor given a set of states, set of input elements,
     * transition map, start state and an empty set of accept states.
     * 
     * @param states        the set of states of the new one-way nondeterministic finite-state acceptor
     * @param inputElements the set of input elements of the new one-way nondeterministic finite-state acceptor
     * @param transitions   the transition map of the new one-way nondeterministic finite-state acceptor
     * @param startState    the start state of the new one-way nondeterministic finite-state acceptor
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements} or {@code transitions} is {@code null};
     *                                  {@code states} or {@code inputElements} contains {@code null}; or
     *                                  {@code transitions} contains {@code null} keys or values
     * @throws IllegalArgumentException if {@code states} is empty; {@code startState} is not in {@code states};
     *                                  {@code transitions}' contains keys whose state or element is not in
     *                                  {@code states} and {@code inputElements}, respectively; or {@code transitions}
     *                                  contains values that are not subsets of {@code states}
     */
    public OneWayNFSA(Set<S> states, Set<I> inputElements, Map<Entry<S, I>, Set<S>> transitions, S startState) {
        this(states, inputElements, transitions, startState, Set.of());
    }

    @Override
    public List<Entry<Entry<Set<S>, I>, Set<S>>> compute(List<I> input) {
        // TODO
        return null;
    }

    @Override
    public Set<S> classify(List<I> input) {
        // TODO
        return null;
    }

    @Override
    public boolean accepts(List<I> input) {
        // TODO
        return false;
    }

    @Override
    public boolean recognizes(Set<List<I>> inputs) {
        // TODO
        return false;
    }
}
