package ca.nmode.hopcroft.machines;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.Map.Entry;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/* A utility class used by the one-way deterministic finite-state machines in this package. */
class OneWayDFSMUtility {
    private OneWayDFSMUtility() {}

    /* Verifies the transition maps of the one-way deterministic finite-state machines in this package. */
    static <S extends State, I> void verifyTransitions(
            DeterministicFSM<S, I, Entry<S, I>, S, List<Entry<Entry<S, I>, S>>> oneWayDFSM) {
        // Ensure the machine has a transition for every state-element pair.
        if (oneWayDFSM.transitions().size() != oneWayDFSM.states().size() * oneWayDFSM.inputElements().size())
            throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose "
                    + "transition map does not contain a transition for each element in its set of input elements on "
                    + "every state in its set of states.");

        for (Entry<Entry<S, I>, S> transition : oneWayDFSM.transitions().entrySet()) {
            // Ensure the key of every transition's key is in the machine's set of states.
            if (!oneWayDFSM.states().contains(transition.getKey().getKey()))
                throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose"
                        + " transition map's keys contain a key that is not in its set of states.");
            // Ensure the value of every transition's key is in the machine's set of input elements.
            if (!oneWayDFSM.inputElements().contains(transition.getKey().getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose"
                        + " transition map's keys contain a value that is not in its set of input elements.");
            // Ensure the value of every transition is in the machine's set of states.
            if (!oneWayDFSM.states().contains(transition.getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose"
                        + " transition map contains a value that is not in its set of states.");
        }
    }

    /* Computes the one-way deterministic finite-state machines in this package. */
    static <S extends State, I> List<Entry<Entry<S, I>, S>> computation(
            DeterministicFSM<S, I, Entry<S, I>, S, List<Entry<Entry<S, I>, S>>> oneWayDFSM, List<I> input) {
        // Ensure the input is not null.
        if (input == null)
            throw new NullPointerException(
                    "Cannot compute a one-way deterministic finite-state machine on a null input.");

        List<Entry<Entry<S, I>, S>> computation = new ArrayList<>();
        // Initialize the current state to the start state.
        S currentState = oneWayDFSM.startState();
        // Add an entry for step zero of the computation, before any element is read.
        computation.add(new SimpleEntry<>(new SimpleEntry<>(currentState, null), currentState));

        for (I inputElement : input) {
            // Create a pair of the current state and element.
            Entry<S, I> transitionKey = new SimpleEntry<>(currentState, inputElement);
            // Update the current state from the pair.
            currentState = oneWayDFSM.transitions().get(transitionKey);
            // Add an entry of the pair and updated current state to the computation.
            computation.add(new SimpleEntry<>(transitionKey, currentState));
            // Halt the computation if the current state is null.
            if (currentState == null)
                break;
        }
        return computation;
    }

    /* Classifies inputs for the one-way deterministic finite-state machines in this package. */
    static <S extends State, I> S classification(
            DeterministicFSM<S, I, Entry<S, I>, S, List<Entry<Entry<S, I>, S>>> oneWayDFSM, List<I> input) {
        // Return the final state of the computation.
        List<Entry<Entry<S, I>, S>> computation = computation(oneWayDFSM, input);
        return computation.get(computation.size() - 1).getValue();
    }

    /* Retrieves the reachable states of the one-way deterministic finite-state machines in this package. */
    static <S extends State, I> Set<S> reachableStates(
            DeterministicFSM<S, I, Entry<S, I>, S, List<Entry<Entry<S, I>, S>>> oneWayDFSM) {
        Set<S> reachableStates = new HashSet<>();
        // Add the start state to the set of reachable states and add it to the visitation queue.
        reachableStates.add(oneWayDFSM.startState());
        Deque<S> visit = new ArrayDeque<>(reachableStates);

        // Continue until transitions have been taken for all visited states on every input element.
        while (!visit.isEmpty()) {
            for (I inputElement : oneWayDFSM.inputElements()) {
                Entry<S, I> transitionKey = new SimpleEntry<>(visit.getFirst(), inputElement);
                // Add the resulting state of the transition to be visited if it was not already reached.
                if (reachableStates.add(oneWayDFSM.transitions().get(transitionKey)))
                    visit.add(oneWayDFSM.transitions().get(transitionKey));
            }
            visit.removeFirst();
        }
        return reachableStates;
    }
}