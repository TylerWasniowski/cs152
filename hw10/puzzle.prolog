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

doFlips([], Row, _, Result) :- sort(Row, Result).
doFlips([H|T], Row, Columns, Result) :-
        flipped(H, Columns, X),
        ord_symDiff(Row, X, Result1),
        doFlips(T, Result1, Columns, Result).
