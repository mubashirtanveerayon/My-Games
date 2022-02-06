Snake snake;
Food food;
int unit,score;
void setup(){
  size(500,500);
  frameRate(10);
  unit = 20;
  score=0;
  snake = new Snake(unit,0,0);
  food = new Food(unit);
}

void draw(){
  background(0);
  fill(255);
  textSize(50);
  text(score,width-90,50);
 noFill();
 if(snake.eat(food)){
   snake.grow();
   food.generate();
   score++;
 }
 if(snake.checkCollision()){
   snake.die();
   score=0;
 }
 
 snake.update(); 
 snake.show();
 fill(255,0,0);
 food.show();
 
}

void showGrid(){
  stroke(255);
  int unitw=(int)width/unit;
  int unith=(int)height/unit;
 for(int i=0;i<unitw;i++){
  line(i*unitw,0, i*unitw,height);
 }
 for(int i=0;i<unith;i++){
  line(0,i*unith, width,i*unith);
 }
}

void keyPressed(){
 if(keyCode==UP){
   snake.changeDir(up.copy());
 }else if(keyCode==DOWN){
    snake.changeDir(down.copy());
 }else if(keyCode==RIGHT){
    snake.changeDir(right.copy());
 }else if(keyCode==LEFT){
    snake.changeDir(left.copy());
 }
}
