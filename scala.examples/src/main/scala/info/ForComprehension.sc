
// list all combinations of numbers x and y where x is drawn from
// 1 to M and y is drawn from 1 to N
for (x <- 1 to 5; y <- 'a' to 'c')
  yield (x, y)

for {
  x <- 1 to 5;
  y <- 'a' to 'c'
}
  yield (x, y)

(1 to 5)
  .flatMap(e =>
    ('a' to 'c').map(e2 => (e, e2)))

