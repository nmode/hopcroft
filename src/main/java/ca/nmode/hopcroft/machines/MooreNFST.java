package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A nondeterministic finite-state moore transducer. It is a {@link NondeterministicFSM nondeterministic finite-state
 * machine} which {@link #translations() translates} each visited state in every branch the
 * {@link NondeterministicFSM#compute(List) computation} of an input to an {@link #outputElements() output element}. The
 * process which yields the corresponding output sequence for each branch is known as its {@link #transduce(List)
 * transduction} on the input.
 * 
 * @param <S> the type of this nondeterministic finite-state moore transducer's states
 * @param <I> the type of this nondeterministic finite-state moore transducer's input elements
 * @param <K> the type of the keys of this nondeterministic finite-state moore transducer's transition map
 * @param <V> the type of the values of this nondeterministic finite-state moore transducer's transition map
 * @param <C> the type of this nondeterministic finite-state moore transducer's computations
 * @param <O> the type of this nondeterministic finite-state moore transducer's output elements
 *
 * @author Naeem Model
 */
public interface MooreNFST<S, I, K, V, C, O> extends NondeterministicFSM<S, I, K, V, C> {
    /**
     * Returns this nondeterministic finite-state moore transducer's unmodifiable set of output elements. The returned
     * set neither is nor contains {@code null}, is non-empty, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state moore transducer's unmodifiable set of output elements
     */
    Set<O> outputElements();

    /**
     * Returns this nondeterministic finite-state moore transducer's unmodifiable translation map. The returned map's
     * keys are all and only the states in this machine's set of states, with values only from its set of output
     * elements, and attempts to modify it result in an {@link UnsupportedOperationException}.
     * 
     * @return this nondeterministic finite-state moore transducer's unmodifiable translation map
     * 
     * @see NondeterministicFSM#states()
     * @see #outputElements()
     */
    Map<S, O> translations();

    /**
     * Returns this nondeterministic finite-state moore transducer's {@link MooreNFST transduction} on the specified
     * input. The returned set neither is nor contains {@code null}.
     * 
     * @param input the sequence of elements to compute this nondeterministic finite-state moore transducer on
     *
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return this nondeterministic finite-state moore transducer's transduction on the specified input
     *
     * @see NondeterministicFSM#inputElements()
     * @see NondeterministicFSM#compute(List)
     * @see #translations()
     */
    Set<List<O>> transduce(List<I> input);
}