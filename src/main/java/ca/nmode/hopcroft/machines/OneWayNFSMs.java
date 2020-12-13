package ca.nmode.hopcroft.machines;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* A utility class used by the one-way nondeterministic finite-state machines in this package. */
class OneWayNFSMs {
    private OneWayNFSMs() {}

    /* Verifies the transition maps of the one-way nondeterministic finite-state machines in this package. */
    static <S, I> void verifyTransitions(Set<S> states, Set<I> inputElements, Map<Entry<S, I>, Set<S>> transitions) {
        for (Entry<Entry<S, I>, Set<S>> transition : transitions.entrySet()) {
            // Ensure the key of every transition's key is in the set of states.
            if (!states.contains(transition.getKey().getKey()))
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state machine "
                        + "whose transition map's keys contain a key that is not in its set of states.");
            // Ensure non-null values of the transition keys are in the set of input elements.
            if (transition.getKey().getValue() != null && !inputElements.contains(transition.getKey().getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state machine "
                        + "whose transition map's keys contain a non-null value that is not in its set of input "
                        + "elements");
            // Ensure the value of every transition is a subset of the set of states.
            if (!states.containsAll(transition.getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state machine "
                        + "whose transition map contains a value that is not a subset of its set of states.");
        }
    }
}
