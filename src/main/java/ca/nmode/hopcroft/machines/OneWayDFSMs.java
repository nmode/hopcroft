package ca.nmode.hopcroft.machines;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.Map.Entry;
import java.util.Set;

import ca.nmode.hopcroft.graphs.LinearTrace;
import ca.nmode.hopcroft.graphs.StateDiagram;
import ca.nmode.hopcroft.graphs.StateVertex;
import ca.nmode.hopcroft.graphs.TransitionEdge;

/* A utility class used by the one-way deterministic finite-state machines in this package. */
class OneWayDFSMs {
    private OneWayDFSMs() {}

    /* Verifies the transition maps of the one-way deterministic finite-state machines in this package. */
    static <S, I> void verifyTransitions(Set<S> states, Set<I> inputElements, Map<Entry<S, I>, S> transitions) {
        // Ensure the machine has a transition for every state-element pair.
        if (transitions.size() != states.size() * inputElements.size())
            throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose "
                    + "transition map does not contain a transition for each element in its set of input elements on "
                    + "every state in its set of states.");

        for (Entry<Entry<S, I>, S> transition : transitions.entrySet()) {
            // Ensure the key of every transition's key is in the machine's set of states.
            if (!states.contains(transition.getKey().getKey()))
                throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose"
                        + " transition map's keys contain a key that is not in its set of states.");
            // Ensure the value of every transition's key is in the machine's set of input elements.
            if (!inputElements.contains(transition.getKey().getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose"
                        + " transition map's keys contain a value that is not in its set of input elements.");
            // Ensure the value of every transition is in the machine's set of states.
            if (!states.contains(transition.getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose"
                        + " transition map contains a value that is not in its set of states.");
        }
    }

    /* Computes the one-way deterministic finite-state machines in this package. */
    static <S, I> List<Entry<Entry<S, I>, S>> compute(List<I> input, Map<Entry<S, I>, S> transitions, S startState) {
        // Ensure the input is not null.
        if (input == null)
            throw new NullPointerException(
                    "Cannot compute a one-way deterministic finite-state machine on a null input.");

        List<Entry<Entry<S, I>, S>> computation = new ArrayList<>();
        // Initialize the current state to the start state.
        S currentState = startState;
        // Add an entry for step zero of the computation, before any element is read.
        computation.add(new SimpleEntry<>(new SimpleEntry<>(currentState, null), currentState));

        for (I inputElement : input) {
            // Create a pair of the current state and element.
            Entry<S, I> transitionKey = new SimpleEntry<>(currentState, inputElement);
            // Update the current state from the pair.
            currentState = transitions.get(transitionKey);
            // Add an entry of the pair and updated current state to the computation.
            computation.add(new SimpleEntry<>(transitionKey, currentState));
            // Halt the computation if the current state is null.
            if (currentState == null)
                break;
        }
        return computation;
    }

    /* Classifies inputs for the one-way deterministic finite-state machines in this package. */
    static <S, I> S classify(List<I> input, Map<Entry<S, I>, S> transitions, S startState) {
        // Return the final state of the computation.
        List<Entry<Entry<S, I>, S>> computation = compute(input, transitions, startState);
        return computation.get(computation.size() - 1).getValue();
    }

    /* Retrieves the reachable states of the one-way deterministic finite-state machines in this package. */
    static <S, I> Set<S> reachableStates(Set<I> inputElements, Map<Entry<S, I>, S> transitions, S startState) {
        Set<S> reachableStates = new HashSet<>();
        // Add the start state to the set of reachable states and add it to the visitation queue.
        reachableStates.add(startState);
        Deque<S> visit = new ArrayDeque<>(reachableStates);

        // Continue until transitions have been taken for all visited states on every input element.
        while (!visit.isEmpty()) {
            for (I inputElement : inputElements) {
                Entry<S, I> transitionKey = new SimpleEntry<>(visit.getFirst(), inputElement);
                // Add the resulting state of the transition to be visited if it was not already reached.
                if (reachableStates.add(transitions.get(transitionKey)))
                    visit.add(transitions.get(transitionKey));
            }
            visit.removeFirst();
        }
        return reachableStates;
    }

    /* Constructs the state diagrams of the one-way deterministic finite-state machines in this package. */
    static <S, I> StateDiagram<S, I> diagram(Map<Entry<S, I>, S> transitions, S startState) {
        StateDiagram<S, I> diagram = new StateDiagram<>(startState);
        for (Entry<Entry<S, I>, S> transition : transitions.entrySet()) {
            diagram.addVertex(transition.getKey().getKey());
            diagram.addVertex(transition.getValue());
            diagram.addEdge(transition.getKey().getKey(), transition.getValue(),
                    new TransitionEdge<>(transition.getKey().getValue()));
        }
        return diagram;
    }

    /* Constructs traces of the one-way deterministic finite-state machines' computations in this package. */
    static <S, I> LinearTrace<S, I> trace(List<I> input, Map<Entry<S, I>, S> transitions, S startState) {
        List<Entry<Entry<S, I>, S>> computation = compute(input, transitions, startState);
        StateVertex<S> currentStateVertex = new StateVertex<>(startState);
        LinearTrace<S, I> trace = new LinearTrace<>(currentStateVertex);
        trace.addVertex(currentStateVertex);
        for (Entry<Entry<S, I>, S> step : computation.subList(1, computation.size())) {
            StateVertex<S> nextStateVertex = new StateVertex<>(step.getValue());
            trace.addVertex(nextStateVertex);
            trace.addEdge(currentStateVertex, nextStateVertex, new TransitionEdge<>(step.getKey().getValue()));
            currentStateVertex = nextStateVertex;
        }
        return trace;
    }
}
