
import pygame

from classes import *

screen_w = 1000
screen_h = 600

number_of_enemies = 10          

pygame.init()

screen = pygame.display.set_mode((screen_w,screen_h))

player = Ship(screen)

bullets = []

enemies = [0 for i in range(number_of_enemies)]

flag = True
for i in range(number_of_enemies):
    enemies[i] = Enemy(screen,15,40,3,flag)
    flag = not flag

running = True

while running:

    for event in pygame.event.get():
        running = not event.type == pygame.QUIT
    
    screen.fill((0,0,0))

    keys_pressed = pygame.key.get_pressed()

    bullet = player.act(keys_pressed)

    if bullet != None:
        bullets.append(bullet)

    for b in bullets:
        if b.y>=0:
            b.update()
            b.draw()
        else:
            bullets.remove(b)

    for e in enemies:
        e.update(bullets)
        e.draw()
        if e.health <= 0 or e.y-e.r >= screen_h:
            e.respawn()
 
    player.update(enemies)
    
    player.draw()

    pygame.display.update()

    pygame.time.delay(17)

    if player.health <= 0:
        break




                    

        
        
    
