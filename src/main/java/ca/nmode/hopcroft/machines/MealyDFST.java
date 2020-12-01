package ca.nmode.hopcroft.machines;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A deterministic finite-state mealy transducer. It is a {@link DeterministicFSM deterministic finite-state machine}
 * which {@link #translations() translates} each undergone {@link DeterministicFSM#transitions() transition} in the
 * {@link DeterministicFSM#compute(List) computation} of an input to an {@link #outputElements() output element}. The
 * process which yields the corresponding output sequence is known as its {@link #transduce(List) transduction} on the
 * input.
 * 
 * @param <S> the type of this deterministic finite-state mealy transducer's states
 * @param <I> the type of this deterministic finite-state mealy transducer's input elements
 * @param <K> the type of the keys of this deterministic finite-state mealy transducer's transition map
 * @param <V> the type of the values of this deterministic finite-state mealy transducer's transition map
 * @param <C> the type of this deterministic finite-state mealy transducer's computations
 * @param <O> the type of this deterministic finite-state mealy transducer's output elements
 *
 * @author Naeem Model
 */
public interface MealyDFST<S, I, K, V, C, O> extends DeterministicFSM<S, I, K, V, C> {
    /**
     * Returns this deterministic finite-state mealy transducer's unmodifiable set of output elements. The returned set
     * neither is nor contains {@code null}, and attempts to modify it result in an
     * {@link UnsupportedOperationException}.
     * 
     * @return this deterministic finite-state mealy transducer's unmodifiable set of output elements
     */
    Set<O> outputElements();

    /**
     * Returns this deterministic finite-state mealy transducer's unmodifiable translation map. The returned map's keys
     * are all and only the keys in this machine's transition map, with values only from its set of output elements, and
     * attempts to modify it result in an {@link UnsupportedOperationException}.
     * 
     * @return this deterministic finite-state mealy transducer's unmodifiable translation map
     * 
     * @see DeterministicFSM#transitions()
     * @see #outputElements()
     */
    Map<K, O> translations();

    /**
     * Returns this deterministic finite-state mealy transducer's {@link MealyDFST transduction} on the specified input.
     * The returned list is not {@code null}.
     * 
     * @param input the sequence of elements to compute this deterministic finite-state mealy transducer on
     *
     * @throws NullPointerException if {@code input} is {@code null}
     * 
     * @return this deterministic finite-state mealy transducer's transduction on the specified input
     *
     * @see DeterministicFSM#inputElements()
     * @see DeterministicFSM#compute(List)
     * @see #translations()
     */
    List<O> transduce(List<I> input);
}