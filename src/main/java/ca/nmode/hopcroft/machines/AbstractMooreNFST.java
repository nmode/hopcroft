package ca.nmode.hopcroft.machines;

import java.util.Map;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/**
 * A skeletal implementation of a {@link MooreNFST nondeterministic finite-state moore transducer}. Extending classes
 * need not validate the set of {@link #states() states}, set of {@link #inputElements() input elements},
 * {@link #startState() start state}, {@link #outputElements() output elements} and {@link #translations() translation
 * map}. It is ensured that the {@link #transitions() transition map} neither is nor contains keys or values that are
 * {@code null}.
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
public abstract class AbstractMooreNFST<S extends State, I, K, V, C, O> extends AbstractMooreFST<S, I, K, V, O>
        implements MooreNFST<S, I, K, V, C, O> {
    /**
     * Constructs a new nondeterministic finite-state moore transducer given a set of states, set of input elements,
     * transition map, start state, set of output elements and translation map.
     * 
     * @param states         the set of states of the new nondeterministic finite-state moore transducer
     * @param inputElements  the set of input elements of the new nondeterministic finite-state moore transducer
     * @param transitions    the transition map of the new nondeterministic finite-state moore transducer
     * @param startState     the start state of the new nondeterministic finite-state moore transducer
     * @param outputElements the set of output elements of the new nondeterministic finite-state moore transducer
     * @param translations   the translation map of the new nondeterministic finite-state moore transducer
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements}, {@code transitions},
     *                                  {@code outputElements} or {@code translations} is {@code null}; {@code states},
     *                                  {@code inputElements} or {@code outputElements} contains {@code null}; or
     *                                  {@code transitions} contains {@code null} keys or values
     * @throws IllegalArgumentException if {@code states} is empty, {@code startState} is not in {@code states},
     *                                  {@code translations} contains values that are not in {@code outputElements},
     *                                  {@code outputElements} is empty, or {@code translations}' key set is not equal
     *                                  to {@code states}
     */
    public AbstractMooreNFST(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState,
            Set<O> outputElements, Map<S, O> translations) {
        super(states, inputElements, transitions, startState, outputElements, translations);
    }

    @Override
    public final Set<S> states() {
        return states;
    }

    @Override
    public final Set<I> inputElements() {
        return inputElements;
    }

    @Override
    public final Map<K, V> transitions() {
        return transitions;
    }

    @Override
    public final S startState() {
        return startState;
    }

    @Override
    public final Set<O> outputElements() {
        return outputElements;
    }

    @Override
    public final Map<S, O> translations() {
        return translations;
    }
}