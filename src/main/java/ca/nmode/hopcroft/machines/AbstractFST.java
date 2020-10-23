package ca.nmode.hopcroft.machines;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/* A skeletal implementation of a finite-state transducer. The generic parameter 'TO' is used to specify the type of
   the translation map's keys, which is 'S' and 'K' for moore and mealy transducers, respectively. */
abstract class AbstractFST<S extends State, I, K, V, O, TO> extends AbstractFSM<S, I, K, V> {
    final Set<O> outputElements;
    final Map<TO, O> translations;

    AbstractFST(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState, Set<O> outputElements,
            Map<TO, O> translations) {
        super(states, inputElements, transitions, startState);

        // Ensure the set of output elements neither is nor contains null.
        if (outputElements == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state transducer whose set of output elements is null.");
        this.outputElements = Collections.unmodifiableSet(new HashSet<>(outputElements));
        if (this.outputElements.contains(null))
            throw new NullPointerException(
                    "Cannot construct a finite-state transducer whose set of output elements contains null.");

        // Ensure the translation map is not null, and its values are in the set of output elements.
        if (translations == null)
            throw new NullPointerException("Cannot construct a finite-state transducer whose translation map is null.");
        if (!this.outputElements.containsAll(translations.values()))
            throw new IllegalArgumentException("Cannot construct a finite-state transducer whose translation map "
                    + "contains values that are not in its set of output elements.");
        this.translations = Collections.unmodifiableMap(new HashMap<>(translations));
    }
}