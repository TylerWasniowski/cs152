#lang racket
(require pict)
(define a 1664525)
(define b 1013904223)
(define m 4294967296)
(define (randoms seed length)
  (letrec (
           (helper
            (λ (seed length) (cond
                               ((= length 0) '())
                               (else
                                (list* (/ seed m) (helper (modulo (+ (* a seed) b) m) (- length 1)))
                                )
                               )))
           ) (helper seed length)))
(define (mondrian width height rands)
  (letrec (
           (draw-rect (λ (w h color) (cc-superimpose (colorize (filled-rectangle w h) color) (rectangle w h))))
           (wide-enough (λ (w r) (and (> w 50) (> (/ w width) (* 0.5 r)))))
           (tall-enough (λ (h r) (and (> h 50) (> (/ h height) (* 0.5 r)))))
           (big-enough (λ (w h r) (and (wide-enough w r) (tall-enough h r))))
           (split-modifier (λ (size r) (+ (/ 1 3) (/ r 3))))
           (left-width (λ (w modifier) (* w modifier)))
           (right-width (λ (w modifier) (* w (- 1 modifier))))
           (top-height (λ (h modifier) (* h modifier)))
           (bottom-height (λ (h modifier) (* h (- 1 modifier))))
           (color (λ (r) (cond
                           ((< r 0.0833) "Red")
                           ((< r 0.1667) "Blue")
                           ((< r 0.25) "Yellow")
                           (else "White")
                           )))
           (split-vert (λ (w h rands)  (let*
                                           (
                                            (left (mondrian-helper (left-width w (split-modifier w (first rands))) h (rest rands)))
                                            (right (mondrian-helper (right-width w (split-modifier w (first rands))) h (second left)))
                                            )
                                         (list (hc-append (first left) (first right)) (second right))
                                         )))
           (split-horz (λ (w h rands) (let*
                                          (
                                           (top (mondrian-helper w (top-height h (split-modifier h (first rands))) (rest rands)))
                                           (bottom (mondrian-helper w (bottom-height h (split-modifier h (first rands))) (second top)))
                                           )
                                        (list (vc-append (first top) (first bottom)) (second bottom))
                                        )))
           (split-both (λ (w h rands) (let*
                                          (
                                           (top-left (mondrian-helper (left-width w (split-modifier w (first rands))) (top-height h (split-modifier h (second rands))) (rest (rest rands))))
                                           (top-right (mondrian-helper (right-width w (split-modifier w (first rands))) (top-height h (split-modifier h (second rands))) (second top-left)))
                                           (bottom-left (mondrian-helper (left-width w (split-modifier w (first rands))) (bottom-height h (split-modifier h (second rands))) (second top-right)))
                                           (bottom-right (mondrian-helper (right-width w (split-modifier w (first rands))) (bottom-height h (split-modifier h (second rands))) (second bottom-left)))
                                           )
                                        (list (vc-append (hc-append (first top-left) (first top-right)) (hc-append (first bottom-left) (first bottom-right))) (second bottom-right))
                                        )))
                                       
           (mondrian-helper (λ (width height rands)
                              (let
                                  ((rand (first rands)))
                                (cond
                                  ((big-enough width height rand) (split-both width height (rest rands)))
                                  ((wide-enough width rand) (split-vert width height (rest rands)))
                                  ((tall-enough height rand) (split-horz width height (rest rands)))
                                  (else (list (draw-rect width height (color (second rands))) (rest (rest rands))))       
                                  )
                                )
                              ))
                           
           ) (mondrian-helper width height rands)))
