import pygame

offset = 6

node = 'O'
cross = 'X'

board = [[' ',' ',' '],
        [' ',' ',' '],
        [' ',' ',' ']]

turn = cross

screen_w = 300
screen_h = 300

line_width = 4

running = True

pygame.init()

screen = pygame.display.set_mode((screen_w, screen_h))

def best_move():
    # the computer or the ai plays node
    # ai is the maximizing player
    # best score set to the worst possible outcome for the ai
    best_score = float('-inf')
    best_move = None
    for i in range(0,3):
        for j in range(0,3):
            if board[i][j] == ' ':
                # ai plays node in the empty cell
                board[i][j] = node
                score = minimax(False)
                # setting the cell back to its previous state
                board[i][j] = ' '
                if score > best_score:
                    best_score = score
                    best_move = [i,j]
    return best_move

def minimax(maximizing):
    if check_winner(node):
        # best possible outcome for the ai
        return float('inf')
    elif check_winner(cross):
        # worst possible outcome for the ai
        return float('-inf')
    elif is_draw():
        return 0
    if maximizing:
        best_score = float('-inf')
        for i in range(0,3):
            for j in range(0,3):
                if board[i][j] == ' ':
                    board[i][j] = node
                    # recursive call to the function
                    score = minimax(False)
                    board[i][j] = ' '
                    best_score = max(score,best_score)
        return best_score
    else:
        best_score = float('inf')
        for i in range(0,3):
            for j in range(0,3):
                if board[i][j] == ' ':
                    board[i][j] = cross
                    # recursive call to the function
                    score = minimax(True)
                    board[i][j] = ' '
                    # player or human is the minimizing player
                    best_score = min(score,best_score)
        return best_score


def check_winner(player):
    for i in range(0,3):
        if board[i][0] == player and board[i][1] == player and board[i][2] == player:
            return True
    for i in range(0,3):
        if board[0][i] == player and board[1][i] == player and board[2][i] == player:
            return True
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
                    pygame.draw.circle(screen,(0,0,0),(x,y),40,line_width)
                else:
                    pygame.draw.line(screen,(0,0,0),(x - screen_w//3//2+offset,y - screen_h//3//2+offset),(x + screen_w//3//2-offset,y + screen_h//3//2-offset),line_width)
                    pygame.draw.line(screen,(0,0,0),(x + screen_w//3//2-offset,y - screen_h//3//2+offset),(x - screen_w//3//2+offset,y + screen_h//3//2-offset),line_width)


def draw_grid():
    for i in range(0,3):
        for j in range(0,3):
            x = i * screen_w//3
            y = j * screen_h//3
            pygame.draw.line(screen,(0,0,0),(0,y+screen_h//3),(screen_w,y+screen_h//3),line_width)
            pygame.draw.line(screen,(0,0,0),(x+screen_w//3,0),(x+screen_w//3,screen_h),line_width)


while running:
    for event in pygame.event.get():
        running = not event.type == pygame.QUIT

        if event.type == pygame.MOUSEBUTTONDOWN and not is_draw() and not check_winner(node) and not check_winner(cross):
            pos = pygame.mouse.get_pos()
            x = pos[0] // (screen_w//3)
            y = pos[1] // (screen_h//3)
            if board[x][y] == ' ':
                board[x][y] = turn
                turn = cross if turn == node else node
                if check_winner(turn):
                    print("Winner is " + turn)
                elif is_draw():
                    print("Draw")
                else:
                    # as the game is not over, the computer will play
                    best = best_move()
                    board[best[0]][best[1]] = node
                    turn = cross if turn == node else node

    screen.fill((255,255,255))

    draw_grid()

    draw_board()

    pygame.display.update()