package ca.nmode.hopcroft.machines;

import ca.nmode.hopcroft.states.State;

/* The base interface from which all finite-state machines are derived. The generic parameter 'VA' is used to specify
   the machine's variant, so that subtypes may not be both deterministic and nondeterministic. */
interface FiniteStateMachine<S extends State, I, K, V, C, VA> {}