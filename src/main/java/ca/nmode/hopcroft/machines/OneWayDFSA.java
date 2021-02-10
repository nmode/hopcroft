package ca.nmode.hopcroft.machines;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import ca.nmode.hopcroft.graphs.SerialTrace;
import ca.nmode.hopcroft.graphs.StateDiagram;
import ca.nmode.hopcroft.graphs.StateVertex;

/**
 * A one-way {@link DFSA deterministic finite-state acceptor}. During a {@link #compute(List) computation}, it reads the
 * elements in an input sequentially, from left to right, and halts once a {@link DFSM#transitions() transition} on the
 * last element is taken, provided it has not halted before then.
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
        OneWayDFSMs.verifyTransitions(this.states, this.inputElements, transitions);
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
     * Constructs a one-way deterministic finite-state acceptor with the specified deterministic finite-state machine's
     * set of states, set of input elements, transition map and start state, as well as the specified set of accept
     * states.
     * 
     * @param d            the deterministic finite-state machine whose set of states, set of input elements, transition
     *                     map and start state is used to construct the new one-way deterministic finite-state acceptor
     * @param acceptStates the set of accept states of the new one-way deterministic finite-state acceptor
     * 
     * @throws NullPointerException     if {@code d} or {@code acceptStates} is {@code null}
     * @throws IllegalArgumentException if {@code acceptStates} is not a subset of {@code d}'s states; the size of
     *                                  {@code d}'s transition map is not equal to the product of its set of states' and
     *                                  set of input elements' sizes; {@code d}'s transition map contains keys whose
     *                                  state or element is not in its set of states and set of input elements,
     *                                  respectively; or {@code d}'s transition map contains values that are not in its
     *                                  set of states
     */
    public OneWayDFSA(DFSM<S, I, Entry<S, I>, S, ?> d, Set<S> acceptStates) {
        this(Objects.requireNonNull(d, "Cannot construct a one-way deterministic finite-state acceptor from a null "
                        + "deterministic finite-state machine.")
                .states(), d.inputElements(), d.transitions(), d.startState(), acceptStates);
    }

    /**
     * Constructs a one-way deterministic finite-state acceptor with the specified deterministic finite-state machine's
     * set of states, set of input elements, transition map and start state, as well as an empty set of accept states.
     * 
     * @param d the deterministic finite-state machine whose set of states, set of input elements, transition map and
     *          start state is used to construct the returned one-way deterministic finite-state acceptor
     * 
     * @throws NullPointerException     if {@code d} is {@code null}
     * @throws IllegalArgumentException if the size of {@code d}'s transition map is not equal to the product of its set
     *                                  of states' and set of input elements' sizes; {@code d}'s transition map contains
     *                                  keys whose state or element is not in its set of states and set of input
     *                                  elements, respectively; or {@code d}'s transition map contains values that are
     *                                  not in {@code states}
     */
    public OneWayDFSA(DFSM<S, I, Entry<S, I>, S, ?> d) {
        this(d, Set.of());
    }

    /**
     * {@inheritDoc}
     * <p>
     * The computation is described by a list of entries. The entry at index {@code i} represents step {@code i} of the
     * computation. Each entry has a subentry as its key; the subentry's key is the current state and its value is the
     * element read. The value of each entry is the output of this machine's {@link #transitions()} transition map} on
     * the subentry. In other words, each entry is an undergone transition.
     * <p>
     * Every computation contains an entry at index {@code 0}. Its subentry consists of the {@link #startState() start
     * state} and {@code null} as the key and value, respectively, and its value is also the start state. This is
     * interpreted as the machine beginning and staying at the start state before reading any element.
     * 
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public final List<Entry<Entry<S, I>, S>> compute(List<I> input) {
        return OneWayDFSMs.compute(input, transitions, startState);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public final S classify(List<I> input) {
        return OneWayDFSMs.classify(input, transitions, startState);
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
     * @see DFSM#states()
     * @see DFSM#transitions()
     * @see DFSM#startState()
     */
    public final Set<S> reachableStates() {
        return OneWayDFSMs.reachableStates(inputElements, transitions, startState);
    }

    /**
     * Returns this one-way deterministic finite-state acceptor's corresponding state diagram.
     * 
     * @return this one-way deterministic finite-state acceptor's corresponding state diagram.
     */
    public final StateDiagram<S, I> diagram() {
        StateDiagram<S, I> diagram = OneWayDFSMs.diagram(transitions, startState);
        for (S state : acceptStates)
            diagram.addAcceptVertex(state);
        return diagram;
    }

    /**
     * Returns the linear trace of this one-way deterministic finite-state acceptor's computation on the specified
     * input.
     * 
     * @param input the sequence of elements to compute this one-way deterministic finite-state acceptor on
     * 
     * @return the linear trace of this one-way deterministic finite-state acceptor's computation on the specified input
     */
    public final SerialTrace<S, I> trace(List<I> input) {
        SerialTrace<S, I> trace = OneWayDFSMs.trace(input, transitions, startState);
        for (StateVertex<S> vertex : trace.vertexSet())
            if (acceptStates.contains(vertex.state()))
                trace.addAcceptVertex(vertex);
        return trace;
    }
}
