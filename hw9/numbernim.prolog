move(X, Y) :- X0 is round(X / 2), X1 is X - 1, between(X0, X1, Y).

win(X) :- X=:=2.
win(X) :- move(X,Y), not(win(Y)).