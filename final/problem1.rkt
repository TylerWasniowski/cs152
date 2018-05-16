#lang racket

(define (invertCycle cycle)
  (cons (first cycle) (foldl (λ (x inverted) (cons x inverted)) '() (rest cycle))))

(define (invert permutation)
  (foldr (λ (x inverted) (cons (invertCycle x) inverted)) '() permutation))
        
