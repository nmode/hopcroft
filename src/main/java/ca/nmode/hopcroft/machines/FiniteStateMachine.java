package ca.nmode.hopcroft.machines;

/* The base interface from which all finite-state machines are derived. The generic parameter 'VA' is used to specify
   the machine's variant, so that subtypes may not be both deterministic and nondeterministic. */
interface FiniteStateMachine<S, I, K, V, C, VA> {}