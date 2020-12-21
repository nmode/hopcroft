package ca.nmode.hopcroft.machines;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* A utility class used by the one-way nondeterministic finite-state machines in this package. */
class OneWayNFSMs {
    private OneWayNFSMs() {}

    /* Verifies the transition maps of the one-way nondeterministic finite-state machines in this package. */
    static <S, I> boolean verifyTransitions(NFSM<S, I, Entry<S, I>, Set<S>, ?> oneWayNFSM) {
        boolean hasEpsilon = false;
        for (Entry<Entry<S, I>, Set<S>> transition : oneWayNFSM.transitions().entrySet()) {
            // Ensure the key of every transition's key is in the set of states.
            if (!oneWayNFSM.states().contains(transition.getKey().getKey()))
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state machine "
                        + "whose transition map's keys contain a key that is not in its set of states.");
            // Ensure only one-way NFSAs have epsilon transitions.
            if (!(oneWayNFSM instanceof OneWayNFSA) && transition.getKey().getValue() == null)
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state transducer"
                        + " whose transition map contains epsilon transitions.");
            // Ensure values of transition keys are either null (for acceptors) or in the set of input elements.
            if (transition.getKey().getValue() == null)
                hasEpsilon = true;
            else if (!oneWayNFSM.inputElements().contains(transition.getKey().getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state machine "
                        + "whose transition map's keys contain a value that is not in its set of input elements.");
            // Ensure the value of every transition is a subset of the set of states.
            if (!oneWayNFSM.states().containsAll(transition.getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state machine "
                        + "whose transition map contains a value that is not a subset of its set of states.");
        }
        return hasEpsilon;
    }

    /* Retrieves the reachable states of the one-way nondeterministic finite-state machines in this package. */
    static <S, I> Set<S> reachableStates(Set<I> inputElements, Map<Entry<S, I>, Set<S>> transitions, S startState) {
        Set<S> reachableStates = new HashSet<>();
        // Add the start state to the set of reachable states and add it to the visitation queue.
        reachableStates.add(startState);
        Deque<S> visit = new ArrayDeque<>(reachableStates);

        Set<I> inputElementsWithNull = new HashSet<>(inputElements);
        inputElementsWithNull.add(null);
        // Continue until transitions have been taken for all visited states on every input element, as well as null.
        while (!visit.isEmpty()) {
            for (I inputElement : inputElementsWithNull) {
                Set<S> transitionValue = transitions.get(new SimpleEntry<>(visit.getFirst(), inputElement));
                if (transitionValue != null)
                    for (S state : transitionValue)
                        // Add the resulting state of the transition to be visited if it was not already reached.
                        if (reachableStates.add(state))
                            visit.add(state);
            }
            visit.removeFirst();
        }
        return reachableStates;
    }
}
