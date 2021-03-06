package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A nondeterministic finite-state machine. Beginning at its {@link #startState() start state} and on receiving a
 * sequence of {@link #inputElements() input elements}, the machine optionally reads any number of elements and
 * {@link #transitions() transitions} to none or multiple of its {@link #states() states} until some halting condition
 * is met. This process is known as its {@link #compute(List) computation} on the input. When none or more than one
 * transitions are made, the computation <i>branches</i> into a parallel computation. On a given branch, if an element
 * for which there are no possible transitions from the machine's current state is read, the branch ends up in a
 * {@code null} state and halts. The entire computation halts if every branch halts.
 *
 * @param <S> the type of this nondeterministic finite-state machine's states
 * @param <I> the type of this nondeterministic finite-state machine's input elements
 * @param <O> the type of this nondeterministic finite-state machine's output elements
 * @param <K> the type of the keys of this nondeterministic finite-state machine's transition map
 * @param <V> the type of the values of this nondeterministic finite-state machine's transition map
 * @param <C> the type of this nondeterministic finite-state machine's computations
 * 
 * @author Naeem Model
 */
public interface NFSM<S, I, O, K, V, C> {
    /**
     * Returns this nondeterministic finite-state machine's unmodifiable set of states. The returned set neither is nor
     * contains {@code null}, is non-empty, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state machine's unmodifiable set of states
     */
    Set<S> states();

    /**
     * Returns this nondeterministic finite-state machine's unmodifiable set of accept states. The returned set is a
     * subset of this machine's set of states, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state acceptor's unmodifiable set of accept states
     * 
     * @see NFSM#states()
     */
    Set<S> acceptStates();

    /**
     * Returns this nondeterministic finite-state machine's start state. The start state is an element of this
     * finite-state machine's set of states.
     * 
     * @return this nondeterministic finite-state machine's start state
     * 
     * @see #states()
     */
    S startState();

    /**
     * Returns this nondeterministic finite-state machine's unmodifiable set of input elements. The returned set neither
     * is nor contains {@code null}, and attempts to modify it result in an {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state machine's unmodifiable set of input elements
     */
    Set<I> inputElements();

    /**
     * Returns this nondeterministic finite-state machine's unmodifiable set of output elements. The returned set
     * neither is nor contains {@code null}, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state machine's unmodifiable set of output elements
     */
    Set<O> outputElements();

    /**
     * Returns this nondeterministic finite-state machine's unmodifiable transition map. The returned map neither is nor
     * contains keys or values that are {@code null}, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state machine's unmodifiable transition map
     */
    Map<K, V> transitions();

    /**
     * Returns this nondeterministic finite-state machine's unmodifiable Mealy translation map. The returned map's keys
     * are all and only the keys in this machine's transition map, with values only from its set of output elements, and
     * attempts to modify it result in an {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state machine's unmodifiable Mealy translation map
     * 
     * @see #outputElements()
     * @see #transitions()
     */
    Map<K, O> MealyTranslations();

    /**
     * Returns this nondeterministic finite-state machine's unmodifiable translation map. The returned map's keys are
     * all and only the states in this machine's set of states, with values only from its set of output elements, and
     * attempts to modify it result in an {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state machine's unmodifiable Moore translation map
     * 
     * @see #states()
     * @see #outputElements()
     */
    Map<S, O> MooreTranslations();

    /**
     * Returns this nondeterministic finite-state machine's computation on the specified input. The returned computation
     * is not {@code null}.
     * 
     * @param input the sequence of elements to compute this nondeterministic finite-state machine on
     * 
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return this nondeterministic finite-state machine's computation on the specified input
     * 
     * @see #inputElements()
     */
    C compute(List<I> input);

    /**
     * Returns a set containing the final state of every branch of this nondeterministic finite-state machine's
     * computation on the specified input.
     * 
     * @param input the sequence of elements to compute this nondeterministic finite-state machine on
     * 
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return a set containing the final state of every branch of this nondeterministic finite-state machine's
     *         computation on the specified input
     * 
     * @see #states()
     * @see #inputElements()
     * @see #compute(List)
     */
    Set<S> classify(List<I> input);

    /**
     * Returns {@code true} if the final state of any branch of this nondeterministic finite-state machine's computation
     * on the specified input is in its set of accept states, {@code false} otherwise.
     * 
     * @param input the sequence of elements to compute this nondeterministic finite-state machine on
     * 
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return {@code true} if the final state of any branch of this nondeterministic finite-state machine's computation
     *         on the specified input is in its set of accept states, {@code false} otherwise
     * 
     * @see #acceptStates()
     * @see #inputElements()
     * @see #compute(List)
     */
    boolean accepts(List<I> input);

    /**
     * Returns {@code true} if every input in the specified set is accepted by this nondeterministic finite-state
     * machine, {@code false} otherwise.
     * 
     * @param inputs the set of sequences of elements to compute this nondeterministic finite-state machine on
     * 
     * @throws NullPointerException if {@code inputs} is or contains {@code null}
     * 
     * @return {@code true} if every input in the specified set is accepted by this nondeterministic finite-state
     *         machine, {@code false} otherwise
     * 
     * @see NFSM#inputElements()
     * @see NFSM#compute(List)
     * @see #accepts(List)
     */
    boolean recognizes(Set<List<I>> inputs);

    /**
     * Returns this nondeterministic finite-state machine's Mealy transduction on the specified input. The returned set
     * neither is nor contains {@code null}.
     * 
     * @param input the sequence of elements to compute this nondeterministic finite-state machine on
     *
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return this nondeterministic finite-state machine's Mealy transduction on the specified input
     *
     * @see #inputElements()
     * @see #MealyTranslations()
     * @see #compute(List)
     */
    Set<List<O>> MealyTransduce(List<I> input);

    /**
     * Returns this nondeterministic finite-state machine's Moore transduction on the specified input. The returned set
     * neither is nor contains {@code null}.
     * 
     * @param input the sequence of elements to compute this nondeterministic finite-state machine on
     *
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return this nondeterministic finite-state machine's Moore transduction on the specified input
     *
     * @see #inputElements()
     * @see #MooreTranslations()
     * @see #compute(List)
     */
    Set<List<O>> MooreTransduce(List<I> input);
}
