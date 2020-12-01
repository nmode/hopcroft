package ca.nmode.hopcroft.machines;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A one-way {@link DeterministicFSA deterministic finite-state acceptor}. During a {@link #compute(List)
 * computation}, it reads the elements in an input sequentially, from left to right, and halts once a
 * {@link DeterministicFSM#transitions() transition} on the last element is taken, provided it has not halted before
 * then.
 *
 * @param <S> the type of this one-way deterministic finite-state acceptor's states
 * @param <I> the type of this one-way deterministic finite-state acceptor's input elements
 *
 * @author Naeem Model
 */
public class OneWayDFSA<S, I> extends AbstractDFSA<S, I, Entry<S, I>, S, List<Entry<Entry<S, I>, S>>> {
    /**
     * Constructs a one-way deterministic finite-state acceptor given a set of states, set of input elements, transition
     * map, start state and set of accept states.
     * 
     * @param states        the set of states of the new one-way deterministic finite-state acceptor
     * @param inputElements the set of input elements of the new one-way deterministic finite-state acceptor
     * @param transitions   the transition map of the new one-way deterministic finite-state acceptor
     * @param startState    the start state of the new one-way deterministic finite-state acceptor
     * @param acceptStates  the set of accept states of the new one-way deterministic finite-state acceptor
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements}, {@code transitions} or
     *                                  {@code acceptStates} is {@code null}; {@code states} or {@code inputElements}
     *                                  contains {@code null}; or {@code transitions} contains {@code null} keys or
     *                                  values
     * @throws IllegalArgumentException if {@code states} is empty; {@code startState} is not in {@code states};
     *                                  {@code acceptStates} is not a subset of {@code states}; {@code transitions}'
     *                                  size is not equal to the product of {@code states}' and {@code inputElement}'s
     *                                  sizes; {@code transitions}' contains keys whose state or element is not in
     *                                  {@code states} and {@code inputElements}, respectively; or {@code transitions}
     *                                  contains values that are not in {@code states}
     */
    public OneWayDFSA(Set<S> states, Set<I> inputElements, Map<Entry<S, I>, S> transitions, S startState,
            Set<S> acceptStates) {
        super(states, inputElements, transitions, startState, acceptStates);
        OneWayDFSMUtility.verifyTransitions(this);
    }

    /**
     * Constructs a one-way deterministic finite-state acceptor given a set of states, set of input elements, transition
     * map, start state and an empty set of accept states.
     * 
     * @param states        the set of states of the new one-way deterministic finite-state acceptor
     * @param inputElements the set of input elements of the new one-way deterministic finite-state acceptor
     * @param transitions   the transition map of the new one-way deterministic finite-state acceptor
     * @param startState    the start state of the new one-way deterministic finite-state acceptor
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements} or {@code transitions} is {@code null};
     *                                  {@code states} or {@code inputElements} contains {@code null}; or
     *                                  {@code transitions} contains {@code null} keys or values
     * @throws IllegalArgumentException if {@code states} is empty; {@code startState} is not in {@code states};
     *                                  {@code transitions}' size is not equal to the product of {@code states}' and
     *                                  {@code inputElement}'s sizes; {@code transitions}' contains keys whose state or
     *                                  element is not in {@code states} and {@code inputElements}, respectively; or
     *                                  {@code transitions} contains values that are not in {@code states}
     */
    public OneWayDFSA(Set<S> states, Set<I> inputElements, Map<Entry<S, I>, S> transitions, S startState) {
        this(states, inputElements, transitions, startState, Set.of());
    }

    /**
     * Returns a one-way deterministic finite-state acceptor constructed from the specified deterministic finite-state
     * machine's set of states, set of input elements, transition map and start state, with the specified set of accept
     * states.
     * 
     * @param <S>          the type of the returned one-way deterministic finite-state acceptor's states
     * @param <I>          the type of the returned one-way deterministic finite-state acceptor's input elements
     * @param d            the deterministic finite-state machine whose set of states, set of input elements, transition
     *                     map and start state is used to construct the returned one-way deterministic finite-state
     *                     machine
     * @param acceptStates the set of accept states of the new one-way deterministic finite-state acceptor
     * 
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * 
     * @return a one-way deterministic finite-state acceptor constructed from the specified deterministic finite-state
     *         machine's set of states, set of input elements, transition map and start state, with the specified set of
     *         accept state
     * 
     * @see #OneWayDFSA(Set, Set, Map, State, Set)
     */
    public static final <S, I> OneWayDFSA<S, I> from(DeterministicFSM<S, I, Entry<S, I>, S, ?> d, Set<S> acceptStates) {
        // Ensure the specified deterministic finite-state machine is not null.
        if (d == null)
            throw new NullPointerException("Cannot construct a one-way deterministic finite-state acceptor from a null "
                    + "deterministic finite-state machine.");
        return new OneWayDFSA<>(d.states(), d.inputElements(), d.transitions(), d.startState(), acceptStates);
    }

    /**
     * Returns a one-way deterministic finite-state acceptor constructed from the specified deterministic finite-state
     * machine's set of states, set of input elements, transition map and start state, with an empty set of accept
     * states.
     * 
     * @param <S> the type of the returned one-way deterministic finite-state acceptor's states
     * @param <I> the type of the returned one-way deterministic finite-state acceptor's input elements
     * @param d   the deterministic finite-state machine whose set of states, set of input elements, transition map and
     *            start state is used to construct the returned one-way deterministic finite-state machine
     * 
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * 
     * @return a one-way deterministic finite-state acceptor constructed from the specified deterministic finite-state
     *         machine's set of states, set of input elements, transition map and start state, with an empty set of
     *         accept states
     * 
     * @see #OneWayDFSA(Set, Set, Map, State)
     */
    public static final <S, I> OneWayDFSA<S, I> from(DeterministicFSM<S, I, Entry<S, I>, S, ?> d) {
        return OneWayDFSA.from(d, Set.of());
    }

    /**
     * {@inheritDoc}
     * <p>
     * The computation is described by a list of entries. An entry at index {@code i} represents step {@code i} of the
     * computation, where the key is itself an entry of the current state and element read, and the value is the
     * resulting state. In other words, each entry is a transition undergone in the computation. The entry at index
     * {@code 0} consists of the {@link DeterministicFSM#startState() start state} and {@code null} as its entry key,
     * and the start state as its value, which is interpreted as the machine beginning and staying at the start state on
     * reading no element (i.e., before reading any element in the input).
     * 
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public final List<Entry<Entry<S, I>, S>> compute(List<I> input) {
        return OneWayDFSMUtility.compute(this, input);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public final S classify(List<I> input) {
        return OneWayDFSMUtility.classify(this, input);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public final boolean accepts(List<I> input) {
        List<Entry<Entry<S, I>, S>> computation = compute(input);
        return acceptStates.contains(computation.get(computation.size() - 1).getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public final boolean recognizes(Set<List<I>> inputs) {
        // Ensure the set of inputs neither is nor contains null.
        if (inputs == null)
            throw new NullPointerException(
                    "A one-way deterministic finite-state machine cannot attempt to recognize a null set of inputs.");
        if (inputs.contains(null))
            throw new NullPointerException("A one-way deterministic finite-state machine cannot attempt to recognize a "
                    + "set of inputs that contains null.");

        // The empty set is recognized if there are no reachable accept states.
        if (inputs.isEmpty())
            return Collections.disjoint(acceptStates, reachableStates());

        // Return true if every input in the set is accepted, false otherwise.
        for (List<I> input : inputs)
            if (!accepts(input))
                return false;
        return true;
    }

    /**
     * Returns this one-way deterministic finite-state acceptor's set of reachable states. A state is reachable if this
     * acceptor has a sequence of zero or more transitions leading to it from its start state. The returned set is a
     * subset of this acceptor's set of states.
     * 
     * @return this one-way deterministic finite-state acceptor's set of reachable states
     * 
     * @see DeterministicFSM#states()
     * @see DeterministicFSM#transitions()
     * @see DeterministicFSM#startState()
     */
    public final Set<S> reachableStates() {
        return OneWayDFSMUtility.reachableStates(this);
    }
}