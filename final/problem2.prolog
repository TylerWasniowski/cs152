inverseCycle([], []).
inverseCycle([H], [H]).
inverseCycle([H | T], Inverted) :-
    reverse(T, Reversed),
    Inverted = [H | Reversed].

inverse([], []).
inverse([H | T], InvertedPerm) :-
    inverseCycle(H, Inverted),
    inverse(T, X),
    InvertedPerm = [Inverted | X].

