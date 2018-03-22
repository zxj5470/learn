import turtle

g = turtle.Turtle()
g.color('red', 'yellow')
g.begin_fill()
while True:
    g.forward(200)
    g.left(170)
    if abs(g.pos()) < 1:
        break
g.end_fill()
turtle.done()
