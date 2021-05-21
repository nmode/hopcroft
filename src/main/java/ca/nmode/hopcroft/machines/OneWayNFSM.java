package ca.nmode.hopcroft.machines;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

public class OneWayNFSM<S, I, O>
        extends AbstractNFSM<S, I, O, Entry<S, I>, Set<S>, List<Entry<Entry<Set<S>, I>, Set<S>>>> {
    private boolean hasEpsilon;

    public OneWayNFSM(Set<S> states, Set<S> acceptStates, S startState, Set<I> inputElements, Set<O> outputElements,
            Map<Entry<S, I>, Set<S>> transitions, Map<Entry<S, I>, O> MealyTranslations, Map<S, O> MooreTranslations) {
        super(states, acceptStates, startState, inputElements, outputElements, transitions, MealyTranslations,
                MooreTranslations);

        hasEpsilon = false;
        for (Entry<Entry<S, I>, Set<S>> transition : transitions.entrySet()) {
            // Ensure the key of every transition's key is in the set of states.
            if (!this.states.contains(transition.getKey().getKey()))
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state machine "
                        + "whose transition map's keys contain a key that is not in its set of states.");

            // Ensure values of transition keys are either null (for acceptors) or in the set of input elements.
            if (transition.getKey().getValue() == null)
                hasEpsilon = true;
            else if (!this.inputElements().contains(transition.getKey().getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state machine "
                        + "whose transition map's keys contain a value that is not in its set of input elements.");
            // Ensure the value of every transition is a subset of the set of states.
            if (!this.states().containsAll(transition.getValue()))
                throw new IllegalArgumentException("Cannot construct a one-way nondeterministic finite-state machine "
                        + "whose transition map contains a value that is not a subset of its set of states.");
        }
    }

    public OneWayNFSM(Set<S> states, S startState, Set<I> inputElements, Set<O> outputElements,
            Map<Entry<S, I>, Set<S>> transitions, Map<Entry<S, I>, O> MealyTranslations, Map<S, O> MooreTranslations) {
        this(states, Set.of(), startState, inputElements, outputElements, transitions, MealyTranslations,
                MooreTranslations);
    }

    public OneWayNFSM(Set<S> states, Set<S> acceptStates, S startState, Set<I> inputElements,
            Map<Entry<S, I>, Set<S>> transitions) {
        this(states, acceptStates, startState, inputElements, Set.of(), transitions, Map.of(), Map.of());
    }

    public OneWayNFSM(Set<S> states, S startState, Set<I> inputElements, Map<Entry<S, I>, Set<S>> transitions) {
        this(states, Set.of(), startState, inputElements, Set.of(), transitions, Map.of(), Map.of());
    }

    @Override
    public List<Entry<Entry<Set<S>, I>, Set<S>>> compute(List<I> input) {
        // Ensure the input is not null.
        if (input == null)
            throw new NullPointerException(
                    "Cannot compute a one-way nondeterministic finite-state machine on a null input.");

        // Initialize the current states to the epsilon closure of the start state.
        Set<S> currentStates = new HashSet<>(epsilonClosure(startState));

        // Add an entry for step zero of the computation, before any element is read.
        List<Entry<Entry<Set<S>, I>, Set<S>>> computation = new ArrayList<>();
        computation.add(new SimpleEntry<>(new SimpleEntry<>(Set.of(startState), null), currentStates));

        for (I inputElement : input) {
            Set<S> nextStates = new HashSet<>();
            // Retrieve the next states of every current state on the current input element.
            for (S currentState : currentStates) {
                Set<S> transitionValue = transitions.get(new SimpleEntry<>(currentState, inputElement));
                if (transitionValue == null || transitionValue.isEmpty())
                    nextStates.add(null);
                else
                    nextStates.addAll(transitionValue);
            }
            nextStates.addAll(epsilonClosure(nextStates));
            computation.add(new SimpleEntry<>(new SimpleEntry<>(currentStates, inputElement), nextStates));

            if (nextStates.size() == 1 && nextStates.contains(null))
                break;

            currentStates = nextStates;
        }

        return computation;
    }

    @Override
    public Set<S> classify(List<I> input) {
        List<Entry<Entry<Set<S>, I>, Set<S>>> computation = compute(input);
        return computation.get(computation.size() - 1).getValue();
    }

    @Override
    public boolean accepts(List<I> input) {
        if (input == null)
            throw new NullPointerException();
        return !Collections.disjoint(acceptStates, classify(input));
    }

    @Override
    public boolean recognizes(Set<List<I>> inputs) {
        // Ensure the set of inputs neither is nor contains null.
        if (inputs == null)
            throw new NullPointerException("A one-way nondeterministic finite-state machine cannot attempt to recognize"
                    + "a null set of inputs.");
        if (inputs.contains(null))
            throw new NullPointerException("A one-way nondeterministic finite-state machine cannot attempt to recognize"
                    + "a set of inputs that contains null.");

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
    public Set<List<O>> MealyTransduce(List<I> input) {
        // TODO
        return null;
    }

    @Override
    public Set<List<O>> MooreTransduce(List<I> input) {
        // TODO
        return null;
    }

    public boolean hasEpsilon() {
        return hasEpsilon;
    }

    /* Retrieves the reachable states of the one-way nondeterministic finite-state machines in this package. */
    public Set<S> reachableStates() {
        Set<S> reachableStates = new HashSet<>();
        // Add the start state to the set of reachable states and add it to the visitation queue.
        reachableStates.add(startState);
        Deque<S> visit = new ArrayDeque<>(reachableStates);

        Set<I> inputElementsWithNull = new HashSet<>(inputElements);
        inputElementsWithNull.add(null);
        // Continue until transitions have been taken for all visited states on every input element, as well as null.
        while (!visit.isEmpty()) {
            for (I inputElement : inputElementsWithNull) {
                Set<S> output = transitions.get(new SimpleEntry<>(visit.getFirst(), inputElement));
                if (output != null)
                    for (S state : output)
                        // Add the resulting state of the transition to be visited if it was not already reached.
                        if (reachableStates.add(state))
                            visit.add(state);
            }
            visit.removeFirst();
        }
        return reachableStates;
    }

    public final Set<S> epsilonClosure(S state) {
        Set<S> epsilonClosure = new HashSet<>();
        // If the specified state is not in the set of states, return the empty set.
        if (states.contains(state)) {
            epsilonClosure.add(state);
            Deque<S> visit = new ArrayDeque<>(epsilonClosure);
            // Continue until epsilon transitions have been taken for all visited states
            while (!visit.isEmpty()) {
                Set<S> transitionValue = transitions.get(new SimpleEntry<>(visit.removeFirst(), null));
                if (transitionValue != null)
                    for (S s : transitionValue)
                        // Add the resulting state of the transition to be visited if it was not already reached.
                        if (epsilonClosure.add(s))
                            visit.addLast(s);
            }
        }
        return epsilonClosure;
    }

    /**
     * Returns the epsilon closure of the specified set of states in this one-way nondeterministic finite-state
     * acceptor. The returned set contains the states of this acceptor that are reachable from any state in the
     * specified set via zero or more epsilon transitions.
     * 
     * @param states the set of states whose epsilon closure is to be taken
     * 
     * @return the epsilon closure of the specified set of states in this one-way nondeterministic finite-state acceptor
     * 
     * @see #epsilonClosure(Object)
     */
    public final Set<S> epsilonClosure(Set<S> states) {
        if (states == null)
            throw new NullPointerException("Cannot take the epsilon closure of a null set of states in a one-way "
                    + "nondeterministic finite-state acceptor.");

        Set<S> epsilonClosure = new HashSet<>();
        for (S state : states)
            epsilonClosure.addAll(epsilonClosure(state));
        return epsilonClosure;
    }
}
