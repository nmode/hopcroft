package ca.nmode.hopcroft.machines;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* A skeletal implementation of a finite-state machine. */
abstract class AbstractFSM<S, I, O, K, V> {
    final Set<S> states;
    final Set<S> acceptStates;
    final S startState;
    final Set<I> inputElements;
    final Set<O> outputElements;
    final Map<K, V> transitions;
    final Map<K, O> MealyTranslations;
    final Map<S, O> MooreTranslations;

    AbstractFSM(Set<S> states, Set<S> acceptStates, S startState, Set<I> inputElements, Set<O> outputElements,
            Map<K, V> transitions, Map<K, O> MealyTranslations, Map<S, O> MooreTranslations) {
        // Ensure the set of states neither is nor contains null, and is non-empty.
        if (states == null)
            throw new NullPointerException("Cannot construct a finite-state machine whose set of states is null.");
        this.states = Collections.unmodifiableSet(new HashSet<>(states));
        if (this.states.contains(null))
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose set of states contains null.");
        if (states.isEmpty())
            throw new IllegalArgumentException("Cannot construct a finite-state machine whose set of states is empty.");

        // Ensure the set of accept states is not null and is a subset of the set of states.
        if (acceptStates == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose set of accept states is null.");
        if (!this.states.containsAll(acceptStates))
            throw new IllegalArgumentException("Cannot construct a finite-state machine whose set of accept states is "
                    + "not a subset of its set of states.");
        this.acceptStates = Collections.unmodifiableSet(new HashSet<>(acceptStates));

        // Ensure the start state is in the set of states.
        if (!this.states.contains(startState))
            throw new IllegalArgumentException(
                    "Cannot construct a finite-state machine whose start state is not in its set of states.");
        this.startState = startState;

        // Ensure the set of input elements neither is nor contains null.
        if (inputElements == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose set of input elements is null.");
        this.inputElements = Collections.unmodifiableSet(new HashSet<>(inputElements));
        if (this.inputElements.contains(null))
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose set of input elements contains null.");

        // Ensure the set of output elements neither is nor contains null.
        if (outputElements == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose set of output elements is null.");
        this.outputElements = Collections.unmodifiableSet(new HashSet<>(outputElements));
        if (this.outputElements.contains(null))
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose set of output elements contains null.");

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

        /* Ensure the Mealy translation map is not null, its values are in the set of output elements and its key set is
           equal to the transition map's key set. */
        if (MealyTranslations == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose Mealy translation map is null.");
        if (!this.outputElements.containsAll(MealyTranslations.values()))
            throw new IllegalArgumentException("Cannot construct a finite-state machine whose Mealy translation map "
                    + "contains values that are not in its set of output elements.");
        if (!MealyTranslations.keySet().equals(transitions.keySet()))
            throw new IllegalArgumentException("Cannot construct a finite-state machine whose Moore translation map's "
                    + "key set is not equal to its transition map's key set.");
        this.MealyTranslations = Collections.unmodifiableMap(MealyTranslations);

        /* Ensure the Moore translation map is not null, its values are in the set of output elements and its key set is
           equal to the set of states. */
        if (MooreTranslations == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state machine whose Moore translation map is null.");
        if (!this.outputElements.containsAll(MooreTranslations.values()))
            throw new IllegalArgumentException("Cannot construct a finite-state machine whose Moore translation map "
                    + "contains values that are not in its set of output elements.");
        if (!MooreTranslations.keySet().equals(states))
            throw new IllegalArgumentException("Cannot construct a finite-state machine whose Moore translation map's "
                    + "key set is not equal to its set of states.");
        this.MooreTranslations = Collections.unmodifiableMap(MooreTranslations);
    }
}
