move(From, To) :- append(To, [_|_], From), append(To, To, To2), append(From, _, To2).

win(X) :- move(X, Y), not(win(Y)).