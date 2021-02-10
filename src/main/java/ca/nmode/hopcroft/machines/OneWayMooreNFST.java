package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A one-way {@link MooreNFST nondeterministic finite-state moore transducer}. During a {@link #compute(List)
 * computation}, it reads the elements in an input sequentially, from left to right, and halts once a
 * {@link NFSM#transitions() transition} on the last element is taken, provided it has not halted before then.
 *
 * @param <S> the type of this one-way nondeterministic finite-state moore transducer's states
 * @param <I> the type of this one-way nondeterministic finite-state moore transducer's input elements
 *
 * @author Naeem Model
 */
public class OneWayMooreNFST<S, I, O>
        extends AbstractMooreNFST<S, I, Entry<S, I>, Set<S>, List<Entry<Entry<Set<S>, I>, Set<S>>>, O> {
    /**
     * Constructs a new one-way nondeterministic finite-state moore transducer given a set of states, set of input
     * elements, transition map, start state, set of output elements and translation map.
     * 
     * @param states         the set of states of the new one-way nondeterministic finite-state moore transducer
     * @param inputElements  the set of input elements of the new one-way nondeterministic finite-state moore transducer
     * @param transitions    the transition map of the new one-way nondeterministic finite-state moore transducer
     * @param startState     the start state of the new one-way nondeterministic finite-state moore transducer
     * @param outputElements the set of output elements of the new one-way nondeterministic finite-state moore
     *                       transducer
     * @param translations   the translation map of the new one-way nondeterministic finite-state moore transducer
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements}, {@code transitions},
     *                                  {@code outputElements} or {@code translations} is {@code null}; {@code states},
     *                                  {@code inputElements} or {@code outputElements} contains {@code null}; or
     *                                  {@code transitions} contains {@code null} keys or values
     * @throws IllegalArgumentException if {@code states} is empty; {@code startState} is not in {@code states};
     * 
     *                                  {@code transitions}' size is not equal to the product of {@code states}' and
     *                                  {@code inputElement}'s sizes; {@code transitions}' contains keys whose state or
     *                                  element is not in {@code states} and {@code inputElements}, respectively;
     *                                  {@code transitions} contains values that are not subsets of{@code states};
     *                                  {@code translations} contains values that are not in {@code outputElements}; or
     *                                  {@code translations}' key set is not equal to {@code states};
     * 
     */
    public OneWayMooreNFST(Set<S> states, Set<I> inputElements, Map<Entry<S, I>, Set<S>> transitions, S startState,
            Set<O> outputElements, Map<S, O> translations) {
        super(states, inputElements, transitions, startState, outputElements, translations);
        OneWayNFSMs.verifyTransitions(this);
    }

    /**
     * Constructs a one-way nondeterministic finite-state moore transducer with the specified nondeterministic
     * finite-state machine's set of states, set of input elements, transition map and start state, as well as the
     * specified set of output elements and translation map.
     * 
     * @param n              the nondeterministic finite-state machine whose set of states, set of input elements,
     *                       transition map and start state is used to construct the new one-way nondeterministic
     *                       finite-state moore transducer
     * @param outputElements the set of output elements of the new one-way nondeterministic finite-state moore
     *                       transducer
     * @param translations   the translation map of the new one-way nondeterministic finite-state moore transducer
     * 
     * @throws NullPointerException     if {@code n}, {@code outputElements}, or {@code translations} is {@code null};
     *                                  or {@code outputElements} contains {@code null}
     * @throws IllegalArgumentException if {@code n}'s transition map contains keys whose state or element is not in its
     *                                  set of states and set of input elements, respectively; {@code n}'s transition
     *                                  map contains values that are not subsets of its set of states;
     *                                  {@code translations} contains values that are not in {@code outputElements}; or
     *                                  {@code translations}' key set is not equal to {@code n}'s set of states
     */
    public OneWayMooreNFST(NFSM<S, I, Entry<S, I>, Set<S>, ?> n, Set<O> outputElements, Map<S, O> translations) {
        this(Objects.requireNonNull(n, "Cannot construct a one-way nondeterministic finite-state moore transducer from "
                        + "a null deterministic finite-state machine.")
                .states(), n.inputElements(), n.transitions(), n.startState(), outputElements, translations);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The computation is described by a list of entries. The entry at index {@code i} represents step {@code i} of the
     * computation. Each entry has a subentry as its key; the subentry's key is the set of current states and its value
     * is the element read. The value of each entry is the union of the output of this machine's {@link #transitions()
     * transition map} on every current state with the current element. In other words, each entry is the combination of
     * the transitions undergone at the same step of every active branch.
     * <p>
     * Every computation contains an entry at index {@code 0}. Its subentry consists of the singleton set containing the
     * {@link #startState() start state} and {@code null} as the key and value, respectively, and its value is the same
     * singleton set. This is interpreted as the machine beginning and staying at the start state before reading any
     * element.
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
    public final Set<List<O>> transduce(List<I> input) {
        // TODO
        return null;
    }

    /**
     * Returns this one-way nondeterministic finite-state Moore transducer's set of reachable states. A state is
     * reachable if this Moore transducer has a sequence of zero or more transitions (including epsilon transitions)
     * leading to it from its start state. The returned set is a subset of this Moore transducer's set of states.
     * 
     * @return this one-way nondeterministic finite-state Moore transducer's set of reachable states
     * 
     * @see DFSM#states()
     * @see DFSM#transitions()
     * @see DFSM#startState()
     */
    public final Set<S> reachableStates() {
        return OneWayNFSMs.reachableStates(inputElements, transitions, startState);
    }
}
