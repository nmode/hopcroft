package ca.nmode.hopcroft.machines;

import java.util.Map;
import java.util.Set;

import ca.nmode.hopcroft.states.State;

/**
 * A skeletal implementation of a {@link MealyDFST deterministic finite-state mealy transducer}. Extending classes need
 * not validate the set of {@link #states() states}, set of {@link #inputElements() input elements},
 * {@link #startState() start state}, {@link #outputElements() output elements} and {@link #translations() translation
 * map}. It is ensured that the {@link #transitions() transition map} neither is nor contains keys or values that are
 * {@code null}.
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
public abstract class AbstractMealyDFST<S extends State, I, K, V, C, O> extends AbstractMealyFST<S, I, K, V, O>
        implements MealyDFST<S, I, K, V, C, O> {
    /**
     * Constructs a new deterministic finite-state mealy transducer given a set of states, set of input elements,
     * transition map, start state, set of output elements and translation map.
     * 
     * @param states         the set of states of the new deterministic finite-state mealy transducer
     * @param inputElements  the set of input elements of the new deterministic finite-state mealy transducer
     * @param transitions    the transition map of the new deterministic finite-state mealy transducer
     * @param startState     the start state of the new deterministic finite-state mealy transducer
     * @param outputElements the set of output elements of the new deterministic finite-state mealy transducer
     * @param translations   the translation map of the new deterministic finite-state mealy transducer
     * 
     * @throws NullPointerException     if {@code states}, {@code inputElements}, {@code transitions},
     *                                  {@code outputElements} or {@code translations} is {@code null}; {@code states},
     *                                  {@code inputElements} or {@code outputElements} contains {@code null}; or
     *                                  {@code transitions} contains {@code null} keys or values
     * @throws IllegalArgumentException if {@code states} is empty, {@code startState} is not in {@code states},
     *                                  {@code translations} contains values that are not in {@code outputElements}, or
     *                                  {@code translations}' key set is not equal to {@code transitions}' key set
     */
    public AbstractMealyDFST(Set<S> states, Set<I> inputElements, Map<K, V> transitions, S startState,
            Set<O> outputElements, Map<K, O> translations) {
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
    public final Map<K, O> translations() {
        return translations;
    }
}