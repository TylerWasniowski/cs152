last(E, [E]).
last(E, [_|T]) :- last(E, T).

notlast(S, L) :- last(E, L), append(S, [E], L).

subseq(S, L) :- append(S, _, L).
subseq(S, [_|T]) :- subseq(S, T), S \= [].

sublist(X, X).
sublist(S, [_|T]) :- sublist(S, T).
sublist([S_H|S_T], [L_H|L_T]) :- S_H = L_H, sublist(S_T, L_T), S_T \= L_T.

without([H|T], E, S) :- H = E, T = S.
without([L_H|L_T], E, [S_H|S_T]) :- L_H = S_H, without(L_T, E, S_T).

permutation(L, L).
permutation(L, [M_H|M_T]) :- without(L, M_H, X), permutation(X, M_T), [M_H|M_T] \= L.

split([], [], []).
split([L_H|L_T], [P_H|P_T], Q) :- P_H = L_H, split(L_T, P_T, Q).
split([L_H|L_T], P, [Q_H|Q_T]) :- Q_H = L_H, split(L_T, P, Q_T).