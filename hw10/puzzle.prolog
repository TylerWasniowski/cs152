sublist(X, X).
sublist(S, [_|T]) :- sublist(S, T).
sublist([S_H|S_T], [L_H|L_T]) :-
        S_H = L_H,
        sublist(S_T, L_T),
        S_T \= L_T.

% flipped(Click, Columns, Cells)
% Cells is the list of cells that are flipped by a click at index
% Click in a row of the given number of columns.

flipped(Click, Columns, [Left, Click, Right]) :- Click > 1, Click < Columns, Left is Click - 1, Right is Click + 1.
flipped(Click, Columns, [Left, Click]) :- Click > 1, Click = Columns, Left is Click - 1.
flipped(Click, Columns, [Click, Right]) :- Click = 1, Click < Columns, Right is Click + 1.
flipped(Click, Columns, [Click]) :- Columns = 1.


% doFlips(Cells, Row, Columns, Result)
% Result is a list of red positions resulting from flipping all
% Cells (list of positions) in a Row (list of red positions)
% of the given number of columns.

doFlips([], Row, _, Row).
doFlips([H|T], Row, Columns, Result) :-
        flipped(H, Columns, X),
        ord_symdiff(Row, X, Result1),
        doFlips(T, Result1, Columns, Result).

% allFlips(Flips, Row, Rows, Columns, AllFlips)
% yields in AllFlips a list of lists for flips for each row so that the
% initial set of Flips in the initial Row turns the rectangle
% of the given rows and columns all red.

allFlips([], _, 0, _, []).
allFlips(Flips, Row, Rows, Columns, [Flips | MoreFlips]) :-
                Rows > 0,
                doFlips(Flips, Row, Columns, X),
                numlist(1, Columns, All),
                ord_symdiff(X, All, FirstRowBlues),
                Rows1 is Rows - 1,
                allFlips(FirstRowBlues, Flips, Rows1, Columns, MoreFlips).

        solution(Rows, Columns, Solution) :-
                numlist(1, Columns, L),
                sublist(Solution, L),
                allFlips(Solution, [], Rows, Columns, _).

