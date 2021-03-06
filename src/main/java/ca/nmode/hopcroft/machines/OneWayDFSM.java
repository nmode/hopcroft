package ca.nmode.hopcroft.machines;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.nmode.hopcroft.graphs.SerialTrace;
import ca.nmode.hopcroft.graphs.StateDiagram;
import ca.nmode.hopcroft.graphs.StateVertex;
import ca.nmode.hopcroft.graphs.TransitionEdge;

import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

public class OneWayDFSM<S, I, O> extends AbstractDFSM<S, I, O, Entry<S, I>, S, List<Entry<Entry<S, I>, S>>> {

    public OneWayDFSM(Set<S> states, Set<S> acceptStates, S startState, Set<I> inputElements, Set<O> outputElements,
            Map<Entry<S, I>, S> transitions, Map<Entry<S, I>, O> MealyTranslations, Map<S, O> MooreTranslations) {
        super(states, acceptStates, startState, inputElements, outputElements, transitions, MealyTranslations,
                MooreTranslations);

        // Ensure the machine has a transition for every state-element pair.
        if (transitions.size() != states.size() * inputElements.size())
            throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose "
                    + "transition map does not contain a transition for each element in its set of input elements on "
                    + "every state in its set of states.");

        for (Entry<Entry<S, I>, S> transition : transitions.entrySet()) {
            // Ensure the key of every transition's key is in the machine's set of states.
            if (!this.states.contains(transition.getKey().getKey()))
                throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose"
                        + " transition map's keys contain a key that is not in its set of states.");
            // Ensure the value of every transition's key is in the machine's set of input elements.
            if (!this.inputElements.contains(transition.getKey().getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose"
                        + " transition map's keys contain a value that is not in its set of input elements.");
            // Ensure the value of every transition is in the machine's set of states.
            if (!this.states.contains(transition.getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way deterministic finite-state machine whose"
                        + " transition map contains a value that is not in its set of states.");
        }
    }

    public OneWayDFSM(Set<S> states, S startState, Set<I> inputElements, Set<O> outputElements,
            Map<Entry<S, I>, S> transitions, Map<Entry<S, I>, O> MealyTranslations, Map<S, O> MooreTranslations) {
        this(states, Set.of(), startState, inputElements, outputElements, transitions, MealyTranslations,
                MooreTranslations);
    }

    public OneWayDFSM(Set<S> states, Set<S> acceptStates, S startState, Set<I> inputElements,
            Map<Entry<S, I>, S> transitions) {
        this(states, acceptStates, startState, inputElements, Set.of(), transitions, Map.of(), Map.of());
    }

    public OneWayDFSM(Set<S> states, S startState, Set<I> inputElements, Map<Entry<S, I>, S> transitions) {
        this(states, Set.of(), startState, inputElements, Set.of(), transitions, Map.of(), Map.of());
    }

    @Override
    public List<Entry<Entry<S, I>, S>> compute(List<I> input) {
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
            // Halt the computation if the null state is reached.
            if (currentState == null)
                break;
        }
        return computation;
    }

    @Override
    public S classify(List<I> input) {
        // Return the final state of the computation.
        List<Entry<Entry<S, I>, S>> computation = compute(input);
        return computation.get(computation.size() - 1).getValue();
    }

    @Override
    public boolean accepts(List<I> input) {
        List<Entry<Entry<S, I>, S>> computation = compute(input);
        return acceptStates.contains(computation.get(computation.size() - 1).getValue());
    }

    @Override
    public boolean recognizes(Set<List<I>> inputs) {
        // Ensure the set of inputs neither is nor contains null.
        if (inputs == null)
            throw new NullPointerException(
                    "A one-way deterministic finite-state machine cannot attempt to recognize a null set of inputs.");
        if (inputs.contains(null))
            throw new NullPointerException("A one-way deterministic finite-state machine cannot attempt to recognize a "
                    + "set of inputs that contains null.");

        // The empty set is recognized if there are no reachable accept states.
        if (inputs.isEmpty())
            return Collections.disjoint(acceptStates, reachableStates());

        // Return true if every input in the set is accepted, false otherwise.
        for (List<I> input : inputs)
            if (!accepts(input))
                return false;
        return true;
    }

    @Override
    public List<O> MealyTransduce(List<I> input) {
        List<O> transduction = new ArrayList<>();
        List<Entry<Entry<S, I>, S>> computation = compute(input);
        // Translate the key of each transition taken in the computation to its corresponding output.
        for (Entry<Entry<S, I>, S> step : computation.subList(1, computation.size()))
            transduction.add(MealyTranslations.get(step.getKey()));
        return transduction;
    }

    @Override
    public List<O> MooreTransduce(List<I> input) {
        List<O> transduction = new ArrayList<>();
        // Translate each visited state in the computation to its corresponding output.
        for (Entry<Entry<S, I>, S> step : compute(input))
            transduction.add(MooreTranslations.get(step.getValue()));
        return transduction;
    }

    public Set<S> reachableStates() {
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
    public StateDiagram<S, I> diagram() {
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
    public SerialTrace<S, I> trace(List<I> input) {
        List<Entry<Entry<S, I>, S>> computation = compute(input);
        StateVertex<S> currentStateVertex = new StateVertex<>(startState);
        SerialTrace<S, I> trace = new SerialTrace<>(currentStateVertex);
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
