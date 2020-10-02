package ca.nmode.hopcroft.states;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MetaStateTest {
    private static MetaState<NamedState> validMetaState;
    private static Set<NamedState> validSubstates;
    private static Set<NamedState> otherValidSubstates;

    @BeforeAll
    static void setUpBeforeClass() {
        validSubstates = new HashSet<>();
        validSubstates.add(new NamedState("q0"));
        validSubstates.add(new NamedState("q1"));
        validSubstates.add(new NamedState("q2"));
        validMetaState = new MetaState<>(validSubstates);
        otherValidSubstates = new HashSet<>(validSubstates);
        otherValidSubstates.add(new NamedState("q3"));
    }

    @Test
    void constructor_nullSet_throws_NPE() {
        assertThrows(NullPointerException.class, () -> new MetaState<>(null));
    }

    @Test
    void constructor_nullSubstate_throws_NPE() {
        validSubstates.add(null);
        assertThrows(NullPointerException.class, () -> new MetaState<>(validSubstates));
        validSubstates.remove(null);
    }

    @Test
    void substates_modify_throws_UOE() {
        assertThrows(UnsupportedOperationException.class, () -> validMetaState.substates().add(null));
    }

    @Test
    void substatesTest() {
        assertEquals(validSubstates, validMetaState.substates());
    }

    @Test
    void equalsTest() {
        assertEquals(validMetaState, validMetaState);
        assertEquals(validMetaState, new MetaState<>(validSubstates));
        assertNotEquals(validMetaState, null);
        assertNotEquals(validMetaState, new MetaState<>(otherValidSubstates));
    }

    @Test
    void hashCodeTest() {
        assertEquals(validMetaState.hashCode(), validSubstates.hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals(validMetaState.toString(), validSubstates.toString());
        assertNotEquals(validMetaState.toString(), otherValidSubstates.toString());
    }
}