inverseCycle([], []).
inverseCycle([H], [H]).
inverseCycle([H | [T]], [H | [T]]).
inverseCycle([H | T], Inverted) :-
    reverse(T, Reversed),
    Inverted = [H | Reversed].
                     
% inverse(...) :- ....
