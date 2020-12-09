package ca.nmode.hopcroft.machines;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* A skeletal implementation of a finite-state machine. */
abstract class AbstractFSM<S, I, K, V> {
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

    /* Verifies the accept states of the abstract finite-state acceptors in this package. */
    static <S> Set<S> verifyAcceptStates(Set<S> states, Set<S> acceptStates) {
        // Ensure the set of accept states is not null and is a subset of the set of states.
        if (acceptStates == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state acceptor whose set of accept states is null.");
        if (!states.containsAll(acceptStates))
            throw new IllegalArgumentException("Cannot construct a finite-state acceptor whose set of accept states is "
                    + "not a subset of its set of states.");
        return Collections.unmodifiableSet(new HashSet<>(acceptStates));
    }

    /* Verifies the output elements of the abstract finite-state transducers in this package. */
    static <O> Set<O> verifyOutputElements(Set<O> outputElements) {
        // Ensure the set of output elements neither is nor contains null.
        if (outputElements == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state transducer whose set of output elements is null.");
        Set<O> unmodifiableOutputElements = Collections.unmodifiableSet(new HashSet<>(outputElements));
        if (unmodifiableOutputElements.contains(null))
            throw new NullPointerException(
                    "Cannot construct a finite-state transducer whose set of output elements contains null.");
        return unmodifiableOutputElements;
    }

    /* Verifies the translation maps of the abstract finite-state transducers in this package. */
    static <S, O, T> Map<T, O> verifyTranslations(AbstractFSM<S, ?, ?, ?> transducer, Set<O> outputElements,
            Map<T, O> translations) {
        // Ensure the translation map is not null, and its values are in the set of output elements.
        if (translations == null)
            throw new NullPointerException("Cannot construct a finite-state transducer whose translation map is null.");
        if (!outputElements.containsAll(translations.values()))
            throw new IllegalArgumentException("Cannot construct a finite-state transducer whose translation map "
                    + "contains values that are not in its set of output elements.");

        // Ensure the translation map's key set is equal to the transition map's key set if the transducer is mealy.
        if ((transducer instanceof AbstractMealyDFST || transducer instanceof AbstractMealyNFST)
                && !translations.keySet().equals(transducer.transitions.keySet()))
            throw new IllegalArgumentException("Cannot construct a finite-state mealy transducer whose translation "
                    + "map's key set is not equal to its transition map's key set.");

        // Ensure the translation map's key set is equal to the set of states if the transducer is moore.
        if ((transducer instanceof AbstractMooreDFST || transducer instanceof AbstractMooreNFST)
                && !translations.keySet().equals(transducer.states))
            throw new IllegalArgumentException("Cannot construct a finite-state moore transducer whose translation "
                    + "map's key set is not equal to its set of states.");
        return Collections.unmodifiableMap(new HashMap<>(translations));
    }
}
