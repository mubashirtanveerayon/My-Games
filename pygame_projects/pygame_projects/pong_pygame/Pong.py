import pygame

move = [0,0,0,0]

screen_w = 650
screen_h = 380

ball_r = 15
ball_x = screen_w//2
ball_y = screen_h//2

vx = 5
vy = 5

paddle_w = 10
paddle_h = 100

#paddle
movement_speed = 10

player_left = [0,screen_h//2-paddle_h//2]
player_right = [screen_w-paddle_w,screen_h//2-paddle_h//2]

pygame.init()

screen = pygame.display.set_mode((screen_w,screen_h))

running = True

def draw_paddle():
    left = pygame.Rect((player_left[0],int(player_left[1]),paddle_w,paddle_h))
    right = pygame.Rect((player_right[0],int(player_right[1]),paddle_w,paddle_h))
    pygame.draw.rect(screen,(255,255,255),left)
    pygame.draw.rect(screen,(255,255,255),right)
    
def draw_ball():
    pygame.draw.circle(screen,(255,255,255),(int(ball_x),int(ball_y)),ball_r) 


while running:
    for event in pygame.event.get():
        running = not event.type == pygame.QUIT
                
    keys_pressed = pygame.key.get_pressed()
    
    if keys_pressed[pygame.K_UP]:
        player_right[1]-=movement_speed
    elif keys_pressed[pygame.K_DOWN]:
        player_right[1]+=movement_speed

    screen.fill((100,240,100))

    player_left[1] = ball_y-paddle_h//2

    if ball_y+ball_r>=screen_h or ball_y<=ball_r:
        vy*=-1

    if ball_x>=screen_w or ball_x<=0:
        ball_x = screen_w//2
        ball_y = screen_h//2

    
    #calculating ball position
    
    ball_x+=vx
    ball_y+=vy

    draw_ball()
    draw_paddle()


    pygame.display.update()

    pygame.time.delay(17)

