% applyCycle(Cycle, A, B)
% The given cycle sends A to B
applyCycle(Cycle, A, A) :- delete(Cycle, A, Cycle).
applyCycle(Cycle, A, B) :- append(_, [A | [B | _]], Cycle).
applyCycle(Cycle, A, B) :- append([B | _], [A], Cycle).
    
% applyPerm(Cycles, A, B)
% The given permutation (list of cycles) sends A to B
applyPerm([], A, A).
applyPerm([Cycle | T], A, B) :-
    applyCycle(Cycle, A, X),
    applyPerm(T, X, B).

% orbit(Perm, A, Orbit)
% Repeatedly applying Perm to A sends A to all elements in Orbit.
orbit(Perm, A, Orbit) :- orbitHelper(Perm, A, [], Orbit).

orbitHelper(_, A, Acc, Acc) :- member(A, Acc).
orbitHelper(Perm, A, Acc, Orbit) :-
    delete(Acc, A, Acc),
    applyPerm(Perm, A, B),
    append(Acc, [A], Acc2),
    orbitHelper(Perm, B, Acc2, Orbit).


% cycles(Cycles, Normalized)
% Given a sequence of cycles, computes their effect
% as a normalized sequence of cycles.
cycles(Cycles, N_Sorted) :-
    flatten(Cycles, Flattened),
    sort(Flattened, Sorted),
    cyclesHelper(Cycles, Sorted, Normalized),
    sort(Normalized, N_Sorted).

cyclesHelper(_, [], []).
cyclesHelper(Cycles, [Elements_H | Elements_T], [Sorted | Normalized_T]) :-
    orbit(Cycles, Elements_H, Normalized_H),
    sort(Normalized_H, Sorted),
    cyclesHelper(Cycles, Elements_T, Normalized_T).
    

rotation(r0, [1, 4, 2], [d, l, f]).
rotation(r1, [0, 3, 5], [d, f, r]).
rotation(r2, [0, 6, 3], [f, l, u]).
rotation(r3, [1, 2, 7], [f, u, r]).
rotation(r4, [0, 6, 5], [b, d, l]).
rotation(r5, [1, 4, 7], [b, r, d]).
rotation(r6, [2, 7, 4], [b, l, u]).
rotation(r7, [3, 5, 6], [b, u, r]).

name(R, N) :- rotation(N, R, _).
name(R, N) :- rotation(N, _, R).

% addFront1(E, Ts, Result): Puts E in front of all elements of Ts
addFront1(_, [], []).
addFront1(E, [T|Ts], [[E|T]|Rs]) :- addFront1(E, Ts, Rs).

% addFront2(S, Ts, Result): Puts all elements of S in front of all
% elements of Ts
addFront2([], _, []).
addFront2([H|T], Ts, Us) :- addFront1(H, Ts, R1), addFront2(T, Ts, R2), append(R1, R2, Us).

picks(_, 0, [[]]).
picks(S, N, R) :- N > 0, N1 is N - 1, picks(S, N1, R1), addFront2(S, R1, R).

find(Perms, Target, Pick, N) :- picks(Perms, N, Picks), member(Pick, Picks), cycles(Pick, Target).