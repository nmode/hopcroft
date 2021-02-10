package ca.nmode.hopcroft.machines;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * A one-way {@link NFSA nondeterministic finite-state acceptor}. During a {@link #compute(List) computation}, it reads
 * the elements in an input sequentially, from left to right, and halts once a {@link NFSM#transitions() transition} on
 * the last element is taken, provided it has not halted before then.
 *
 * @param <S> the type of this one-way nondeterministic finite-state acceptor's states
 * @param <I> the type of this one-way nondeterministic finite-state acceptor's input elements
 *
 * @author Naeem Model
 */
public class OneWayNFSA<S, I> extends AbstractNFSA<S, I, Entry<S, I>, Set<S>, List<Entry<Entry<Set<S>, I>, Set<S>>>> {
    private boolean hasEpsilon;

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
        hasEpsilon = OneWayNFSMs.verifyTransitions(this);
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

    /**
     * Constructs a one-way nondeterministic finite-state acceptor with the specified nondeterministic finite-state
     * machine's set of states, set of input elements, transition map and start state, as well as the specified set of
     * accept states.
     * 
     * @param n            the nondeterministic finite-state machine whose set of states, set of input elements,
     *                     transition map and start state is used to construct the new one-way nondeterministic
     *                     finite-state acceptor
     * @param acceptStates the set of accept states of the new one-way nondeterministic finite-state acceptor
     * 
     * @throws NullPointerException     if {@code n} or {@code acceptStates} is {@code null}
     * @throws IllegalArgumentException if {@code acceptStates} is not a subset of {@code n}'s states; {@code n}'s
     *                                  transition map contains keys whose state or element is not in its set of states
     *                                  and set of input elements, respectively; or {@code n}'s transition map contains
     *                                  values that are not subsets of its set of states
     */
    public OneWayNFSA(NFSM<S, I, Entry<S, I>, Set<S>, ?> n, Set<S> acceptStates) {
        this(Objects.requireNonNull(n, "Cannot construct a one-way nondeterministic finite-state acceptor from a null "
                        + "nondeterministic finite-state machine.")
                .states(), n.inputElements(), n.transitions(), n.startState(), acceptStates);
    }

    /**
     * Constructs a one-way nondeterministic finite-state acceptor with the specified nondeterministic finite-state
     * machine's set of states, set of input elements, transition map and start state, as well as an empty set of accept
     * states.
     * 
     * @param n the nondeterministic finite-state machine whose set of states, set of input elements, transition map and
     *          start state is used to construct the new one-way nondeterministic finite-state acceptor
     * 
     * @throws NullPointerException     if {@code n} is {@code null}
     * @throws IllegalArgumentException if {{@code n}'s transition map contains keys whose state or element is not in
     *                                  its set of states and set of input elements, respectively; or {@code n}'s
     *                                  transition map contains values that are not subsets of its set of states
     */
    public OneWayNFSA(NFSM<S, I, Entry<S, I>, Set<S>, ?> n) {
        this(n, Set.of());
    }

    /**
     * {@inheritDoc}
     * <p>
     * The computation is described by a list of entries. The entry at index {@code i} represents step {@code i} of the
     * computation. Each entry has a subentry as its key; the subentry's key is the set of current states and its value
     * is the element read. The value of each entry is the {@link #epsilonClosure(Set) epsilon closure} of the union of
     * the output of this machine's {@link #transitions() transition map} on every current state with the current
     * element. In other words, each entry is the combination of the transitions undergone at the same step of every
     * active branch.
     * <p>
     * Every computation contains an entry at index {@code 0}. Its subentry consists of the singleton set containing the
     * {@link #startState() start state} and {@code null} as the key and value, respectively, and its value is the
     * epsilon closure of the start state. This is interpreted as the machine beginning at the start state and
     * transitioning before reading any element.
     * 
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public final List<Entry<Entry<Set<S>, I>, Set<S>>> compute(List<I> input) {
        return OneWayNFSMs.compute(this, input);
    }

    @Override
    public final Set<S> classify(List<I> input) {
        return OneWayNFSMs.classify(this, input);
    }

    @Override
    public final boolean accepts(List<I> input) {
        // TODO
        return false;
    }

    @Override
    public final boolean recognizes(Set<List<I>> inputs) {
        // TODO
        return false;
    }

    /**
     * Returns {@code true} if this one-way nondeterministic finite-state acceptor has epsilon transitions,
     * {@code false} otherwise.
     * 
     * @return {@code true} if this one-way nondeterministic finite-state acceptor has epsilon transitions,
     *         {@code false} otherwise
     */
    public final boolean hasEpsilon() {
        return hasEpsilon;
    }

    /**
     * Returns the epsilon closure of the specified state in this one-way nondeterministic finite-state acceptor. The
     * returned set contains the states of this acceptor that are reachable from the specified state via zero or more
     * epsilon transitions.
     * 
     * @param state the state whose epsilon closure is to be taken
     * 
     * @return the epsilon closure of the specified state in this one-way nondeterministic finite-state acceptor
     * 
     * @see #states()
     * @see #transitions()
     */
    public final Set<S> epsilonClosure(S state) {
        Set<S> epsilonClosure = new HashSet<>();
        // If the specified state is not in the set of states, return the empty set.
        if (states.contains(state)) {
            epsilonClosure.add(state);
            Deque<S> visit = new ArrayDeque<>(epsilonClosure);
            // Continue until epsilon transitions have been taken for all visited states
            while (!visit.isEmpty()) {
                Set<S> transitionValue = transitions.get(new SimpleEntry<>(visit.removeFirst(), null));
                if (transitionValue != null)
                    for (S s : transitionValue)
                        // Add the resulting state of the transition to be visited if it was not already reached.
                        if (epsilonClosure.add(s))
                            visit.addLast(s);
            }
        }
        return epsilonClosure;
    }

    /**
     * Returns the epsilon closure of the specified set of states in this one-way nondeterministic finite-state
     * acceptor. The returned set contains the states of this acceptor that are reachable from any state in the
     * specified set via zero or more epsilon transitions.
     * 
     * @param states the set of states whose epsilon closure is to be taken
     * 
     * @return the epsilon closure of the specified set of states in this one-way nondeterministic finite-state acceptor
     * 
     * @see #epsilonClosure(Object)
     */
    public final Set<S> epsilonClosure(Set<S> states) {
        if (states == null)
            throw new NullPointerException("Cannot take the epsilon closure of a null set of states in a one-way "
                    + "nondeterministic finite-state acceptor.");

        Set<S> epsilonClosure = new HashSet<>();
        for (S state : states)
            epsilonClosure.addAll(epsilonClosure(state));
        return epsilonClosure;
    }

    /**
     * Returns this one-way nondeterministic finite-state acceptor's set of reachable states. A state is reachable if
     * this acceptor has a sequence of zero or more transitions (including epsilon transitions) leading to it from its
     * start state. The returned set is a subset of this acceptor's set of states.
     * 
     * @return this one-way nondeterministic finite-state acceptor's set of reachable states
     * 
     * @see DFSM#states()
     * @see DFSM#transitions()
     * @see DFSM#startState()
     */
    public final Set<S> reachableStates() {
        return OneWayNFSMs.reachableStates(inputElements, transitions, startState);
    }
}
