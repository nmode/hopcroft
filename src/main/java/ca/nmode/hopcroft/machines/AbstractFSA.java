package ca.nmode.hopcroft.machines;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* A skeletal implementation of a finite-state acceptor. */
abstract class AbstractFSA<S, I, K, V> extends AbstractFSM<S, I, K, V> {
    final Set<S> acceptStates;

    AbstractFSA(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState, Set<S> acceptStates) {
        super(states, inputElements, transitions, startState);

        // Ensure the set of accept states is not null and is a subset of the set of states.
        if (acceptStates == null)
            throw new NullPointerException(
                    "Cannot construct a finite-state acceptor whose set of accept states is null.");
        if (!this.states.containsAll(acceptStates))
            throw new IllegalArgumentException("Cannot construct a finite-state acceptor whose set of accept states is "
                    + "not a subset of its set of states.");
        this.acceptStates = Collections.unmodifiableSet(new HashSet<>(acceptStates));
    }
}