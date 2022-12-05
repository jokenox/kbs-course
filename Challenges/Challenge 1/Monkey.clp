(assert (monkey near-door))
(assert (monkey farfrom-window))
(assert (monkey hands-free))

(defrule r1 (monkey near-door) (monkey farfrom-window) => (printout  t "monkey goes to the box" crlf) (assert (monkey near-box)) (retract 1) (retract 2))

(defrule r2 (monkey near-box) (monkey hands-free) => (printout t "mokey took the box" crlf) (retract 3) (assert (monkey hands-busy)) (assert (monkey grabbed-box)))

(defrule r3 (monkey near-box) (monkey hands-busy) (monkey grabbed-box) => (printout t "monkey goes below the banana" crlf) (assert (monkey below-banana)))

(defrule r4 (monkey below-banana) (monkey hands-busy) => (printout t "monkey climbs the box" crlf) (assert (monkey on-box)) (assert (monkey hands-free)) (assert (monkey near-banana)) (retract 4) (retract 5) (retract 6) (retract 7))

(defrule r5 (monkey near-banana) (monkey hands-free) => (printout t "monkey takes the banana" crlf) (assert (monkey hands-busy)) (assert (monkey grabbed-banana)) (retract 9))

(defrule r6 (monkey grabbed-banana) => (printout t "monkey eats the banana" crlf) (retract 10))