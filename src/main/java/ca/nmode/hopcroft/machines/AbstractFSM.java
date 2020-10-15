package ca.nmode.hopcroft.machines;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/* A skeletal implementation of a finite-state machine. */
abstract class AbstractFSM<S extends State, I, K, V> {
    final Set<S> states;
    final Set<I> inputElements;
    final Map<K, V> transitions;
    final S startState;

    AbstractFSM(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState) {
        // Ensure the set of states neither is nor contains null, and is non-empty.
        if (states == null)
            throw new NullPointerException("Cannot construct a finite-state machine whose set of states is null.");
        this.states = Collections.unmodifiableSet(new HashSet<>(states));
        if (this.states.contains(null))
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose set of states contains null.");
        if (states.isEmpty())
            throw new IllegalArgumentException("Cannot construct a finite-state machine whose set of states is empty.");

        // Ensure the set of input elements neither is nor contains null.
        if (inputElements == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose set of input elements is null.");
        this.inputElements = Collections.unmodifiableSet(new HashSet<>(inputElements));
        if (this.inputElements.contains(null))
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose set of input elements contains null.");

        // Ensure the transition map neither is nor contains keys or values that are null.
        if (transitions == null)
            throw new NullPointerException("Cannot construct a finite-state machine whose transition map is null.");
        this.transitions = Collections.unmodifiableMap(new HashMap<>(transitions));
        if (this.transitions.containsKey(null))
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose transition map contains null keys.");
        if (this.transitions.containsValue(null))
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose transition map contains null values.");

        // Ensure the start state is in the set of states.
        if (!this.states.contains(startState))
            throw new IllegalArgumentException(
                    "Cannot construct a finite-state machine whose start state is not in its set of states.");
        this.startState = startState;
    }
}