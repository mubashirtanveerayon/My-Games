import pygame
import random
import math

class Enemy:

    def __init__(self,surface,min_r,max_r,v,territory_flag):
        self.screen = surface
        self.territory_flag = territory_flag
        self.screen_w,self.screen_h = self.screen.get_size()
        self.r = random.randint(min_r,max_r)
        self.health = self.r*3
        self.speed = v
        self.min_r = min_r
        self.max_r = max_r
        self.respawn()

    def update(self,bullets):
        self.y+=self.speed
        for b in bullets:
            if math.dist((self.x,self.y),(b.x,b.y)) <= self.r or math.dist((self.x,self.y),(b.x+b.width,b.y))<=self.r or math.dist((self.x,self.y),(b.x,b.y+b.height))<=self.r or math.dist((self.x,self.y),(b.x+b.width,b.y+b.height))<=self.r:
                self.health-=b.damage
                bullets.remove(b)
                r = self.health//3
                if r>=self.min_r:
                    self.r = r
                break
    
    def respawn(self):
        if self.territory_flag:
            self.x = random.randint(self.r,self.screen_w//2-self.r)
            self.y = random.randint(-self.screen_h//2+self.r,-self.r)
        else:
            self.x = random.randint(self.screen_w//2+self.r,self.screen_w-self.r)
            self.y = random.randint(-self.screen_h+self.r,-self.screen_h//2-self.r)

    def draw(self):
        pygame.draw.circle(self.screen,(255,50,100),(int(self.x),int(self.y)),self.r)


class Bullet:

    def __init__(self,surface,x,y,v,damage=13):
        self.x = x
        self.y = y
        self.screen = surface
        self.damage = damage
        self.speed = v
        self.width = 5
        self.height = 25
        self.color = (255,0,0)
    
    def draw(self):
        rect = pygame.Rect((int(self.x),int(self.y),self.width,self.height))
        pygame.draw.rect(self.screen,(255,0,0),rect)
    
    def update(self):
        self.y+=self.speed


class Ship:
    
    def __init__(self,surface):
        self.screen = surface
        self.base = 50
        self.height = 65
        self.screen_w = self.screen.get_width()
        self.screen_h = self.screen.get_height()
        self.points = [[self.screen_w//2-self.base//2,self.screen_h-50],[self.screen_w//2,self.screen_h-50-self.height],[self.screen_w//2+self.base//2,self.screen_h-50]]
        self.speed = 11
        self.health = 200
        self.cooldown = 15

    def draw(self):
        pygame.draw.polygon(self.screen,(255,255,255),self.points)

    def act(self,keys_pressed):
        self.cooldown-=1
        if keys_pressed[pygame.K_w] and self.points[1][1]-self.speed>=0:
            for coord in self.points:
                coord[1] -= self.speed
        elif keys_pressed[pygame.K_s] and self.points[0][1]+self.speed<=self.screen_h and self.points[2][1]+self.speed<=self.screen_h:
            for coord in self.points:
                coord[1] += self.speed
        elif keys_pressed[pygame.K_d] and self.points[2][0]+self.speed<=self.screen_w:
            for coord in self.points:
                coord[0] += self.speed
        elif keys_pressed[pygame.K_a] and self.points[0][0]-self.speed>=0:
            for coord in self.points:
                coord[0] -= self.speed
        elif keys_pressed[pygame.K_SPACE] and self.cooldown<=0:
            self.cooldown = 15
            return Bullet(self.screen,self.points[1][0],self.points[1][1],-15)
    
    def update(self,enemies):
        for e in enemies:
            for coord in self.points:
                if math.dist((e.x,e.y),(coord[0],coord[1])) <= e.r:
                    self.health = 0
                    return
                # hyp = math.dist((e.x,e.y),(coord[0],coord[1]))
                # dotproduct = e.x*coord[0] + e.y*coord[1]
                # dotproduct/=(math.sqrt(e.x**2+e.y**2)*math.sqrt(coord[0]**2+coord[1]**2))            
                # angle = math.acos(dotproduct)
                # d = math.sin(angle)*hyp
                # if d<=e.r:
                #     print(str(d)+" | "+str(e.r)+" | "+str(angle)+" | "+str(self.points.index(coord)))
                #     self.health = 0
                #     return
            
                    

        
        
    
