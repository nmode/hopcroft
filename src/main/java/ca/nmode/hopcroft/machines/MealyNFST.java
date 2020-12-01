package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A nondeterministic finite-state mealy transducer. It is a {@link NondeterministicFSM nondeterministic finite-state
 * machine} which {@link #translations() translates} each undergone {@link NondeterministicFSM#transitions() transition}
 * in every branch of the {@link NondeterministicFSM#compute(List) computation} of an input to an
 * {@link #outputElements() output element}. The process which yields the corresponding output sequence for each branch
 * is known as its {@link #transduce(List) transduction} on the input.
 * 
 * @param <S> the type of this nondeterministic finite-state mealy transducer's states
 * @param <I> the type of this nondeterministic finite-state mealy transducer's input elements
 * @param <K> the type of the keys of this nondeterministic finite-state mealy transducer's transition map
 * @param <V> the type of the values of this nondeterministic finite-state mealy transducer's transition map
 * @param <C> the type of this nondeterministic finite-state mealy transducer's computations
 * @param <O> the type of this nondeterministic finite-state mealy transducer's output elements
 *
 * @author Naeem Model
 */
public interface MealyNFST<S, I, K, V, C, O> extends NondeterministicFSM<S, I, K, V, C> {
    /**
     * Returns this nondeterministic finite-state mealy transducer's unmodifiable set of output elements. The returned
     * set neither is nor contains {@code null}, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state mealy transducer's unmodifiable set of output elements
     */
    Set<O> outputElements();

    /**
     * Returns this nondeterministic finite-state mealy transducer's unmodifiable translation map. The returned map's
     * keys are all and only the keys in this machine's transition map, with values only from its set of output
     * elements, and attempts to modify it result in an {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state mealy transducer's unmodifiable translation map
     * 
     * @see NondeterministicFSM#transitions()
     * @see #outputElements()
     */
    Map<K, O> translations();

    /**
     * Returns this nondeterministic finite-state mealy transducer's {@link MealyNFST transduction} on the specified
     * input. The returned set neither is nor contains {@code null}.
     * 
     * @param input the sequence of elements to compute this nondeterministic finite-state mealy transducer on
     *
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return this nondeterministic finite-state mealy transducer's transduction on the specified input
     *
     * @see NondeterministicFSM#inputElements()
     * @see NondeterministicFSM#compute(List)
     * @see #translations()
     */
    Set<List<O>> transduce(List<I> input);
}