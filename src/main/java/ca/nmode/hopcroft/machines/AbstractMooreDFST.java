package ca.nmode.hopcroft.machines;

import java.util.Map;
import java.util.Set;

/**
 * A skeletal implementation of a {@link MooreDFST deterministic finite-state moore transducer}. Extending classes need
 * not validate the set of {@link #states() states}, set of {@link #inputElements() input elements},
 * {@link #startState() start state}, {@link #outputElements() output elements} and {@link #translations() translation
 * map}. It is ensured that the {@link #transitions() transition map} neither is nor contains keys or values that are
 * {@code null}.
 * 
 * @param <S> the type of this deterministic finite-state moore transducer's states
 * @param <I> the type of this deterministic finite-state moore transducer's input elements
 * @param <K> the type of the keys of this deterministic finite-state moore transducer's transition map
 * @param <V> the type of the values of this deterministic finite-state moore transducer's transition map
 * @param <C> the type of this deterministic finite-state moore transducer's computations
 * @param <O> the type of this deterministic finite-state moore transducer's output elements
 *
 * @author Naeem Model
 */
public abstract class AbstractMooreDFST<S, I, K, V, C, O> extends AbstractFST<S, I, K, V, O, S>
        implements MooreDFST<S, I, K, V, C, O> {
    /**
     * Constructs a new deterministic finite-state moore transducer given a set of states, set of input elements,
     * transition map, start state, set of output elements and translation map.
     * 
     * @param states         the set of states of the new deterministic finite-state moore transducer
     * @param inputElements  the set of input elements of the new deterministic finite-state moore transducer
     * @param transitions    the transition map of the new deterministic finite-state moore transducer
     * @param startState     the start state of the new deterministic finite-state moore transducer
     * @param outputElements the set of output elements of the new deterministic finite-state moore transducer
     * @param translations   the translation map of the new deterministic finite-state moore transducer
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements}, {@code transitions},
     *                                  {@code outputElements} or {@code translations} is {@code null}; {@code states},
     *                                  {@code inputElements} or {@code outputElements} contains {@code null}; or
     *                                  {@code transitions} contains {@code null} keys or values
     * @throws IllegalArgumentException if {@code states} is empty, {@code startState} is not in {@code states},
     *                                  {@code translations} contains values that are not in {@code outputElements}, or
     *                                  {@code translations}' key set is not equal to {@code states}
     */
    public AbstractMooreDFST(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState,
            Set<O> outputElements, Map<S, O> translations) {
        super(states, inputElements, transitions, startState, outputElements, translations);

        // Ensure the translation map's key set is equal to the set of states.
        if (!translations.keySet().equals(states))
            throw new IllegalArgumentException("Cannot construct a deterministic finite-state moore transducer whose "
                    + "translation map's key set is not equal to its set of states.");
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