#lang racket
(define (save-pict picture filename)
  (send (pict->bitmap picture) save-file filename 'png))
(randoms 1231 100)
(save-pict (first (mondrian 50 50 '(0 0.1))) "test1.png")
(save-pict (first (mondrian 50 51 '(1 0.5 0 1 0 0))) "test2.png")
(save-pict (first (mondrian 51 50 '(1 0.5 0 0.2 0 1))) "test3.png")
(save-pict (first (mondrian 51 51 '(1 0.5 0.5 0 0.1 0 1 0 1 0 1))) "test4.png")
(save-pict (first (mondrian 200 300 (randoms 2134221 1000))) "mondrian.png")