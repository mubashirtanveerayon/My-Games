import pygame

class Button:
    def __init__(self,surface,x,y,w,h):
        self.screen = surface
        self.x = x
        self.y = y
        self.width = w
        self.height = h

    def over(self,mouse_x,mouse_y):
        return mouse_x >= self.x and mouse_x <= self.x+self.width and mouse_y >= self.y and mouse_y <= self.y+self.height
        

    def draw(self,color):
        rect = pygame.Rect((self.x,self.y,self.width,self.height))
        pygame.draw.rect(self.screen,color,rect)
    
    def set_text(self,text,size,color):
        font = pygame.font.Font(pygame.font.get_default_font(), size)
        text_rect = font.render(text, True, color)
        render_text(self.screen,text,size,self.x+self.width//2-text_rect.get_width()//2,self.y+self.height//2-text_rect.get_height()//2,color)

def render_text(surface,text,size,x,y,color):
    font = pygame.font.Font(pygame.font.get_default_font(), size)
    text_rect = font.render(text, True, color)
    surface.blit(text_rect,(x,y))

pygame.init()

screen = pygame.display.set_mode((800,600))

b = Button(screen,100,150,100,100)

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
    screen.fill((0,0,0))
    
    b.draw((255,255,255))

    b.set_text("Hello",15,(0,0,0))

    pygame.display.update()