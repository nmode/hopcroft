package ca.nmode.hopcroft.machines;

import java.util.Map;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/* A skeletal implementation of a finite-state mealy transducer. */
abstract class AbstractMealyFST<S extends State, I, K, V, O> extends AbstractFST<S, I, K, V, O, K> {
    AbstractMealyFST(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState, Set<O> outputElements,
            Map<K, O> translations) {
        super(states, inputElements, transitions, startState, outputElements, translations);

        // Ensure the translation map's key set is equal to the transition map's key set.
        if (!translations.keySet().equals(transitions.keySet()))
            throw new IllegalArgumentException(
                    "Cannot construct a finite-state moore transducer whose translation map's key set is not equal to "
                            + "its transition map's key set.");
    }
}