import pygame
import pygame_gui

screen_w,screen_h = 800,600

pygame.init()

surface = pygame.display.set_mode((screen_w,screen_h))

manager = pygame_gui.UIManager((screen_w,screen_h))

running = True

clock = pygame.time.Clock()

button = pygame_gui.elements.UIButton(pygame.Rect((200,100,200,70)),"This is a button",manager)

text_box = pygame_gui.elements.UITextBox("this is a text box",pygame.Rect((200,200,200,70)),manager)

label = pygame_gui.elements.UILabel(pygame.Rect((200,300,200,70)),"this is a label",manager)

while running:
    surface.fill((0,0,0))
    time_delta = clock.tick(60) / 1000.0
    for event in pygame.event.get():
        running = not event.type == pygame.QUIT

        if event.type == pygame_gui.UI_BUTTON_PRESSED :
            if event.ui_element == button:
                running = False

        manager.process_events(event)
    
    manager.update(time_delta)
    manager.draw_ui(surface)
    pygame.display.update()
