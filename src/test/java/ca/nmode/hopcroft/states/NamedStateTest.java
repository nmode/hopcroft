package ca.nmode.hopcroft.states;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NamedStateTest {
    private static NamedState validNamedState;
    private static String validName;
    private static String otherValidName;

    @BeforeAll
    static void setUpBeforeClass() {
        validName = UUID.randomUUID().toString();
        validNamedState = new NamedState(validName);
        otherValidName = UUID.randomUUID().toString();
        while (validName.equals(otherValidName))
            otherValidName = UUID.randomUUID().toString();
    }

    @Test
    void constructor_nullName_throws_NPE() {
        assertThrows(NullPointerException.class, () -> new NamedState(null));
    }

    @Test
    void constructor_blankName_throws_IAE() {
        assertThrows(IllegalArgumentException.class, () -> new NamedState("  \n \r\t  \n \t\t\n    \r "));
    }

    @Test
    void equalsTest() {
        assertEquals(validNamedState, validNamedState);
        assertEquals(validNamedState, new NamedState(validName));
        assertNotEquals(validNamedState, null);
        assertNotEquals(validNamedState, new NamedState(otherValidName));
    }

    @Test
    void hashCodeTest() {
        assertEquals(validNamedState.hashCode(), validName.hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals(validNamedState.toString(), validName);
        assertNotEquals(validNamedState.toString(), otherValidName);
    }
}