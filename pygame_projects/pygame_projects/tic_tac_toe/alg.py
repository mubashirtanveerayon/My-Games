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

def best_move():
    if turn == cross:
        for i in range(3):
            for j in range(3):
                if board[i][j] == ' ':
                    board[i][j] = cross
                    if check_winner(cross):
                        board[i][j] = ' '
                        return [i,j]
                    board[i][j] = ' '
        for i in range(3):
            for j in range(3):
                if board[i][j] == ' ':
                    board[i][j] = node
                    if check_winner(node):
                        board[i][j] = ' '
                        return [i,j]
                    board[i][j] = ' '
    else:
        for i in range(3):
            for j in range(3):
                if board[i][j] == ' ':
                    board[i][j] = node
                    if check_winner(node):
                        board[i][j] = ' '
                        return [i,j]
                    board[i][j] = ' '
        for i in range(3):
            for j in range(3):
                if board[i][j] == ' ':
                    board[i][j] = cross
                    if check_winner(cross):
                        board[i][j] = ' '
                        return [i,j]
                    board[i][j] = ' '
    
    available_corners = corners()
    available_squares = available_moves()

    if turn == cross:
        if len(available_corners)!=0:
            return available_corners[0]
        else:
            if board[1][1] == ' ':
                return [1,1]
            else:
                return available_squares[0]
    else:
        if board[1][1] == ' ':
            return [1,1]
        elif board[1][1] != node:
            if len(available_corners)!=0:
                return available_corners[0]
            else:
                return available_squares[0]
        else:
            if board[0][1] != cross and board[2][1] != cross:
                if board[0][1] == ' ':
                    return [0,1]
                elif board[2][1] == ' ':
                    return [2,1]
            elif board[1][0] != cross and board[1][2] != cross:
                if board[1][0] == ' ':
                    return [1,0]
                elif board[1][2] == ' ':
                    return [1,2]
            else:
                plus = plus_sign()
                if len(plus)!=0:
                    return plus[0]
                else:
                    return available_corners[0]

def plus_sign():
    squares = []
    if board[0][1] == ' ':
        squares.append([0,1])
    if board[1][0] == ' ':
        squares.append([1,0])
    if board[1][2] == ' ':
        squares.append([1,2])
    if board[2][1] == ' ':
        squares.append([2,1])
    return squares


def corners():
    corners = []
    if board[0][0] == ' ':
        corners.append([0,0])
    if board[0][2] == ' ':
        corners.append([0,2])
    if board[2][0] == ' ':
        corners.append([2,0])
    if board[2][2] == ' ':
        corners.append([2,2])
    return corners

def available_moves():
    moves = []
    for i in range(0,3):
        for j in range(0,3):
            if board[i][j] == ' ':
                moves.append([i,j])
    return moves


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
                else:
                    x,y = best_move()
                    board[x][y] = turn
                    turn = cross if turn == node else node
                

    # setting background color to white
    screen.fill((255,255,255))

    draw_grid()

    draw_board()

    pygame.display.update()
