package ca.nmode.hopcroft.machines;

import java.util.Map;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/* A skeletal implementation of a finite-state moore transducer. */
abstract class AbstractMooreFST<S extends State, I, K, V, O> extends AbstractFST<S, I, K, V, O, S> {
    AbstractMooreFST(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState, Set<O> outputElements,
            Map<S, O> translations) {
        super(states, inputElements, transitions, startState, outputElements, translations);

        // Ensure the set of output elements is non-empty.
        if (outputElements.isEmpty())
            throw new IllegalArgumentException(
                    "Cannot construct a finite-state moore transducer whose set of output elements is empty.");

        // Ensure the translation map's key set is equal to the set of states.
        if (!translations.keySet().equals(states))
            throw new IllegalArgumentException(
                    "Cannot construct a finite-state moore transducer whose translation map's key set is not equal to "
                            + "its set of states.");
    }
}