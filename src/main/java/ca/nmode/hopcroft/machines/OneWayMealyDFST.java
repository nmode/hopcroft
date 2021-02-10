package ca.nmode.hopcroft.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import ca.nmode.hopcroft.graphs.SerialTrace;
import ca.nmode.hopcroft.graphs.StateDiagram;

/**
 * A one-way {@link MealyDFST deterministic finite-state mealy transducer}. During a {@link #compute(List) computation},
 * it reads the elements in an input sequentially, from left to right, and halts once a {@link DFSM#transitions()
 * transition} on the last element is taken, provided it has not halted before then.
 *
 * @param <S> the type of this one-way deterministic finite-state mealy transducer's states
 * @param <I> the type of this one-way deterministic finite-state mealy transducer's input elements
 *
 * @author Naeem Model
 */
public class OneWayMealyDFST<S, I, O> extends AbstractMealyDFST<S, I, Entry<S, I>, S, List<Entry<Entry<S, I>, S>>, O> {
    /**
     * Constructs a new one-way deterministic finite-state mealy transducer given a set of states, set of input
     * elements, transition map, start state, set of output elements and translation map.
     * 
     * @param states         the set of states of the new one-way deterministic finite-state mealy transducer
     * @param inputElements  the set of input elements of the new one-way deterministic finite-state mealy transducer
     * @param transitions    the transition map of the new one-way deterministic finite-state mealy transducer
     * @param startState     the start state of the new one-way deterministic finite-state mealy transducer
     * @param outputElements the set of output elements of the new one-way deterministic finite-state mealy transducer
     * @param translations   the translation map of the new one-way deterministic finite-state mealy transducer
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements}, {@code transitions},
     *                                  {@code outputElements} or {@code translations} is {@code null}; {@code states},
     *                                  {@code inputElements} or {@code outputElements} contains {@code null}; or
     *                                  {@code transitions} contains {@code null} keys or values
     * @throws IllegalArgumentException if {@code states} is empty; {@code startState} is not in {@code states};
     *                                  {@code transitions}' size is not equal to the product of {@code states}' and
     *                                  {@code inputElement}'s sizes; {@code transitions} contains keys whose state or
     *                                  element is not in {@code states} and {@code inputElements}, respectively;
     *                                  {@code transitions} contains values that are not in {@code states};
     *                                  {@code translations} contains values that are not in {@code outputElements}; or
     *                                  {@code translations}' key set is not equal to {@code transitions}' key set;
     */
    public OneWayMealyDFST(Set<S> states, Set<I> inputElements, Map<Entry<S, I>, S> transitions, S startState,
            Set<O> outputElements, Map<Entry<S, I>, O> translations) {
        super(states, inputElements, transitions, startState, outputElements, translations);
        OneWayDFSMs.verifyTransitions(this.states, this.inputElements, transitions);
    }

    /**
     * Constructs a one-way deterministic finite-state mealy transducer with the specified deterministic finite-state
     * machine's set of states, set of input elements, transition map and start state, as well as the specified set of
     * output elements and translation map.
     * 
     * @param d              the deterministic finite-state machine whose set of states, set of input elements,
     *                       transition map and start state is used to construct the new one-way deterministic
     *                       finite-state mealy transducer
     * @param outputElements the set of output elements of the new one-way deterministic finite-state mealy transducer
     * @param translations   the translation map of the new one-way deterministic finite-state mealy transducer
     * 
     * @throws NullPointerException     if {@code d}, {@code outputElements}, or {@code translations} is {@code null};
     *                                  or {@code outputElements} contains {@code null}
     * @throws IllegalArgumentException if the size of {@code d}'s transition map is not equal to the product of its set
     *                                  of states' and set of input elements' sizes; {@code d}'s transition map contains
     *                                  keys whose state or element is not in its set of states and set of input
     *                                  elements, respectively; {@code d}'s transition map contains values that are not
     *                                  in its set of states; {@code translations} contains values that are not in
     *                                  {@code outputElements}; or {@code translations}' key set is not equal to the key
     *                                  set of {@code d}'s transition map
     */
    public OneWayMealyDFST(DFSM<S, I, Entry<S, I>, S, ?> d, Set<O> outputElements, Map<Entry<S, I>, O> translations) {
        this(Objects.requireNonNull(d, "Cannot construct a one-way deterministic finite-state mealy transducer from a "
                        + "null deterministic finite-state machine.")
                .states(), d.inputElements(), d.transitions(), d.startState(), outputElements, translations);
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
    public final List<O> transduce(List<I> input) {
        List<O> transduction = new ArrayList<>();
        List<Entry<Entry<S, I>, S>> computation = compute(input);
        // Translate the key of each transition taken in the computation to its corresponding output.
        for (Entry<Entry<S, I>, S> step : computation.subList(1, computation.size()))
            transduction.add(translations.get(step.getKey()));
        return transduction;
    }

    /**
     * Returns this one-way deterministic finite-state mealy transducer's set of reachable states. A state is reachable
     * if this transducer has a sequence of zero or more transitions leading to it from its start state. The returned
     * set is a subset of this transducer's set of states.
     * 
     * @return this one-way deterministic finite-state mealy transducer's set of reachable states
     * 
     * @see DFSM#states()
     * @see DFSM#transitions()
     * @see DFSM#startState()
     */
    public final Set<S> reachableStates() {
        return OneWayDFSMs.reachableStates(inputElements, transitions, startState);
    }

    /**
     * Returns this one-way deterministic finite-state mealy transducer's corresponding state diagram.
     * 
     * @return this one-way deterministic finite-state mealy transducer's corresponding state diagram.
     */
    public final StateDiagram<S, I> diagram() {
        return OneWayDFSMs.diagram(transitions, startState);
    }

    /**
     * Returns the linear trace of this one-way deterministic finite-state mealy transducer's computation on the
     * specified input.
     * 
     * @param input the sequence of elements to compute this one-way deterministic finite-state mealy transducer on
     * 
     * @return the linear trace of this one-way deterministic finite-state mealy transducer's computation on the
     *         specified input
     */
    public final SerialTrace<S, I> trace(List<I> input) {
        return OneWayDFSMs.trace(input, transitions, startState);
    }
}
