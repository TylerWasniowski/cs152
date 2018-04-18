move(X,Y) :- X > Y, X =< 2 * Y.

win(X) :- X=:=2.
win(X) :- move(X,Y), not(win(Y)).