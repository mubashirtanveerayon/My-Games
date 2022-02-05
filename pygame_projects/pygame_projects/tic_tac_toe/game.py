import pygame

offset = 6

node = 'O'
cross = 'X'

# initializing the board with empty spaces
board = [[' ',' ',' '],
        [' ',' ',' '],
        [' ',' ',' ']]

# X plays first
turn = cross

screen_w = 300
screen_h = 300

line_width = 4

running = True

pygame.init()

screen = pygame.display.set_mode((screen_w, screen_h))

def check_winner(player):
    # horizontal
    for i in range(0,3):
        if board[i][0] == player and board[i][1] == player and board[i][2] == player:
            return True
    # vartical
    for i in range(0,3):
        if board[0][i] == player and board[1][i] == player and board[2][i] == player:
            return True
    # diagonal
    if board[0][0] == player and board[1][1] == player and board[2][2] == player:
        return True
    if board[0][2] == player and board[1][1] == player and board[2][0] == player:
        return True
    return False

def is_draw():
    for i in range(0,3):
        for j in range(0,3):
            if board[i][j] == ' ':
                return False
    return True

def draw_board():
    for i in range(0,3):
        for j in range(0,3):
            if board[i][j] != ' ':
                x = i * screen_w//3 + screen_w//3//2
                y = j * screen_h//3 + screen_h//3//2
                if board[i][j] == node:
                    # drawing 'O' as an actual circle
                    pygame.draw.circle(screen,(0,0,0),(x,y),40,line_width)
                else:
                    # drawing 'X' as 2 diagonally intersecting lines
                    pygame.draw.line(screen,(0,0,0),(x - screen_w//3//2+offset,y - screen_h//3//2+offset),(x + screen_w//3//2-offset,y + screen_h//3//2-offset),line_width)
                    pygame.draw.line(screen,(0,0,0),(x + screen_w//3//2-offset,y - screen_h//3//2+offset),(x - screen_w//3//2+offset,y + screen_h//3//2-offset),line_width)


def draw_grid():
    for i in range(0,3):
        for j in range(0,3):
            x = i * screen_w//3
            y = j * screen_h//3
            pygame.draw.line(screen,(0,0,0),(0,y+screen_h//3),(screen_w,y+screen_h//3),line_width)
            pygame.draw.line(screen,(0,0,0),(x+screen_w//3,0),(x+screen_w//3,screen_h),line_width)

# game loop
while running:
    for event in pygame.event.get():
        running = not event.type == pygame.QUIT

        if event.type == pygame.MOUSEBUTTONDOWN and not is_draw() and not check_winner(node) and not check_winner(cross):
            pos = pygame.mouse.get_pos()
            # finding the row and column for the mouse clicked position
            x = pos[0] // (screen_w//3)
            y = pos[1] // (screen_h//3)
            if board[x][y] == ' ':
                board[x][y] = turn
                # turn is changed to the other player
                turn = cross if turn == node else node
                if check_winner(turn):
                    print("Winner is " + turn)
                elif is_draw():
                    print("Draw")

    # setting background color to white
    screen.fill((255,255,255))

    draw_grid()

    draw_board()

    pygame.display.update()
